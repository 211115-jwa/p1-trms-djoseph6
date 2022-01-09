package com.revature.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.revature.beans.Role;
import com.revature.data.postgres.RolePostgres;

public class RoleDAOTest {
	private RoleDAO rDAO = new RolePostgres();

	@Test
	public void getRoleWhenIdExists() {
		int inputId = 1;
		Role output = rDAO.getById(inputId);
		assertNotNull(output);
	}
	@Test
	public void getRoleWhenIdDoesntExists() {
		int inputId = 120;
		Role output = rDAO.getById(inputId);
		assertNull(output);
	}
	@Test
	public void getSetRoleWithGetAll() {
		Set<Role> output = rDAO.getAll();
		assertNotNull(output);
	}
	@Test 
	public void getSetRoleWhenNameExists() {
		String inputName = "Employee";
		Set<Role> output = rDAO.getByName(inputName);
		assertFalse(output.isEmpty());
	}
	@Test 
	public void getSetRoleWhenNameDoesntExists() {
		String inputName = "Friday";
		Set<Role> output = rDAO.getByName(inputName);
		assertTrue(output.isEmpty());
	}
	@Test
	public void getIdWhenCreatePass() {
		Role inputRole = new Role();
		int output = rDAO.create(inputRole);
		assertEquals(0, output);
	}
	@Test
	public void getIdWhenCreateFail() {
		Role inputRole = new Role();
		inputRole.setRoleId(7);
		int output = rDAO.create(inputRole);
		assertNotEquals(1, output);
	}
}
