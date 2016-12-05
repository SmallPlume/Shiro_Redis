package com.modules.websocket.entity;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 提示
	 */
	private String alert;
	
	
	private List<String> names;
	
	
	private String sentMsg;
	
	private String from;
	
	private String date;

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public String getSentMsg() {
		return sentMsg;
	}

	public void setSentMsg(String sentMsg) {
		this.sentMsg = sentMsg;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Message(String alert, List<String> names, String sentMsg,
			String from, String date) {
		super();
		this.alert = alert;
		this.names = names;
		this.sentMsg = sentMsg;
		this.from = from;
		this.date = date;
	}
	
}
