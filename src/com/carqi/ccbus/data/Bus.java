package com.carqi.ccbus.data;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Bus implements Serializable {
	private String line;
	private String intro;
	private String forward;
	private String reverse;
	private String station;
	
	
	public Bus(String line, String intro, String station) {
		this.line = line;
		this.intro = intro;
		this.station = station;
	}
	public Bus(String line, String intro, String forward, String reverse,
			String station) {
		this.line = line;
		this.intro = intro;
		this.forward = forward;
		this.reverse = reverse;
		this.station = station;
	}
	public Bus() {
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	public String getReverse() {
		return reverse;
	}
	public void setReverse(String reverse) {
		this.reverse = reverse;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	

}
