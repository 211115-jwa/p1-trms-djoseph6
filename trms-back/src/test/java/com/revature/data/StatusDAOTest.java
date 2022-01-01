package com.revature.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.revature.beans.Status;
import com.revature.data.postgres.StatusPostgres;

public class StatusDAOTest {
	private StatusDAO sDAO = new StatusPostgres();
	
	@Test
	public void getStatusWhenIdExists() {
		int inputId = 1;
		Status output = sDAO.getById(inputId);
		assertNotNull(output);
	}
	@Test
	public void getStatusWhenIdDoesntExists() {
		int inputId = 120;
		Status output = sDAO.getById(inputId);
		assertNull(output);
	}
	@Test
	public void getSetStatusWithGetAll() {
		Set<Status> output = sDAO.getAll();
		assertNotNull(output);
	}
	@Test 
	public void getSetStatusWhenNameExists() {
		String inputName = "Unapproved";
		Set<Status> output = sDAO.getByName(inputName);
		assertFalse(output.isEmpty());
	}
	@Test 
	public void getSetStatusWhenNameDoesntExists() {
		String inputName = "Friday";
		Set<Status> output = sDAO.getByName(inputName);
		assertTrue(output.isEmpty());
	}
	@Test
	public void getIdWhenCreatePass() {
		Status inputStat = new Status();
		int output = sDAO.create(inputStat);
		assertEquals(0, output);
	}
	@Test
	public void getIdWhenCreateFail() {
		Status inputStat = new Status();
		inputStat.setStatusId(7);
		int output = sDAO.create(inputStat);
		assertNotEquals(1, output);
	}
}
