package com.customerAccount.services.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewAccountMovementsDTO extends MovementBaseDTO {
	
    @NotEmpty(message = "Account Number cannot be empty or null")
	private String accountNumber;
}
