package com.krl109.scheduler.tabLayout;


public class Schedule {
	private int imageId;
	public String dateTimeSch;
	public String recipientNumbers;
	public String contentMessages;
	
	public static int scheduleId;
	public long timemillis;
	public long timesent;
	public String frequency;
	public String remaining;
	public String status;
	public String freqtimes;
	public String type;
	
	public Schedule(){};
	
	public Schedule(int imageId, String dateTime, String recipientNumbers, String contentMessages) {
		this.imageId = imageId;
		this.dateTimeSch = dateTime;
		this.recipientNumbers = recipientNumbers;
		this.contentMessages = contentMessages;
	}
	
	public Schedule(long timemillis, long timesent, String[] data){
		this.timemillis = timemillis;
		this.dateTimeSch = data[0];
		this.recipientNumbers = data[1];
		this.contentMessages = data[2];
		this.frequency = data[3];
		this.remaining = data[4];
		this.status = data[5];
		this.freqtimes = data[6];
		this.timesent = timesent;
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
	
	public long getTimesent() {
		return timesent;
	}

	public void setTimesent(long timesent) {
		this.timesent = timesent;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setMessageType(String message){
		
		if(message.contains("%%")){
			this.type = "typical";
		}
		else{
			this.type = "normal";
		}
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return dateTimeSch + "\n" + recipientNumbers + "\n" + contentMessages;
	}
}
