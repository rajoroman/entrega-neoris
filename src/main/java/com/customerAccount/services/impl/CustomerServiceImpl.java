package com.customerAccount.services.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.customerAccount.services.AllCustomerServiceRepository;
import com.customerAccount.services.dto.AccountDTO;
import com.customerAccount.services.dto.AccountMovementsDTO;
import com.customerAccount.services.dto.CustomerDTO;
import com.customerAccount.services.dto.ReportDTO;
import com.customerAccount.services.dto.RequestReportDTO;
import com.customerAccount.services.enums.TypesOfMovements;
import com.customerAccount.services.exceptions.ExceptionCustomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;

import com.customerAccount.models.entity.Customer;
import com.customerAccount.models.repository.CustomerRepository;

@Slf4j
@Service
public class CustomerServiceImpl implements AllCustomerServiceRepository {

	@Autowired
	private CustomerRepository customerRepository;

	@Transactional(readOnly = true)
	@Override
	public CustomerDTO withCustomerId(String customerId) throws Exception {
		Optional<Customer> customerOpt = customerRepository.findByCustomerId(customerId);
		if (customerOpt.isPresent()) {
			List<CustomerDTO> customerDTO = this.toDTO(Arrays.asList(customerOpt.get()));
			return customerDTO.get(0);
		}
		throw new ExceptionCustomService(ExceptionCustomService.CUSTOMER_NOT_FOUND);
	}

	@Transactional(readOnly = true)
	@Override
	public List<ReportDTO> getCustomerToReports(RequestReportDTO requestReportDTO) throws Exception {
		Optional<Customer> customerOpt = customerRepository.findByCustomerId(requestReportDTO.getCustomerId());
		List<ReportDTO> reportDTOList = new ArrayList<>();
		if (customerOpt.isPresent()) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			CustomerDTO customerDTO = mapper.convertValue(customerOpt.get(), CustomerDTO.class);
			Optional<List<AccountDTO>> accountDTOOpt = Optional.of(customerDTO.getAccounts());
			if (accountDTOOpt.isPresent()) {
				accountDTOOpt.get().forEach(accountDTOEmit -> {
					Optional<List<AccountMovementsDTO>> movementsDTOOpt = Optional.of(accountDTOEmit.getMovements());
					if (movementsDTOOpt.isPresent()) {
						List<AccountMovementsDTO> accountMovementsDTOList = new ArrayList<>();
						accountMovementsDTOList = this.filterMovements(movementsDTOOpt.get(),requestReportDTO);
						accountMovementsDTOList.stream().forEach(accountMovEmit -> {
							ReportDTO reportDTO = this.buildReportDTO(customerDTO, accountDTOEmit, accountMovEmit);
							reportDTOList.add(reportDTO);
						});
					}
				});
			}
		} else {
			throw new ExceptionCustomService(ExceptionCustomService.CUSTOMER_NOT_FOUND);
		}
		return reportDTOList;
	}
	
	@Transactional
	@Override
	public List<CustomerDTO> save(List<CustomerDTO> customerDTOlist) throws Exception {
		List<Customer> customersList = this.toEntity(customerDTOlist);
		try {
			customersList = customerRepository.saveAll(customersList);
			return this.toDTO(customersList);
		} catch (DataIntegrityViolationException | JpaSystemException e) {
			log.info("Error transaction", e);
			throw new RuntimeException(e);
		}
	}

	private List<CustomerDTO> toDTO(List<Customer> customerList) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		List<CustomerDTO> result = new ArrayList<>();
		customerList.forEach(customer -> {
			CustomerDTO customerDTO = mapper.convertValue(customer, CustomerDTO.class);
			result.add(customerDTO);
		});
		return result;
	}

	private List<Customer> toEntity(List<CustomerDTO> customerDTOlist) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		List<Customer> result = new ArrayList<>();
		customerDTOlist.forEach(customerDTO -> {
			Customer customer = mapper.convertValue(customerDTO, Customer.class);
			result.add(customer);
		});
		return result;
	}

	private List<AccountMovementsDTO> filterMovements(List<AccountMovementsDTO> accountMovementsDTOList,
			RequestReportDTO requestReportDTO) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateTo = LocalDate.parse(requestReportDTO.getTo().toString(), df);
		LocalDate dateFrom = LocalDate.parse(requestReportDTO.getFrom().toString(), df);

		return accountMovementsDTOList.stream()
				.filter(emit -> (emit.getDate().equals(dateTo) || emit.getDate().equals(dateFrom))
						|| (emit.getDate().isAfter(dateFrom) && emit.getDate().isBefore(dateTo)))
				.sorted((emit1, emit2) -> ((Long) emit2.getId()).compareTo(emit1.getId())).collect(Collectors.toList());
	}

	private ReportDTO buildReportDTO(CustomerDTO customerDTO, AccountDTO accountDTOEmit, AccountMovementsDTO accountMovEmit) {
			ReportDTO reportDTO = new ReportDTO();
			reportDTO.setNameCustomer(customerDTO.getName());
			reportDTO.setInitialBalance(accountDTOEmit.getInitialBalance());
			reportDTO.setAccountNumber(accountDTOEmit.getAccountNumber());
			reportDTO.setTypeAccount(accountDTOEmit.getTypeAccount().getDescription());
			reportDTO.setBalance(accountMovEmit.getBalance());
			reportDTO.setDate(accountMovEmit.getDate());
			reportDTO.setStatus(accountMovEmit.getStatus());
			if (accountMovEmit.getTypeOfMovement().equals(TypesOfMovements.DEBITO))
				reportDTO.setValue(accountMovEmit.getValue().negate());
			else
				reportDTO.setValue(accountMovEmit.getValue());
				return reportDTO;
	}
}
