package com.revature.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.revature.beans.EventType;
import com.revature.data.postgres.EventTypePostgres;

public class EventTypeDAOTest {
	private EventTypeDAO etDAO = new EventTypePostgres();
	
	@Test
	public void getEventTypeWhenIdExists() {
		int inputId = 1;
		EventType output = etDAO.getById(inputId);
		assertNotNull(output);
	}
	@Test
	public void getEventTypeWhenIdDoesntExists() {
		int inputId = 12;
		EventType output = etDAO.getById(inputId);
		assertNull(output);
	}
	@Test
	public void getSetEventTypeWithGetAll() {
		Set<Object> output = etDAO.getAll();
		assertNotNull(output);
	}
	@Test 
	public void getSetEventTypeWhenNameExists() {
		String inputName = "Event";
		Set<EventType> output = etDAO.getByName(inputName);
		assertFalse(output.isEmpty());
	}
	@Test 
	public void getSetEventTypeWhenNameDoesntExists() {
		String inputName = "Friday";
		Set<EventType> output = etDAO.getByName(inputName);
		assertTrue(output.isEmpty());
	}
	@Test
	public void getIdWhenCreatePass() {
		EventType inputET = new EventType();
		int output = etDAO.create(inputET);
		assertNotNull(output);
	}
	@Test
	public void getIdWhenCreateFail() {
		EventType inputET = new EventType();
		inputET.setEventId(7);
		int output = etDAO.create(inputET);
		assertNotEquals(6, output);
	}
}
