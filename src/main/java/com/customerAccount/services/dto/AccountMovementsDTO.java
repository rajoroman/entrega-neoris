package com.customerAccount.services.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountMovementsDTO extends MovementBaseDTO {

	private Long id;	
	private AccountDTO account;
	private BigDecimal balance;
	private Boolean status;
	@Temporal(TemporalType.DATE)
	private LocalDate date;
}
