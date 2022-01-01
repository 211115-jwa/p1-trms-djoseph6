package com.revature.data.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.revature.beans.Role;
import com.revature.data.RoleDAO;
import com.revature.utils.ConnectionUtil;

public class RolePostgres implements RoleDAO{
	private ConnectionUtil connUtil = ConnectionUtil.getConnectionUtil();

	@Override
	public Role getById(int id) {
		Role role = new Role();
		try (Connection conn = connUtil.getConnection()) {
			String sql = "select * from role where role_id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);
			
			ResultSet resultSet = pStmt.executeQuery();
			
			if (resultSet.next()) {
				role = new Role();
				role.setRoleId(id);
				role.setName(resultSet.getString("Role_name"));
				
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return role;
	}

	@Override
	public Set<Role> getAll() {
		Set<Role> rolees = new HashSet<>();
		try (Connection conn = connUtil.getConnection()) {
			String sql = "select * from role";
			Statement stmt = conn.createStatement();
			
			ResultSet resultSet = stmt.executeQuery(sql);
			
			while (resultSet.next()) {
				Role role = new Role();
				role.setRoleId(resultSet.getInt("role_id"));
				role.setName(resultSet.getString("role_name"));
				
				rolees.add(role);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rolees;
	}

	@Override
	public Set<Role> getByName(String name) {
		Set<Role> rolees = new HashSet<>();
		try (Connection conn = connUtil.getConnection()) {
			String sql = "select * from role where role_name=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, name);
			
			ResultSet resultSet = pStmt.executeQuery();
			
			while (resultSet.next()) {
				Role role = new Role();
				role.setRoleId(resultSet.getInt("role_id"));
				role.setName(resultSet.getString("role_name"));
				
				rolees.add(role);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rolees;
	}

	@Override
	public int create(Role dataToAdd) {
		int generatedId=0;
		try (Connection conn = connUtil.getConnection()) {
			conn.setAutoCommit(false);
			String[] keys = {"format_id"};
			String sql="insert into role"
					+ " (role_id,"
					+ " role_name,"
					+ " approver)"
					+ " values (?,?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql,keys);
			pStmt.setInt(1, dataToAdd.getRoleId());
			pStmt.setString(2, dataToAdd.getName());
			
			
			pStmt.executeUpdate();
			ResultSet generatedKeys = pStmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				generatedId = generatedKeys.getInt(1);
				conn.commit();
			} else {
				conn.rollback();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return generatedId;
	}

}
