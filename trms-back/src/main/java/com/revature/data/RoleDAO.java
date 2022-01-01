package com.revature.data;

import java.util.Set;

import com.revature.beans.Role;


public interface RoleDAO {
	public Role getById(int id);
	public Set<Role> getAll();
	public Set<Role> getByName(String name);
	public int create(Role dataToAdd);

}
