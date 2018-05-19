package com.renjith.minimum.webProject.domain;

/**
 * @author Renjith
 *
 */
public class EventObject {

	private String event_type;
	private String data;
	private long timestamp;
	
	
	public String getEvent_type() {
		return event_type;
	}
	public String getData() {
		return data;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}
	public void setData(String data) {
		this.data = data;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "xxxxJsonO [event_type=" + event_type + ", data=" + data + ", timestamp=" + timestamp + "]";
	}	
	
	
	
	
	
	
	
}