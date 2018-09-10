package com.david.ilvolo.util;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "message")
public class ResponseMessage {

	private String response;

	public ResponseMessage() {
		super();
	}

	public ResponseMessage(String response) {
		super();
		this.response = response;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
