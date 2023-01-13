package com.customerAccount.services.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenderDTO implements Serializable {
	
	private Long   id; 	
	private String description;
}
