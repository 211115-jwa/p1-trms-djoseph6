package com.revature.data;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.revature.beans.Employee;
import com.revature.data.postgres.EmployeePostgres;

public class EmployeeDAOTest1 {
private static EmployeeDAO eDAO = new EmployeePostgres();

@Test
public void createEmployeeSuccess() {
	Employee input = new Employee();
	Employee inputSupervisor = new Employee(); //Supervisor employee to be input into input
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
@Test
public void getEmployeeWhenIdExist() {
	int inputId = 1;
	Employee output = eDAO.getById(inputId);
	assertNotNull(output);
	}
@Test
public void getEmployeeWhenIdDoesntExist() {
	int inputId = 12;
	Employee output = eDAO.getById(inputId);
	assertNull(output);
	}
@Test
public void getSetEmployeeWithGetAll() {
	Set<Employee> output = eDAO.getAll();
	assertNotNull(output);
}
@Test
public void updateEmployeeWhenEmployeeIDExists() {
	int inputEmpId = 1;
	String inputFN = "Danny";
	String inputUN = "johndoe";
	Employee input = new Employee();
	Employee supervisor = new Employee();
	input.setSupervisor(supervisor);
	input.setEmpId(inputEmpId);
	input.setFirstName(inputFN);
	input.setUsername(inputUN); //adding username for username test
	eDAO.update(input);
	assertEquals(inputFN, eDAO.getById(inputEmpId).getFirstName());
	
	}
@Test
public void updateEmployeeWhenEmployeeIDDoesntExists() {
	int inputEmpId = 12;		//invalid empid
	String inputFN = "Danny";
	Employee input = new Employee();
	Employee supervisor = new Employee();
	input.setSupervisor(supervisor);
	input.setEmpId(inputEmpId);
	input.setFirstName(inputFN);
	eDAO.update(input);				
	assertNull(eDAO.getById(inputEmpId));
	}
@Test
public void getEmployeeWhenUsernameExists() {
	String inputFN = "johndoe";
	Employee input = new Employee();
	Employee supervisor = new Employee();
	input.setSupervisor(supervisor);
	input.setFirstName(inputFN);
	input.setEmpId(1);		//valid empid
	Employee output = eDAO.getByUsername(inputFN);
	assertNotNull(output);
	}
@Test
public void getEmployeeWhenUsernameDoesntExists() {
	String inputFN = "johndoe1";
	Employee input = new Employee();
	Employee supervisor = new Employee();
	input.setSupervisor(supervisor);
	input.setFirstName(inputFN);
	input.setEmpId(1);	//valid empid
	Employee output = eDAO.getByUsername(inputFN);
	assertNull(output);
	}


}
