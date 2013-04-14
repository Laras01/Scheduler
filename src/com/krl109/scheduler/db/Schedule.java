package com.krl109.scheduler.db;

public class Schedule {
	long timeList;
	String recipient;
	String message;

	public Schedule(long timeList, String recipient, String message) {
		this.timeList = timeList;
		this.recipient = recipient;
		this.message = message;
	}

	public long getTimeList() { return timeList; }

	public void setTimeList(long timeList) { this.timeList = timeList; }

	public String getRecipient() { return recipient; }

	public void setRecipient(String recipient) { this.recipient = recipient; }

	public String getMessage() { return message; }

	public void setMessage(String message) { this.message = message; }
}
