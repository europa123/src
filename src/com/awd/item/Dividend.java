package com.awd.item;

/**
 * Represents a Dividend
 * @author awd
 *
 */
public class Dividend{
	

	private double lastDividend;
	private double fixedDividend;
	private int parValue;
	
	public Double getLastDividend() {
		return lastDividend;
	}
	public Double getFixedDividend() {
		return fixedDividend;
	}
	public Integer getParValue() {
		return parValue;
	}
	public void setLastDividend(double lastDividend) {
		this.lastDividend = lastDividend;
	}
	public void setFixedDividend(double fixedDividend) {
		this.fixedDividend = fixedDividend;
	}
	public void setParValue(int parValue) {
		this.parValue = parValue;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(fixedDividend);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lastDividend);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + parValue;
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
		Dividend other = (Dividend) obj;
		if (Double.doubleToLongBits(fixedDividend) != Double.doubleToLongBits(other.fixedDividend))
			return false;
		if (Double.doubleToLongBits(lastDividend) != Double.doubleToLongBits(other.lastDividend))
			return false;
		if (parValue != other.parValue)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Dividend [lastDividend=" + lastDividend + ", fixedDividend=" + fixedDividend + ", parValue=" + parValue
				+ "]";
	}
}

