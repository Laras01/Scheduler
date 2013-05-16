package com.krl109.scheduler.tabLayout;


public class Schedule {
	private int imageId;
	private String dateTimeSch;
	private String recipientNumbers;
	private String contentMessages;
	
	public static int scheduleId;
	private long timemillis;
	private String frequency;
	private String remaining;
	private String status;
	private String freqtimes;
	
	public Schedule(){};
	
	public Schedule(int imageId, String dateTime, String recipientNumbers, String contentMessages) {
		this.imageId = imageId;
		this.dateTimeSch = dateTime;
		this.recipientNumbers = recipientNumbers;
		this.contentMessages = contentMessages;
	}
	
	public Schedule(long timemillis, String[] data){
		this.timemillis = timemillis;
		this.dateTimeSch = data[0];
		this.recipientNumbers = data[1];
		this.contentMessages = data[2];
		this.frequency = data[3];
		this.remaining = data[4];
		this.status = data[5];
		this.freqtimes = data[6];
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

	public long getTimemillis() {
		return timemillis;
	}

	public void setTimemillis(long timemillis) {
		this.timemillis = timemillis;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getRemaining() {
		return remaining;
	}

	public void setRemaining(String remaining) {
		this.remaining = remaining;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFreqtimes() {
		return freqtimes;
	}

	public void setFreqtimes(String freqtimes) {
		this.freqtimes = freqtimes;
	}
	
	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	@Override
	public String toString() {
		return dateTimeSch + "\n" + recipientNumbers + "\n" + contentMessages;
	}
}
