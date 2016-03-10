package ru.bisoft.rent.model;


public class StatisticReport {
	
	private Object year;
	private Integer count;
	
	public Object getYear() {
		return year;
	}
	public void setYear(Object year) {
		this.year = year;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public StatisticReport(Object year, Integer count) {
		super();
		this.year = year;
		this.count = count;
	}
}
