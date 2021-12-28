package com.revature.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.revature.beans.Department;
import com.revature.data.postgres.DepartmentPostgres;

public class DepartmentDAOTest {
	private DepartmentDAO dDAO = new DepartmentPostgres();
	

	@Test
	public void getByIdWhenIdExists() {
		Department depInput = new Department();
		depInput.setDeptId(1);
		depInput.setName("Biology");
		depInput.setDeptHeadId(3);
		int input = 0;
		Department output = dDAO.getById(input);
		assertEquals(depInput, output.getDeptId());
	}

}
