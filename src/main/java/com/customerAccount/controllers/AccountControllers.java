package com.customerAccount.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.customerAccount.services.AllAccountServiceRepository;
import com.customerAccount.services.dto.AccountDTO;
import com.customerAccount.services.dto.NewAccountDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@Validated
@RestController
@RequestMapping("accounts")
public class AccountControllers {
	
	@Autowired
	private AllAccountServiceRepository accountService;
	
	@PostMapping
	public ResponseEntity<?> saveCustomer(
			@RequestBody 
			@NotEmpty(message = "Input account list cannot be empty.")
			List<@Valid NewAccountDTO> newAcountDTOlist) throws Exception {
		List<AccountDTO> accountSave = accountService.save(newAcountDTOlist);	
		return ResponseEntity.status(HttpStatus.CREATED).body(accountSave);
	}

}
