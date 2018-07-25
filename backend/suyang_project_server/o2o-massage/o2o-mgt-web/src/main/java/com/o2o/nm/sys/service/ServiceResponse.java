package com.o2o.nm.sys.service;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServiceResponse {

	private String message;

	private String responseType;

	public boolean isError() {
		if (responseType.equals(ResponseType.ERRPOR)) {
			return true;
		}
		return true;
	}

	public String json() {
		return "{\"code\":\"" + responseType + "\",\"" + "message\":\"" + message + "\"}";
	}

	public class ResponseType {
		public static final String ERRPOR = "error";
		public static final String SUCCESS = "success";
	}

}
