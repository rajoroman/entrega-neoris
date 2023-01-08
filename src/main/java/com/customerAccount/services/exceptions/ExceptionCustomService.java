package com.customerAccount.services.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExceptionCustomService extends Exception {
	
	private static final long serialVersionUID = -5944006828038286308L;
	
	public static final int CUSTOMER_NOT_FOUND = 1;
	public static final int CUSTOMER_ALREADY_EXISTIS = 2;
	public static final int ACCOUNT_TYPE_NOT_FOUND = 3;
	public static final int INSUFFICIENT_BALANCE = 4;
	
	private String message;
	private int code;
	
	public ExceptionCustomService(String msg) {
		super(msg);
	}
	
	public ExceptionCustomService(Throwable e) {
		super(e);
	}
	
	public ExceptionCustomService(int code) {
		super();
		this.code = code;
	}
}
