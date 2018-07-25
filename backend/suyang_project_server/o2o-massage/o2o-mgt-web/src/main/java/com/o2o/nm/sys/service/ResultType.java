package com.o2o.nm.sys.service;

import cn.jeeweb.modules.sys.Constants;

public enum ResultType {
	UPDATE(Constants.RESULT_UPDATE), INSERT(Constants.RESULT_INSERT), ERROR("error"), SUCCESS("success"), EXIST("exist");

	private String name;

	private ResultType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}