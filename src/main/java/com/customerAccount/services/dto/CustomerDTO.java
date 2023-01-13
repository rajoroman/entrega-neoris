package com.customerAccount.services.dto;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDTO extends PersonDTO implements Serializable  {
	
	@NotNull(message = "CustomerId cannot be empty or null.")
	private String customerId;
	@NotNull(message = "Password cannot be empty or null.")
	private String password;
	@NotNull(message = "Status cannot be empty or null.")
	private Boolean status;
	private List<AccountDTO> accounts;
}
