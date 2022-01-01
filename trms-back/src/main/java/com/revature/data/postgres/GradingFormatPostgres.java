package com.revature.data.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.revature.beans.GradingFormat;
import com.revature.data.GradingFormatDAO;
import com.revature.utils.ConnectionUtil;

public class GradingFormatPostgres implements GradingFormatDAO {
	private ConnectionUtil connUtil = ConnectionUtil.getConnectionUtil();

	@Override
	public GradingFormat getById(int id) {
		GradingFormat format = null;
		try (Connection conn = connUtil.getConnection()) {
			String sql = "select * from grading_format where format_id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);
			
			ResultSet resultSet = pStmt.executeQuery();
			
			if (resultSet.next()) {
				format = new GradingFormat();
				format.setFormatId(id);
				format.setName(resultSet.getString("format_name"));
				format.setExample(resultSet.getString("example"));
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return format;
	}

	@Override
	public Set<Object> getAll() {
		Set<Object> formats = new HashSet<>();
		try (Connection conn = connUtil.getConnection()) {
			String sql = "select * from grading_format";
			Statement stmt = conn.createStatement();
			
			ResultSet resultSet = stmt.executeQuery(sql);
			
			while (resultSet.next()) {
				GradingFormat format = new GradingFormat();
				format.setFormatId(resultSet.getInt("format_id"));
				format.setName(resultSet.getString("format_name"));
				format.setExample(resultSet.getString("example"));
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return formats;
	}

	@Override
	public Set<GradingFormat> getByName(String name) {
		Set<GradingFormat> formats = new HashSet<>();
		try (Connection conn = connUtil.getConnection()) {
			String sql = "select * from grading_format where format_name=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, name);
			
			ResultSet resultSet = pStmt.executeQuery();
			
			while (resultSet.next()) {
				GradingFormat format = new GradingFormat();
				format.setFormatId(resultSet.getInt("format_id"));
				format.setName(resultSet.getString("format_name"));
				format.setExample(resultSet.getString("example"));
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return formats;
	}

	@Override
	public int create(GradingFormat dataToAdd) {
		int generatedId=0;
		try (Connection conn = connUtil.getConnection()) {
			conn.setAutoCommit(false);
			String[] keys = {"format_id"};
			String sql="insert into grading_format"
					+ " (format_id,"
					+ " format_name,"
					+ " example)"
					+ " values (?,?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql,keys);
			pStmt.setInt(1, dataToAdd.getFormatId());
			pStmt.setString(2, dataToAdd.getName());
			pStmt.setString(3, dataToAdd.getExample());
			
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
