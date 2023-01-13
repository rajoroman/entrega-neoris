package com.customerAccount.services.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestReportDTO {
	
	@NotEmpty(message = "CustomerId cannot be empty or null.")
	private String customerId;
	
	@NotNull(message = "From cannot be empty or null.")
	private LocalDate from;
	
	@NotNull(message = "To cannot be empty or null.")
	private LocalDate to;
}
