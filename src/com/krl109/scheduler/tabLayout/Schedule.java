package com.krl109.scheduler.tabLayout;


public class Schedule {
	private int imageId;
	private String dateTimeSch;
	private String recipientNumbers;
	private String contentMessages;

	public Schedule(int imageId, String dateTime, String recipientNumbers, String contentMessages) {
		this.imageId = imageId;
		this.dateTimeSch = dateTime;
		this.recipientNumbers = recipientNumbers;
		this.contentMessages = contentMessages;
	}
	
	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getDateTimeSch() {
		return dateTimeSch;
	}

	public void setDateTimeSch(String dateTimeSch) {
		this.dateTimeSch = dateTimeSch;
	}

	public String getRecipientNumbers() {
		return recipientNumbers;
	}

	public void setRecipientNumbers(String recipientNumbers) {
		this.recipientNumbers = recipientNumbers;
	}

	public String getContentMessages() {
		return contentMessages;
	}

	public void setContentMessages(String contentMessages) {
		this.contentMessages = contentMessages;
	}

	@Override
	public String toString() {
		return dateTimeSch + "\n" + recipientNumbers + "\n" + contentMessages;
	}
}
