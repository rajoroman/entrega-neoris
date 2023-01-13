package com.customerAccount.services.dto;

import java.math.BigDecimal;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDTO {

	private Long id;
	private String accountNumber;
	private AccountTypeDTO typeAccount;	
	private BigDecimal  initialBalance;	
	private Boolean status;
	private CustomerDTO customer;
	private List<AccountMovementsDTO> movements;
}
