package com.customerAccount.services.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorInfo {
	
	
	   public ErrorInfo(int statusCode, Object message, String uri) {
	       this.message = message;
	       this.statusCode = statusCode;
	       this.uri = uri;
	       
	   }

	   @JsonProperty("message")
	   private Object message;
	   
	   @JsonProperty("status_code")
	   private int statusCode;
	   
	   @JsonProperty("uri")
	   private String uri;
	  
	   
}
