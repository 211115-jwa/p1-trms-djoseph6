package com.revature.data.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.revature.beans.Status;
import com.revature.data.StatusDAO;
import com.revature.utils.ConnectionUtil;

public class StatusPostgres implements StatusDAO {
	private ConnectionUtil connUtil = ConnectionUtil.getConnectionUtil();

	@Override
	public Status getById(int id) {
		Status status = new Status();
		try (Connection conn = connUtil.getConnection()) {
			String sql = "select * from status where status_id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);
			
			ResultSet resultSet = pStmt.executeQuery();
			
			if (resultSet.next()) {
				status = new Status();
				status.setStatusId(id);
				status.setName(resultSet.getString("status_name"));
				status.setApprover(resultSet.getString("approver"));
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public Set<Status> getAll() {
		Set<Status> statuses = new HashSet<>();
		try (Connection conn = connUtil.getConnection()) {
			String sql = "select * from status";
			Statement stmt = conn.createStatement();
			
			ResultSet resultSet = stmt.executeQuery(sql);
			
			while (resultSet.next()) {
				Status status = new Status();
				status.setStatusId(resultSet.getInt("status_id"));
				status.setName(resultSet.getString("status_name"));
				status.setApprover(resultSet.getString("approver"));
				statuses.add(status);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statuses;
	}

	@Override
	public Set<Status> getByName(String name) {
		Set<Status> statuses = new HashSet<>();
		try (Connection conn = connUtil.getConnection()) {
			String sql = "select * from status where status_name=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, name);
			
			ResultSet resultSet = pStmt.executeQuery();
			
			while (resultSet.next()) {
				Status status = new Status();
				status.setStatusId(resultSet.getInt("status_id"));
				status.setName(resultSet.getString("status_name"));
				status.setApprover(resultSet.getString("approver"));
				statuses.add(status);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statuses;
	}

	@Override
	public int create(Status dataToAdd) {
		int generatedId=0;
		try (Connection conn = connUtil.getConnection()) {
			conn.setAutoCommit(false);
			String[] keys = {"format_id"};
			String sql="insert into status"
					+ " (status_id,"
					+ " status_name,"
					+ " approver)"
					+ " values (?,?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql,keys);
			pStmt.setInt(1, dataToAdd.getStatusId());
			pStmt.setString(2, dataToAdd.getName());
			pStmt.setString(3, dataToAdd.getApprover());
			
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
