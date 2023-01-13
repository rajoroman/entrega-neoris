package com.customerAccount.models.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.customerAccount.services.enums.TypesOfMovements;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Data
public class AccountMovements {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private LocalDate date;
	
	@Enumerated(EnumType.STRING) 
	private TypesOfMovements typeOfMovement;
	
	@Column(scale = 2, nullable = false)
	private BigDecimal value;
	
	@Column(scale = 2, nullable = false)
	private BigDecimal balance;
	
	@JsonBackReference
	@ManyToOne
	private Account account;
	
	private Boolean status;
	
	
	@PrePersist
	private void setPrepersist() {
		this.date = LocalDate.now();
		this.status = Boolean.TRUE;
	}
	
	
}
