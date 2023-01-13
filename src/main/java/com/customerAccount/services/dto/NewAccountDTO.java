package com.customerAccount.services.dto;



import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewAccountDTO {

	private Long id;
	
	@NotEmpty(message = "Account number cannot be empty or null")
	private String accountNumber;
	
	@NotNull(message = "Type account cannot be empty or null")
	@Min(value = 1, message = "Type account cannot be less 1")
	private Long typeAccount;
	
	@NotNull(message = "Balance initial cannot be empty or null")
	private BigDecimal  initialBalance;
	
	@NotNull(message = "Status cannot be empty or null")
	private Boolean status;
	
	@NotEmpty(message = "Customer Id cannot be empty or null")
	private String customerId;
}
