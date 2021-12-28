package com.revature.data;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import com.revature.beans.Employee;
import com.revature.data.postgres.EmployeePostgres;

public class EmployeeDAOTest1 {
private EmployeeDAO eDAO = new EmployeePostgres();

@Test
public void createEmployeeSuccess() {
	Employee input = new Employee();
	Employee inputSupervisor = new Employee();
	input.setSupervisor(inputSupervisor);
	int output = eDAO.create(input);
	assertEquals(0, output);
	}
@Test
public void createEmployeeFail() {
	Employee input = new Employee();
	Employee inputSupervisor = new Employee();
	input.setSupervisor(inputSupervisor);
	int output = eDAO.create(input);
	int a = 1;
	assertNotEquals(a, output);
	}
}
