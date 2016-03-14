package com.awd.item;

import java.sql.Timestamp;
import java.util.Date;



/**
 * Represents a Trade
 * @author awd
 *
 */
public class Trade implements Comparable<Trade>{

	private Timestamp tradeDate;
	private int nmrShares;
	private boolean isBuy;
	private double price;
	private Stock stock;
	

	/**
	 * Represents a Trade
	 * @param stockSymbol
	 * @param tradeDate
	 * @param isBuy
	 */
	public Trade(Stock stock, boolean isBuy){
		this.stock = stock;
		this.isBuy = isBuy;
	}
	
	
	/**
	 * Returns number of shares in the trade
	 * @return
	 */
	public int getNmrShares() {
		return nmrShares;
	}

	/**
	 * OSets the number of shares to the incoming value
	 * @param nmrShares
	 */
	public void setNmrShares(int nmrShares) {
		this.nmrShares = nmrShares;
	}
	
	/**
	 * Increments number of shares by 1 and returns total
	 * @return
	 */
	public int addShare(){
		return ++nmrShares;
	}

	
	public void sellShares() {
		isBuy = false;
	}
	
	public void buyShares(){
		isBuy = true;
	}

	public void setBuy(boolean isBuy) {
		this.isBuy = isBuy;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Returns trade date, or null if the trade has not been committed
	 * @return
	 */
	public Date getTradeDate() {
		return tradeDate;
	}

	public Stock getStock() {
		return stock;
	}
	
	/**
	 * Return "B" for buy, "S" for sell
	 * @return
	 */
	public String getBuySellIndicator(){
		
		if (isBuy){
			return "B";
		}
		else{
			return "S";
		}
	}
	
	/**
	 * 
	 * @return
	 */
/*	public double getStockPrice(){
	
		long currentTime = System.currentTimeMillis();
		
		List<PriceHistory> last15Mins = new LinkedList<PriceHistory>();
		
		for (PriceHistory priceHistory : getPriceHistory()) {
			if(priceHistory.getTimestamp().getTime() >= currentTime){
				last15Mins.add(priceHistory);
			}
			else{
				break; //list is sorted
			}
		}
	}
*/	
	/**
	 * Ordinarily this field would be immutable,
	 * but setting the time allows for some automated trades to be historically set 
	 * for the sake of the example.
	 */
	public void setTradeDate(Timestamp timestamp){
		
		if(timestamp != null){
			tradeDate = timestamp;
		}
		else{
			tradeDate = new Timestamp(System.currentTimeMillis());
		}
	}
	
	@Override
	public String toString() {
		return "Trade [tradeDate=" + tradeDate + ", nmrShares=" + nmrShares + ", isBuy=" + isBuy + ", price=" + price
				+ ", stock=" + stock + "]";
	}


	public int compareTo(Trade trade){
		return trade.getTradeDate().compareTo(getTradeDate());
	}
}
