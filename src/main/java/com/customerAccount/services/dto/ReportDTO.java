package com.customerAccount.services.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReportDTO  {
	
	private LocalDate date;
	private String nameCustomer;
	private String accountNumber;
	private String typeAccount;
	private BigDecimal initialBalance;
	private Boolean status;
	private BigDecimal value;
	private BigDecimal balance;
}
