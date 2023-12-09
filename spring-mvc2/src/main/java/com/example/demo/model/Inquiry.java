package com.example.demo.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Inquiry {
	@NotBlank
	@Size(max = 60)
	private String name;

	@NotBlank
	@Email
	@Size(max = 254)
	private String email;

	@NotBlank
	@Size(max = 500)
	private String inquiry;
}
