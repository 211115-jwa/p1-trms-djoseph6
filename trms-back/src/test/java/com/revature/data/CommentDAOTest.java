package com.revature.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.revature.beans.Comment;
import com.revature.beans.Employee;
import com.revature.beans.Reimbursement;
import com.revature.data.postgres.CommentPostgres;

public class CommentDAOTest {
	private CommentDAO cDAO = new CommentPostgres();
	
	@Test
	public void createCommentPass() {
		Comment input = new Comment();
		Reimbursement inputReim = new Reimbursement(); //This is null in comment bean
		inputReim.setReqId(1);							//commentpostgres calls getReqId
		Employee inputEmp = new Employee();				//same as reimbursement
		inputEmp.setEmpId(1);
		input.setRequest(inputReim);
		input.setApprover(inputEmp);
		int output = cDAO.create(input);
		assertEquals(0, output);
		}
	@Test
	public void createCommentFail() {  //Makes sure when we create a Comment, we do not return another Comments ID
		Comment input = new Comment();
		int inputComId = 3;
		input.setCommentId(inputComId);
		Reimbursement inputReim = new Reimbursement();
		inputReim.setReqId(1);
		Employee inputEmp = new Employee();
		inputEmp.setEmpId(1);
		input.setRequest(inputReim);
		input.setApprover(inputEmp);
		int output = cDAO.create(input);
		assertNotEquals(inputComId, output);
	}
		
	@Test
	public void getCommentWhenIdExists() {
		Comment inputCom = new Comment();
		inputCom.setCommentId(5);
		Reimbursement inputReim = new Reimbursement();
		inputReim.setReqId(1);
		Employee inputEmp = new Employee();
		inputEmp.setEmpId(1);
		inputCom.setRequest(inputReim);
		inputCom.setApprover(inputEmp);
		int input = 5;
		Comment output = cDAO.getById(input);
		assertEquals(inputCom.getCommentId(), output.getCommentId());
		}
	@Test
	public void getCommentWhenIdDoesentExists() {
		Comment inputCom = new Comment();
		inputCom.setCommentId(12);
		//inputCom.setName("Bio");
		//inputCom.setDeptHeadId(3);
		int input = 12;
		Comment output = cDAO.getById(input);
		assertNull(output);
		}
	@Test
	public void getSetCommentWhenGetAll() {
		Set<Comment> output = cDAO.getAll();
		assertNotNull(output);
		}
	@Test
	public void getSetCommentsWhenReqIdExists() {
		int inputId = 1;
		Set<Comment> output = cDAO.getByRequestId(inputId);
		assertFalse(output.isEmpty());
	}
	@Test
	public void getSetCommentsWhenReqIdDoesntExists() {
		int inputId = 12;
		Set<Comment> output = cDAO.getByRequestId(inputId);
		assertTrue(output.isEmpty());
	}
}
