package dev.itboot.mb.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Teacher {
	private Long   id;

	@NotBlank
	@Size(max = 60)
	private String userName;

	@NotBlank
	@Email
	@Size(max = 254)
	private String email;
}
