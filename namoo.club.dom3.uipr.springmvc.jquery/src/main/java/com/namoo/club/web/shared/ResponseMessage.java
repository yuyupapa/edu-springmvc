package com.namoo.club.web.shared;

public class ResponseMessage {
	//
	private boolean success;
	private Object message;
	
	//--------------------------------------------------------------------------
	
	public ResponseMessage(boolean success) {
		//
		this.success = success;
	}

	public ResponseMessage(boolean success, Object message) {
		//
		this.success = success;
		this.message = message;
	}
	
	public ResponseMessage(Exception e) {
		//
		this.success = false;
		this.message = e.getMessage();
	}
	
	//--------------------------------------------------------------------------

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}
}
