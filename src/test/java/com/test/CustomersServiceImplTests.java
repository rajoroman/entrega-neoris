package com.test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.customerAccount.TestApplication;
import com.customerAccount.models.entity.Customer;
import com.customerAccount.models.entity.Gender;
import com.customerAccount.models.repository.CustomerRepository;
import com.customerAccount.services.dto.CustomerDTO;
import com.customerAccount.services.dto.GenderDTO;
import com.customerAccount.services.impl.CustomerServiceImpl;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
class CustomersServiceImplTests {

	@InjectMocks
	CustomerServiceImpl subject;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@Test
	void saveCustomerTest() throws Exception {
		List<CustomerDTO> customerDTOlist = new ArrayList<>();
		List<Customer> customerlist = new ArrayList<>();
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setAddres("addresTest");
		customerDTO.setCustomerId("123456");
		customerDTO.setEga(40);
		customerDTO.setGender(new GenderDTO(1L,""));
		customerDTO.setId(1L);
		customerDTO.setIdPerson("987654");
		customerDTO.setName("TestName");
		customerDTO.setNumberPhone("99887766");
		customerDTO.setPassword("123456");
		customerDTO.setStatus(Boolean.TRUE);
		customerDTOlist.add(customerDTO);
		
		Customer customer = new Customer();
		customer.setAddres("addresTest");
		customer.setCustomerId("123456");
		customer.setEga(40);
		customer.setGender(new Gender(1L,""));
		customer.setId(1L);
		customer.setIdPerson("987654");
		customer.setName("TestName");
		customer.setNumberPhone("99887766");
		customer.setPassword("123456");
		customer.setStatus(Boolean.TRUE);
		customerlist.add(customer);
		
		when(customerRepository.saveAll(any())).thenReturn(customerlist);
		
		List<CustomerDTO> result = subject.save(customerDTOlist);
		assertNotNull(result,"Must be not null");
		assertTrue(result.size() > 0);
		
	}
	
	@Test
	void saveCustomerExceptionTest() throws Exception {
		List<CustomerDTO> customerDTOlist = new ArrayList<>();
		List<Customer> customerlist = new ArrayList<>();
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setAddres("addresTest");
		customerDTO.setCustomerId("123456");
		customerDTO.setEga(40);
		customerDTO.setGender(new GenderDTO(1L,""));
		customerDTO.setId(1L);
		customerDTO.setIdPerson("987654");
		customerDTO.setName("TestName");
		customerDTO.setNumberPhone("99887766");
		customerDTO.setPassword("123456");
		customerDTO.setStatus(Boolean.TRUE);
		customerDTOlist.add(customerDTO);
		
		Customer customer = new Customer();
		customer.setAddres("addresTest");
		customer.setCustomerId("123456");
		customer.setEga(40);
		customer.setGender(new Gender(1L,""));
		customer.setId(1L);
		customer.setIdPerson("987654");
		customer.setName("TestName");
		customer.setNumberPhone("99887766");
		customer.setPassword("123456");
		customer.setStatus(Boolean.TRUE);
		customerlist.add(customer);
		
		when(customerRepository.saveAll(any())).thenThrow(new DataIntegrityViolationException("Error transaction"));

		RuntimeException exception = assertThrows(RuntimeException.class,
	            ()->{subject.save(customerDTOlist);} );
		
	}	
}
