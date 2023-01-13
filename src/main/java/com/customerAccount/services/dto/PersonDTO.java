package com.customerAccount.services.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonDTO {
	private Long id;
	@NotNull(message = "Id Person cannot be empty or null.")
	private String idPerson;
	@NotNull(message = "Name cannot be empty or null.")
	private String name;
	@NotNull(message = "Gender cannot be empty or null.")
	private GenderDTO gender;
	@NotNull(message = "Age cannot be empty or null.")
	private Integer ega;
	@NotNull(message = "Address cannot be empty or null.")
	private String addres;
	@NotNull(message = "Number Phone cannot be empty or null.")
	private String numberPhone;
}
