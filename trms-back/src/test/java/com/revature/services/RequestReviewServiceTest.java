package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.beans.Department;
import com.revature.beans.Employee;
import com.revature.beans.Reimbursement;
import com.revature.beans.Status;
import com.revature.data.CommentDAO;
import com.revature.data.EmployeeDAO;
import com.revature.data.EventTypeDAO;
import com.revature.data.GradingFormatDAO;
import com.revature.data.ReimbursementDAO;
import com.revature.data.StatusDAO;
import com.revature.utils.DAOFactory;

@ExtendWith(MockitoExtension.class)
public class RequestReviewServiceTest {
	@Mock
	private EventTypeDAO eventTypeDao = DAOFactory.getEventTypeDAO();
	@Mock
	private GradingFormatDAO gradFormatDao = DAOFactory.getGradingFormatDAO();
	@Mock
	private StatusDAO statusDao = DAOFactory.getStatusDAO();
	@Mock
	private ReimbursementDAO reqDao = DAOFactory.getReimbursementDAO();
	@Mock
	private CommentDAO commentDao = DAOFactory.getCommentDAO();
	@Mock
	private EmployeeDAO empDao = DAOFactory.getEmployeeDAO();
	
	@InjectMocks
	RequestReviewService rrServ = new RequestReviewServiceImpl();
	
	@BeforeAll
	public static void beforeAllTests() {
	Set<Reimbursement> inputSetReimbursement = new HashSet<Reimbursement>();
	String approver1 = "direct supervisor";
	String approver2 = "department head";
	String approver3 = "benefits coordinator";
	for(int a = 0; a<6; a++) {
		Reimbursement r = new Reimbursement();
		Status status = new Status();
		r.setStatus(status);
		r.getStatus().setApprover(approver1);
		Employee emp = new Employee();
		Employee supervisor = new Employee();
		emp.setEmpId(a);
		supervisor.setEmpId(a);
		emp.setSupervisor(supervisor);
		r.setRequestor(emp);
		Department dep = new Department();
		r.getRequestor().getSupervisor().setDepartment(dep);
		r.getRequestor().getSupervisor().getDepartment().setDeptId(a);
		
		inputSetReimbursement.add(r);
		}
	}
	
	@Test
	public void getSetReimbursementWithEmployeeApprover() {
		Employee inputApprover = new Employee();
		inputApprover.setEmpId(2);
		Set<Reimbursement> inputSetReimbursement = new HashSet<Reimbursement>();
		when(reqDao.getAll()).thenReturn(inputSetReimbursement);
		System.out.println(inputSetReimbursement);
		Set<Reimbursement> outputSetReimbursement = rrServ.getPendingReimbursements(inputApprover);
		
		//assertFalse(outputSetReimbursement.isEmpty());
		assertFalse(outputSetReimbursement.isEmpty());
	}
	
}
