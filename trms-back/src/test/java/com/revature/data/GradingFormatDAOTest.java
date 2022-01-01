package com.revature.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.revature.beans.GradingFormat;
import com.revature.data.postgres.GradingFormatPostgres;

public class GradingFormatDAOTest {
	private GradingFormatDAO gfDAO = new GradingFormatPostgres();
	
	@Test
	public void getGradingFormatWhenIdExists() {
		int inputId = 1;
		GradingFormat output = gfDAO.getById(inputId);
		assertNotNull(output);
	}
	@Test
	public void getGradingFormatWhenIdDoesntExists() {
		int inputId = 12;
		GradingFormat output = gfDAO.getById(inputId);
		assertNull(output);
	}
	@Test
	public void getSetGradingFormatWithGetAll() {
		Set<Object> output = gfDAO.getAll();
		assertNotNull(output);
	}
	@Test 
	public void getSetGradingFormatWhenNameExists() {
		String inputName = "name";
		Set<GradingFormat> output = gfDAO.getByName(inputName);
		assertFalse(output.isEmpty());
	}
	@Test 
	public void getSetGradingFormatWhenNameDoesntExists() {
		String inputName = "Friday";
		Set<GradingFormat> output = gfDAO.getByName(inputName);
		assertTrue(output.isEmpty());
	}
	@Test
	public void getIdWhenCreatePass() {
		GradingFormat inputGF = new GradingFormat();
		int output = gfDAO.create(inputGF);
		assertEquals(4, output);
	}
	@Test
	public void getIdWhenCreateFail() {
		GradingFormat inputGF = new GradingFormat();
		inputGF.setFormatId(7);
		int output = gfDAO.create(inputGF);
		assertNotEquals(1, output);
	}

}
