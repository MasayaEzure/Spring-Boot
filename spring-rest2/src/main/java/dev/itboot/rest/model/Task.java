package dev.itboot.rest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Task {

	@Id
	@GeneratedValue
	private Long id;

	@NotBlank
	@Size(max = 255)
	private String name;

	@NotNull
	private Boolean completed = false;
}
