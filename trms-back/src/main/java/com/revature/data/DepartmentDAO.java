package com.revature.data;

import java.util.Set;

import com.revature.beans.Department;

public interface DepartmentDAO {
	public int create(Department dataToAdd);
	public Department getById(int id);
	public Set<Department> getAll();
	public Set<Department> getByName(String name);
}
