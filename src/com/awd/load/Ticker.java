package com.awd.load;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Ticker {

	/**
	 * Ticker for stock prices
	 */
	public Ticker(){
		
	}
	
	public static double getTickerPrice(String stockSymbol){
		
		//at this moment, we return random prices.  
		//Stock symbol can be used in future to get specific price when 
		//functionality becomes available
		DecimalFormat df = new DecimalFormat("#.####");
		df.setRoundingMode(RoundingMode.CEILING);
		Double d = Math.random();
		
		return Double.valueOf(df.format(d*100));
	}
}
