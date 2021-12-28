package com.revature.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.revature.beans.Department;
import com.revature.data.postgres.DepartmentPostgres;

public class DepartmentDAOTest {
	private DepartmentDAO dDAO = new DepartmentPostgres();
	Department depInput = new Department();

	@Test
	public void getByIdWhenIdExists() {
		int input = 0;
		Department output = dDAO.getById(input);
		assertEquals(depInput, output.getDeptId());
	}

}
