package com.example.demo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	Collection<Employee> findByNameLike(String name);
}
