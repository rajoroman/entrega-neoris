package com.customerAccount.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.customerAccount.services.AllAccountServiceRepository;
import com.customerAccount.services.AllAccountTypeServiceRepository;
import com.customerAccount.services.AllCustomerServiceRepository;
import com.customerAccount.services.dto.AccountDTO;
import com.customerAccount.services.dto.AccountTypeDTO;
import com.customerAccount.services.dto.CustomerDTO;
import com.customerAccount.services.dto.NewAccountDTO;
import com.customerAccount.services.exceptions.ExceptionCustomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

import com.customerAccount.models.entity.Account;
import com.customerAccount.models.repository.AccountRepository;



@Slf4j
@Service
public class AccountServiceImpl implements AllAccountServiceRepository {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AllCustomerServiceRepository customerService;

	@Autowired
	private AllAccountTypeServiceRepository accountTypeService;

	@Transactional(readOnly = true)
	@Override
	public AccountDTO withAccountNumber(String accountNumber) throws Exception {
		Optional<Account> accountOpt = accountRepository.findByAccountNumber(accountNumber);
		if (accountOpt.isPresent()) {
			List<AccountDTO> accountDTOlist = this.toDTO(Arrays.asList(accountOpt.get()));
			return accountDTOlist.get(0);
		}
		throw new ExceptionCustomService(ExceptionCustomService.CUSTOMER_NOT_FOUND);
	}

	@Transactional
	@Override
	public List<AccountDTO> save(List<NewAccountDTO> newAccountDTOlist) throws Exception{
		List<AccountDTO> accountListDTO = new ArrayList<>();
		try {
			newAccountDTOlist.forEach(newAccount -> {
					try {
						CustomerDTO customerDTO = this.getCustomer(newAccount.getCustomerId());
						AccountTypeDTO typeAccountDTO = accountTypeService.withId(newAccount.getTypeAccount());
						AccountDTO accountDTO = new AccountDTO();
						accountDTO.setCustomer(customerDTO);
						accountDTO.setTypeAccount(typeAccountDTO);
						accountDTO.setAccountNumber(newAccount.getAccountNumber());
						accountDTO.setInitialBalance(newAccount.getInitialBalance());
						accountDTO.setStatus(newAccount.getStatus());
						accountListDTO.add(accountDTO);
					} catch (Exception e) {
						log.info("error",e);
						throw new RuntimeException(e);
					} 
			});
			List<Account> accountList = this.toEntity(accountListDTO);
			accountList = accountRepository.saveAll(accountList);
			return this.toDTO(accountList);
		} catch (DataIntegrityViolationException | JpaSystemException e) {
			log.info("Error transaction", e);
			throw new RuntimeException(e);
		}
	}

	private List<AccountDTO> toDTO(List<Account> accountList) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		List<AccountDTO> result = new ArrayList<>();
		accountList.forEach(account -> {
			CustomerDTO customerDTO = new CustomerDTO();
			AccountDTO  accountDTO = new AccountDTO();
			accountDTO = mapper.convertValue(account, AccountDTO.class);
			customerDTO = mapper.convertValue(account.getCustomer(), CustomerDTO.class);
			accountDTO.setCustomer(customerDTO);
			result.add(accountDTO);
		});
		return result;
	}

	private List<Account> toEntity(List<AccountDTO> accountDTOlist) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		List<Account> result = new ArrayList<>();
		accountDTOlist.forEach(accountDTO -> {
			Account account = mapper.convertValue(accountDTO, Account.class);
			result.add(account);

		});
		return result;
	}

	private CustomerDTO getCustomer(String customerId) throws ExceptionCustomService  {
		CustomerDTO customerDTO = new CustomerDTO();
		try {
			customerDTO = customerService.withCustomerId(customerId);
		} catch (Exception e) {
			throw new ExceptionCustomService(ExceptionCustomService.CUSTOMER_NOT_FOUND);
		}
		return customerDTO;
	}
}
