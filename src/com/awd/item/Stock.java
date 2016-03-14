package com.awd.item;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import com.awd.exchange.Exchange;

/**
 * Represents a Stock object
 * @author awd
 *
 */
public class Stock {

	private String symbol;
	private String type;
	private Dividend dividend;
	private double price;
	private List<PriceHistory> priceHistory = new LinkedList<PriceHistory>();
	
	/**
	 * 
	 * @param lastDividend
	 * @param fixedDividend
	 * @param parValue
	 * @param price
	 */
	public Stock(Double lastDividend, Double fixedDividend, Integer parValue, Double price){
		
		Dividend div = new Dividend();
		div.setFixedDividend(fixedDividend);
		div.setLastDividend(lastDividend);
		div.setParValue(parValue);
		this.dividend = div;
		this.price = price;
		priceHistory.add(new PriceHistory(price, new Timestamp(System.currentTimeMillis())));
	}
	

	/**
	 * 
	 * @param newPrice
	 */
	public void updatePrice(double newPrice){
		
		priceHistory.add(new PriceHistory(newPrice, new Timestamp(System.currentTimeMillis())));
		this.price = newPrice;
		
	}
	
	/**
	 * Returns the stock price
	 * @return
	 */
	public double getPrice(){
		
		return price;
	}
	
	@Override
	public String toString() {
		return "Stock [symbol=" + symbol + ", type=" + type + ", dividend=" + dividend + ", price=" + price + "]";
	}


	/**
	 * 
	 * @return
	 */
	public List<PriceHistory> getPriceHistory(){
		
		return priceHistory;
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getPeRatio(){
		
		double ratio = 0;
		
		if (type != null && type.equals(Exchange.PREFERRED)){
			ratio = price / getPreferredYeild();
		} 
		else {
			ratio = price / getCommonYeild();
		}
		
		return ratio;
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getCommonYeild(){
		if (dividend == null || dividend.getLastDividend() == 0 || price == 0){
			return 0.0;
		}
		
		return dividend.getLastDividend() / price;
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getPreferredYeild(){
		if(dividend == null || dividend.getFixedDividend() == 0 || dividend.getParValue() == 0){
			return 0.0;
		}
		
		return dividend.getFixedDividend()*dividend.getParValue();
	}


	/**
	 * 
	 * @param dividend
	 */
	public void setDividend(Dividend dividend) {
		this.dividend = dividend;
	}


	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the lastDividend
	 */
	public Double getLastDividend() {
		return dividend.getLastDividend();
	}


	/**
	 * @return the fixedDividend
	 */
	public Double getFixedDividend() {
		
		if(dividend.getFixedDividend() != null){
			return dividend.getFixedDividend();
		}
		else {
			return null;
		}
	}


	/**
	 * @return the parValue
	 */
	public Integer getParValue() {
		return dividend.getParValue();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dividend == null) ? 0 : dividend.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		if (dividend == null) {
			if (other.dividend != null)
				return false;
		} else if (!dividend.equals(other.dividend))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	/**
	 * 
	 * @author awd
	 *
	 */
	private class PriceHistory {
		
		private java.sql.Timestamp timestamp;
		private double price;
		
		private PriceHistory(double price, Timestamp timestamp){
			this.price = price;
			this.timestamp = timestamp;
		}
		
		public java.sql.Timestamp getTimestamp() {
			return timestamp;
		}

		public double getPrice() {
			return price;
		}
		
	}
	
}