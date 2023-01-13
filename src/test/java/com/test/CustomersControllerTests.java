package com.test;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.customerAccount.TestApplication;
import com.customerAccount.controllers.CustomerControllers;
import com.customerAccount.services.AllCustomerServiceRepository;
import com.customerAccount.services.dto.CustomerDTO;
import com.customerAccount.services.dto.GenderDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.ConstraintViolationException;
import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = TestApplication.class)
class CustomersControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
	CustomerControllers subject;
	
	@MockBean
	private AllCustomerServiceRepository serviceCustomer;
	
	@Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(subject).build();
    }
	
	@Test
	void saveCustomerTest() throws Exception {
		ObjectMapper objectmapper = new ObjectMapper(); 
		List<CustomerDTO> customerDTOlist = new ArrayList<>();
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
		mockMvc.perform(post("/customers")
	                    .content(objectmapper.writeValueAsString(customerDTOlist))
	                    .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isCreated())
	            .andReturn()
	            .getResponse();
		
	}
	
	@Test()
	void saveCustomerExceptionTest() throws Exception {
		ObjectMapper objectmapper = new ObjectMapper(); 
		List<CustomerDTO> customerDTOlist = new ArrayList<>();
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setAddres(null);
		customerDTO.setCustomerId(null);
		customerDTO.setEga(40);
		customerDTO.setGender(new GenderDTO(1L,""));
		customerDTO.setId(1L);
		customerDTO.setIdPerson(null);
		customerDTO.setName("TestName");
		customerDTO.setNumberPhone("99887766");
		customerDTO.setPassword("123456");
		customerDTO.setStatus(Boolean.TRUE);
		customerDTOlist.add(customerDTO);
		mockMvc.perform(post("/customers")
	                    .content(objectmapper.writeValueAsString(customerDTOlist))
	                    .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isBadRequest())
	            .andReturn()
	            .getResponse();
		
	}

}
