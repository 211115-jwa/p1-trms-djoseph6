package com.revature.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.revature.beans.Employee;
import com.revature.beans.Reimbursement;
import com.revature.beans.Role;
import com.revature.beans.Status;
import com.revature.data.postgres.ReimbursementPostgres;

public class ReimbursementDAOTest1 {
	private ReimbursementDAO rDAO = new ReimbursementPostgres();
	
	
	@Test
	public void getIdWhenCreatePass() {
		Reimbursement inputReim = new Reimbursement();
		Employee inputRequestor = new Employee();
		LocalDate inputDate = LocalDate.of(2021, 01, 01);
		LocalTime inputTime = LocalTime.of(12, 0, 0);
		inputReim.setRequestor(inputRequestor);
		inputReim.setEventDate(inputDate);
		inputReim.setEventTime(inputTime);
		int output = rDAO.create(inputReim);
		assertEquals(0, output);
	}
	@Test
	public void getIdWhenCreateFail() {
		Reimbursement inputReim = new Reimbursement();
		Employee inputRequestor = new Employee();
		LocalDate inputDate = LocalDate.of(2021, 01, 01);
		LocalTime inputTime = LocalTime.of(12, 0, 0);
		inputReim.setRequestor(inputRequestor);
		inputReim.setEventDate(inputDate);
		inputReim.setEventTime(inputTime);
		inputReim.setReqId(7);
		int output = rDAO.create(inputReim);
		assertNotEquals(1, output);
	}
	@Test
	public void getRoleWhenIdExists() {
		int inputId = 1;
		Reimbursement output = rDAO.getById(inputId);
		assertNotNull(output);
	}
	@Test
	public void getRoleWhenIdDoesntExists() {
		int inputId = 120;
		Reimbursement output = rDAO.getById(inputId);
		assertNull(output);
	} 
	@Test
	public void getSetRoleWithGetAll() {
		Set<Reimbursement> output = rDAO.getAll();
		assertNotNull(output);
	}
	@Test
	public void getSetReimbursementWhenRequestorExists() {
		Employee inputEmp = new Employee();
		inputEmp.setEmpId(1);
		Set<Reimbursement> output = rDAO.getByRequestor(inputEmp);
		assertNotNull(output);
	}
	@Test
	public void getSetReimbursementWhenRequestorDoesntExists() {
		Employee inputEmp = new Employee();
		Set<Reimbursement> output = rDAO.getByRequestor(inputEmp);
		assertTrue(output.isEmpty());
	}
	@Test
	public void getSetReimbursementWhenStatusExists() {
		Status input = new Status();
		input.setStatusId(1);
		Set<Reimbursement> output = rDAO.getByStatus(input);
		assertNotNull(output);
	}
	@Test
	public void getSetReimbursementWhenStatusDoesntExists() {
		Status input = new Status();
		input.setStatusId(12);
		Set<Reimbursement> output = rDAO.getByStatus(input);
		assertNotNull(output);
	}
}
