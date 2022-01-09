package com.revature.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.revature.beans.Department;
import com.revature.data.postgres.DepartmentPostgres;

public class DepartmentDAOTest {
	private DepartmentDAO dDAO = new DepartmentPostgres();
	
@Test
public void createDepartmentPass() {
	Department input = new Department();
	int output = dDAO.create(input);
	assertEquals(0, output);
	}
@Test
public void createDepartmentFail() {  //Makes sure when we create a department, we do not return another departments ID
	Department input = new Department();
	int inputDeptId = 3;
	input.setDeptId(inputDeptId);
	int output = dDAO.create(input);
	assertNotEquals(inputDeptId, output);
}
	
@Test
public void getDepartmentWhenIdExists() {
	Department depInput = new Department();
	depInput.setDeptId(1);
	//depInput.setName("Bio");
	//depInput.setDeptHeadId(3);
	int input = 1;
	Department output = dDAO.getById(input);
	assertEquals(depInput.getDeptId(), output.getDeptId());
	}
@Test
public void getDepartmentWhenIdDoesentExists() {
	Department depInput = new Department();
	depInput.setDeptId(12);
	//depInput.setName("Bio");
	//depInput.setDeptHeadId(3);
	int input = 12;
	Department output = dDAO.getById(input);
	assertNull(output);
	}
@Test
public void getSetDepartmentWhenGetAll() {
	Set<Department> output = dDAO.getAll();
	assertNotNull(output);
	}
@Test
public void getSetDepartmentWhenNameExists() {
	String inputDeptName = "Bio";
	Set<Department> output = dDAO.getByName(inputDeptName);
	assertNotNull(output);
	}
@Test
public void getSetDepartmentWhenNameDoesntExists() {
	String inputDeptNameInvalid = "Chemistry";
	Set<Department> outputInvalid = dDAO.getByName(inputDeptNameInvalid); //returns an empty array. Java considers this not null
	assertTrue(outputInvalid.isEmpty());
	}
}
