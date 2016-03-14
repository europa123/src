package com.awd.load;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import com.awd.item.Stock;

/**
 * Loads individual Stocks from file for use by the Exchange
 * @author awd
 *
 */
public class StockLoader{
	
	private BufferedReader buff;
	private InputStreamReader reader;
	
	private List<Stock> stockList = new LinkedList<Stock>();
	
	/**
	 * 
	 * @param inputFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public StockLoader(InputStream in) throws FileNotFoundException, IOException{

		reader = new InputStreamReader(in);
		
		readFile();
		
	}

	private void readFile() throws IOException{
		buff = new BufferedReader(reader);
		
		String line = buff.readLine();
		while(line != null){
			//Process the line into memory - Create List and return from public method
			System.out.println(line);
			
			createStock(line);
			
			line = buff.readLine();
		}
		
	}
	
	/**
	 * Create Stock from input
	 * @param line
	 */
	private void createStock(String line) {

		String [] stockFields = line.split("\\|");
		
		String symbol    = stockFields[0];
		String type      = stockFields[1];
		Double lastDiv   = (stockFields[2].isEmpty() || stockFields[2] == "" ? 0 :  Double.valueOf(stockFields[2]));
		Double fixedDiv  = (stockFields[3].isEmpty() || stockFields[3] == "" || stockFields[3] == null ? 0 :  Double.valueOf(stockFields[3]));
		Integer parValue = (stockFields[4] == "" ? 0 :  Integer.valueOf(stockFields [4]));
		
		System.out.println("symbol: "   + symbol);		
		System.out.println("type: "     + type);		
		System.out.println("lastDiv: "  + lastDiv);		
		System.out.println("fixedDiv: " + fixedDiv);		
		System.out.println("parValue: " + parValue);	
		
		Stock stock = new Stock(lastDiv, fixedDiv, parValue, 0.00);
		stock.setSymbol(symbol);
		stock.setType(type);
		
		stockList.add( stock );
	}

	/**
	 * 
	 * @return
	 */
	public List<Stock> getStockList(){
		return stockList;
	}
}


