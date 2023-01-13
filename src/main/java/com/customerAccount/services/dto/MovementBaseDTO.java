package com.customerAccount.services.dto;

import java.math.BigDecimal;

import com.customerAccount.services.enums.TypesOfMovements;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovementBaseDTO {

	@NotNull(message = "Type movement cannot be empty or null")
	@Enumerated(EnumType.STRING) 
	private TypesOfMovements typeOfMovement;
	
	@NotNull(message = "Value of movement cannot be empty or null")
	@Min(value = 1, message = "Value of movement cannot be less 1")
	private BigDecimal value;	
}
