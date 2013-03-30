package com.carqi.ccbus.data;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BusStation implements Serializable {
	private String station;
	private String fristLetter;
	private String allLetter;
	
	public BusStation() {
	}
	
	public BusStation(String station) {
		this.station = station;
	}

	public BusStation(String station, String fristLetter, String allLetter) {
		this.station = station;
		this.fristLetter = fristLetter;
		this.allLetter = allLetter;
	}
	
	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}
	public String getFristLetter() {
		return fristLetter;
	}
	public void setFristLetter(String fristLetter) {
		this.fristLetter = fristLetter;
	}
	public String getAllLetter() {
		return allLetter;
	}
	public void setAllLetter(String allLetter) {
		this.allLetter = allLetter;
	}
	
}
