package com.customerAccount.services.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.TransactionException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ErrorHandler {

	private static final String DATA_EXIST = "Data already exists";
	
	private static final String CUSTOMER_EXIST = "Customers already exists";

	private static final String CONNECTION_CLOSE = "Error establishing a database connection";
	
	private static final String CUSTOMER_NOT_FOUND = "Customer not found";
	
	private static final String CUSTOMER_OR_TYPEACCOUNT_NOT_FOUND = "Customer or Type Account not found";
	
	public static final String INSUFFICIENT_BALANCE = "Insufficent balance";
	
	public static final String FORMAT_INVALID_STRING = "Invalid format in string, string empty";

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorInfo> runtimeException(HttpServletRequest request, RuntimeException e) {

		ErrorInfo errorInfo = null;
		HttpStatus httpStatus = null;

		if (e.getCause() instanceof DataIntegrityViolationException) {
			errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), DATA_EXIST, request.getRequestURI());
			httpStatus = HttpStatus.BAD_REQUEST;

		} else if (e.getCause() instanceof TransactionException) {
			errorInfo = new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), CONNECTION_CLOSE,
					request.getRequestURI());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			
		} else if (e.getCause() instanceof ExceptionCustomService) {
			
			ExceptionCustomService cause = (ExceptionCustomService) e.getCause();
			
			if(cause.getCode() == ExceptionCustomService.INSUFFICIENT_BALANCE) {
				errorInfo = new ErrorInfo(HttpStatus.OK.value(), INSUFFICIENT_BALANCE,
						request.getRequestURI());
				httpStatus = HttpStatus.OK;
				
			}else if(cause.getCode() == ExceptionCustomService.CUSTOMER_NOT_FOUND) {
				
				errorInfo = new ErrorInfo(HttpStatus.NOT_FOUND.value(), CUSTOMER_OR_TYPEACCOUNT_NOT_FOUND,
						request.getRequestURI());
				httpStatus = HttpStatus.NOT_FOUND;
			}
			
		}else if(e.getCause() instanceof InvalidFormatException) {
			errorInfo = new ErrorInfo(HttpStatus.OK.value(), FORMAT_INVALID_STRING,
					request.getRequestURI());
			httpStatus = HttpStatus.OK;
		}
			
		return new ResponseEntity<>(errorInfo, httpStatus);
	}

	@ExceptionHandler(ExceptionCustomService.class)
	public ResponseEntity<ErrorInfo> notDataFoundException(HttpServletRequest request, ExceptionCustomService e) {

		ErrorInfo errorInfo = null;
		HttpStatus httpStatus = null;
		
		if(e.getCode() == ExceptionCustomService.CUSTOMER_NOT_FOUND) {
			errorInfo = new ErrorInfo(HttpStatus.NOT_FOUND.value(), CUSTOMER_NOT_FOUND, request.getRequestURI());
			httpStatus = HttpStatus.NOT_FOUND;
		}
		
		if(e.getCode() == ExceptionCustomService.CUSTOMER_ALREADY_EXISTIS) {
			errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), CUSTOMER_EXIST, request.getRequestURI());
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		
		if(e.getCode() == ExceptionCustomService.INSUFFICIENT_BALANCE) {
			errorInfo = new ErrorInfo(HttpStatus.OK.value(), INSUFFICIENT_BALANCE, request.getRequestURI());
			httpStatus = HttpStatus.OK;
		}

		return new ResponseEntity<>(errorInfo, httpStatus);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> notDataFoundException(HttpServletRequest request, Exception e) {

		ErrorInfo errorInfo = null;
		HttpStatus httpStatus = null;

		return new ResponseEntity<>(errorInfo, httpStatus);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> handleValidationErrors(HttpServletRequest request, MethodArgumentNotValidException ex) {
		
		
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        
        List<String> message = this.buildMessage(fieldErrors);
        
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.OK.value(), message, request.getRequestURI());
        
        return new ResponseEntity<>(errorInfo, HttpStatus.OK);
    }
	
	@ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorInfo> constraintViolationExceptionErrors(HttpServletRequest request,ConstraintViolationException ex) {
		
		List<String> errorList = new ArrayList<>();
		ex.getConstraintViolations().forEach(constrains ->{
			errorList.add(constrains.getMessage());
		});
		
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.OK.value(), errorList, request.getRequestURI());
        
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
	
	private List<String> buildMessage(List<FieldError> fieldErrors) {
		List<String> message = new ArrayList<>();
		fieldErrors.forEach(field ->{
			message.add(field.getDefaultMessage().toUpperCase());
		});
		return message;
	}
}
