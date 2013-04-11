package com.carqi.ccbus.data;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BusExchange implements Serializable {
	private String startStation; //起点
	private String line1; //线路1
	private String exchangeStation1; //换乘站点1
	private String line1_StopCount; //起点到换乘站点1所经过的站点数
	private String line2; //线路2
	private String exchangeStation2; //换乘站点2（2次换乘时用到）
	private String line3; //线路3 （2次换乘时用到）
	private String endStation; //终点
	private String line2_StopCount; //1次换乘时换乘站点到终点所经过的站点数
	private String total; //乘坐的总站数
	
	
	
	
	
	
	
	/**
	 * 换乘查询可直达时的构造方法
	 * @param startStation 这里存储的为线路的所有站
	 * @param line1
	 */
	public BusExchange(String startStation, String line1) {
		this.startStation = startStation;
		this.line1 = line1;
	}


	/**
	 * 换乘查询需1次换乘时的构造方法
	 * @param startStation
	 * @param line1
	 * @param exchangeStation1
	 * @param line1_StopCount
	 * @param line2
	 * @param endStation
	 * @param line2_StopCount
	 * @param total
	 */
	public BusExchange(String startStation, String line1, 
					   String exchangeStation1, String line1_StopCount, 
					   String line2, String endStation,
					   String line2_StopCount, String total) {
		this.startStation = startStation;
		this.line1 = line1;
		this.exchangeStation1 = exchangeStation1;
		this.line1_StopCount = line1_StopCount;
		this.line2 = line2;
		this.endStation = endStation;
		this.line2_StopCount = line2_StopCount;
		this.total = total;
	}
	/**
	 * 换乘查询需2次换乘时的构造方法
	 * @param startStation
	 * @param line1
	 * @param exchangeStation1
	 * @param line1_StopCount
	 * @param line2
	 * @param exchangeStation2
	 * @param line2_StopCount
	 * @param line3
	 * @param endStation
	 * @param total
	 */
	public BusExchange(String startStation, String line1,
			String exchangeStation1, String line1_StopCount, String line2,
			String exchangeStation2, String line2_StopCount, String line3,
			String endStation, String total) {
		super();
		this.startStation = startStation;
		this.line1 = line1;
		this.exchangeStation1 = exchangeStation1;
		this.line1_StopCount = line1_StopCount;
		this.line2 = line2;
		this.exchangeStation2 = exchangeStation2;
		this.line2_StopCount = line2_StopCount;
		this.line3 = line3;
		this.endStation = endStation;
		this.total = total;
	}


	public String getStartStation() {
		return startStation;
	}
	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}
	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getExchangeStation1() {
		return exchangeStation1;
	}
	public void setExchangeStation1(String exchangeStation1) {
		this.exchangeStation1 = exchangeStation1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public String getExchangeStation2() {
		return exchangeStation2;
	}
	public void setExchangeStation2(String exchangeStation2) {
		this.exchangeStation2 = exchangeStation2;
	}
	public String getLine3() {
		return line3;
	}
	public void setLine3(String line3) {
		this.line3 = line3;
	}
	public String getEndStation() {
		return endStation;
	}
	public void setEndStation(String endStation) {
		this.endStation = endStation;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}


	public String getLine1_StopCount() {
		return line1_StopCount;
	}


	public void setLine1_StopCount(String line1_StopCount) {
		this.line1_StopCount = line1_StopCount;
	}


	public String getLine2_StopCount() {
		return line2_StopCount;
	}


	public void setLine2_StopCount(String line2_StopCount) {
		this.line2_StopCount = line2_StopCount;
	}

	
}
