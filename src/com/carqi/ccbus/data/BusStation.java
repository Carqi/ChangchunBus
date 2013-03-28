package com.carqi.ccbus.data;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BusStation implements Serializable {
	private String station;

	public BusStation() {
	}
	public BusStation(String station) {
		this.station = station;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}
	
}
