package com.nyc.station.api.controller.model;

public class ResponseDto {

	public static final String SUCCESS = "success";
	public static final String ERROR = "error";

	protected String result = SUCCESS;
	protected String message = "";

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
