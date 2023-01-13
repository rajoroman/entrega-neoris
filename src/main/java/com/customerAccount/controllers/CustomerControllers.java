package com.customerAccount.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customerAccount.services.AllCustomerServiceRepository;
import com.customerAccount.services.dto.CustomerDTO;
import com.customerAccount.services.dto.ReportDTO;
import com.customerAccount.services.dto.RequestReportDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@Validated
@RestController
@RequestMapping("customers")
public class CustomerControllers {
	
	@Autowired
	private AllCustomerServiceRepository serviceCustomer;	
	
	@PostMapping
	public ResponseEntity<?> saveCustomer(
			@RequestBody
			@NotEmpty(message = "Input customer list cannot be empty.")
			List<@Valid CustomerDTO> customerDTOlist) throws Exception {
		List<CustomerDTO> customerSave = serviceCustomer.save(customerDTOlist);	
		return ResponseEntity.status(HttpStatus.CREATED).body(customerSave);
	}
	
	@GetMapping("/{customerId}")
	public ResponseEntity<?> getCustomer(@PathVariable String customerId) throws Exception {
		CustomerDTO customerDTO = serviceCustomer.withCustomerId(customerId);	
		return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
	}
	
	@GetMapping("/reports")
	public ResponseEntity<?> getReport(@RequestBody @Valid RequestReportDTO requestReportDTO ) throws Exception {
		List<ReportDTO> customerReport = serviceCustomer.getCustomerToReports(requestReportDTO);	
		return ResponseEntity.status(HttpStatus.OK).body(customerReport);
	}

}
