package com.awd.exchange;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.awd.item.Stock;
import com.awd.item.Trade;
import com.awd.load.StockLoader;
import com.awd.load.Ticker;

/**
 * Trade Exchange
 * 
 * @author awd
 *
 */
public class Exchange {

	public final static String PREFERRED = "Preferred";
	public final static String DATE_FORMAT = "yyyyMMdd kk:mm:ss:SSS";
	public List<Stock> STOCKS = null;
	public List<Trade> TRADES = new LinkedList<Trade>();
	
	private static DecimalFormat df = new DecimalFormat("#.####");
	
	
	public static void main(String[] args) {
		
		Exchange exchange = new Exchange();
		
		exchange.loadStocks();
		exchange.automatedTrades();
		exchange.calculateGeometricMean();
		
		exchange.calculateStockPrice();
	}
	
	
	/**
	 * Calculates the GBCE All Share Index
	 */
	public void calculateGeometricMean() {

		double product = 1.0;
		//Loop through stocks
		for (int i = 0; i < STOCKS.size(); i++) {
			if(STOCKS.get(i).getPrice() > 0){
				product = product * STOCKS.get(i).getPrice();
			}
			System.out.println(product);
		}
		double geometricMean = Math.pow(product, 1.0/STOCKS.size());
		
		System.out.println("GBCE All Share Index stands at: " + formatNumber(geometricMean));
	}
	
	
	/**
	 * Calculates the Stock Price for a given stock
	 * Price is based on trades in last 15 mins
	 * @param stockSymbol
	 * @return
	 */
	public void calculateStockPrice(){
		
		updateTradeDatesToPast();
		
		Collections.sort(TRADES);

		for(int i = 0; i < STOCKS.size(); i++){
			
			long cutOffTime = (System.currentTimeMillis() - (60*15*1000));
			
			List<Trade> last15Mins = new LinkedList<Trade>();
			
			int totalTradeCount = 0;
			int truncatedTradeCount = 0;
			for (Trade trade : TRADES) {
				
				
				if( trade.getStock().getSymbol().equals(STOCKS.get(i).getSymbol())) {
					totalTradeCount++;
					
					if(trade.getTradeDate().getTime() >= cutOffTime){
						truncatedTradeCount++;
						last15Mins.add(trade);
					}
				}
			}
			
			int quantityRunningTotal = 0;
			double tempCalc = 0;
			
			for (Trade trade : last15Mins) {
				
				System.out.println("TRADE: " + trade.toString());
				quantityRunningTotal = quantityRunningTotal + trade.getNmrShares();
				tempCalc = tempCalc + (trade.getPrice() * trade.getNmrShares());
			}
			
			System.out.println("Stock Price for: " + STOCKS.get(i).getSymbol());
			System.out.println("Total Trades: " + totalTradeCount);
			System.out.println("Truncated Trades: " + truncatedTradeCount);
			System.out.println("Stock Price: " + formatNumber( tempCalc / quantityRunningTotal));
		}
	}

	
	/**
	 * Fabricate older trades
	 */
	private void updateTradeDatesToPast() {

		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		c.set(Calendar.YEAR, 2015);
		for(int i = 0; i < 10; i++){
			TRADES.get(i).setTradeDate(new Timestamp(c.getTimeInMillis()));
		}
	}


	/**
	 * Commits a trade to the TRADES collection
	 * @param trade
	 */
	public void commitTrade(Trade trade){

			trade.setTradeDate(null);
			TRADES.add(trade);
	}
	


	/**
	 * Standard format for dates
	 * @param date
	 * @return
	 */
	public static String dateAsString(Date date){
		
		return new SimpleDateFormat(DATE_FORMAT).format(date);	
	}
	
	


	/**
	 * Automated trades, randomly selected stocks and buy/sell ind
	 */
	private void automatedTrades(){
		
		int ceilingOfAutomatedTrades = 100;
		int nmrAutomatedTrades = 0;
		
		//ensure all stocks are traded at least once
		Set<String> tradedStocks = new HashSet<String>();
		
		while(nmrAutomatedTrades < ceilingOfAutomatedTrades || tradedStocks.size() < STOCKS.size()){
			//Random numbers for stock to be picked & initial pricing
			
			double randomStock = Math.random();
			double randomNmrShares = Math.random();
			
			int stockPicker = (int) (randomStock*10)%10;
			int nmrShares = (int)(randomNmrShares*10)%10;
			
			//select a stock if the random number is inside the list size
			if(stockPicker < STOCKS.size()){
				Stock stock = STOCKS.get(stockPicker);
				
				stock.updatePrice(Ticker.getTickerPrice(stock.getSymbol()));

				Trade trade = new Trade(stock, getIsBuyFlag());
				trade.setPrice(Double.valueOf(formatNumber( stock.getPrice() * nmrShares)));
				trade.setNmrShares(nmrShares);

				commitTrade(trade);
				tradedStocks.add(stock.getSymbol());
				nmrAutomatedTrades++;
			}
		}
		
		System.out.println("Automated trades completed: nmr trades " + TRADES.size());
	}
	



	/**
	 * Load stocks from file
	 */
	private void loadStocks() {

		try{			
			InputStream input = getClass().getResourceAsStream("..\\..\\..\\resource\\StockDetails.txt");
			
			StockLoader loader = new StockLoader(input);
			
			STOCKS = loader.getStockList();
			
			for (Stock stock : STOCKS) {
				System.out.println("Stock: " + stock.toString());
			}
			
		} catch (FileNotFoundException fnf){
			fnf.printStackTrace();
			System.out.println("IO issue while manipulating stocks " + fnf.getMessage());
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Unknown error whileprocessing stocks: " + e.getMessage());
		}
	}
	
	
	
	/**
	 * Random boolean generator
	 * @return
	 */
	private boolean getIsBuyFlag(){
		double num = Math.random() ;
		return ((int)(num*10) % 10) >= 5;
	}
	

	/**
	 * Formats incoming decimal numbers
	 * @param number
	 * @return
	 */
	private String formatNumber(double number){
		
		df.setRoundingMode(RoundingMode.CEILING);
		return df.format(number);
	}
}
