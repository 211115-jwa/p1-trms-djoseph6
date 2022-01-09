package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.beans.Comment;
import com.revature.beans.Employee;
import com.revature.beans.EventType;
import com.revature.beans.GradingFormat;
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
public class EmployeeServiceTest {
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
	EmployeeService empServ = new EmployeeServiceImpl();
	
	
	@Test
	public void getEventTypeAndGradingFormatSetsWithGetRequest() {
		Set<Object> inputMockETSet =  new HashSet<Object>(); 	//Creating Set to be returned by DAO
			for(int a = 0; a<5; a++) {
				EventType eventType = new EventType();
				eventType.setEventId(a);
				inputMockETSet.add(eventType);
			}
		Set<Object> inputMockGFSet = new HashSet<Object>();	
			for(int a = 0; a<5; a++) {
				GradingFormat gradingFormat = new GradingFormat();	//Creating Set to be returned by DAO
				gradingFormat.setFormatId(a);
				inputMockGFSet.add(gradingFormat);
			}
			EventType eventType = new EventType();
			eventType.setEventId(2);
			
			String mapETString = "eventTypes";
			String mapGFString = "gradingFormats";
		
		when(eventTypeDao.getAll()).thenReturn(inputMockETSet);
		when(gradFormatDao.getAll()).thenReturn(inputMockGFSet);
		Map<String, Set<Object>> output = empServ.getRequestOptions();
		//System.out.println(output.values());
		
		assertFalse(output.values().isEmpty());
		assertTrue(output.containsValue(inputMockETSet));
		assertTrue(output.containsValue(inputMockGFSet));
		assertTrue(output.containsKey(mapETString));
		assertTrue(output.containsKey(mapGFString));
		assertNotNull(output.get(mapETString));
	}
	@Test
	public void getAllEventTypeWhenEventTypeIsString() {
		Set<Object> inputMockETSet =  new HashSet<Object>(); 	//Creating Set to be returned by DAO
		Set<Object> inputMockGFSet = new HashSet<Object>();	
		
		String mapETString = "eventTypes";
		String mapGFString = "gradingFormats";
		
		when(eventTypeDao.getAll()).thenReturn(inputMockETSet);
		when(gradFormatDao.getAll()).thenReturn(inputMockGFSet);
		Map<String, Set<Object>> output = empServ.getRequestOptions();
		System.out.println(output.values());
		
		assertTrue(output.get(mapETString).isEmpty());
		assertTrue(output.get(mapGFString).isEmpty());
	}
	@Test
	public void getReqIdWhenReimbursementRequestExists() {
		Reimbursement inputRequest = new Reimbursement();
		Status initialStatus = new Status();
		int inputInteger = 10;
		when(statusDao.getById(1)).thenReturn(initialStatus);
		when(reqDao.create(inputRequest)).thenReturn(inputInteger);
		int output = empServ.submitReimbursementRequest(inputRequest);
		
		assertEquals(output, inputInteger);
		assertEquals(inputRequest.getStatus(), initialStatus);	//Making sure Reimbursement setStatus
	}
	@Test
	public void getSetReimbursementWhenEmployeeExists() {
		Employee inputRequestor = new Employee();
		Set<Reimbursement> inputMockSet = new HashSet<Reimbursement>();
		when(reqDao.getByRequestor(inputRequestor)).thenReturn(inputMockSet);
		Set<Reimbursement> output = empServ.getReimbursementRequests(inputRequestor);
		
		assertEquals(inputMockSet, output);
	}
	@Test
	public void getSetCommentWithReimbursementRequest() {
		Reimbursement inputRequest = new Reimbursement();
		Set<Comment> inputSetComment = new HashSet<Comment>();
		Comment inputComment = new Comment();
		Employee inputEmployee = new Employee();
		inputComment.setApprover(inputEmployee);
		inputSetComment.add(inputComment);
		when(commentDao.getByRequestId(inputRequest.getReqId())).thenReturn(inputSetComment);
		when(empDao.getById(inputComment.getApprover().getEmpId())).thenReturn(inputEmployee);
		
		Set<Comment> output = empServ.getComments(inputRequest);
		
		assertEquals(output, inputSetComment);
		assertEquals(inputComment.getRequest(), inputRequest);
	}
	@Test
	public void getIntWhenAddingComment() {
		Comment inputComment = new Comment();
		int inputId = 5;
		when(commentDao.create(inputComment)).thenReturn(inputId);
		int output = empServ.addComment(inputComment);
		
		assertEquals(output, inputId);
	}
	@Test
	public void getEmployeeByEmpId() {
		Employee inputEmployee = new Employee();
		int inputEmpId = 3;
		when(empDao.getById(inputEmpId)).thenReturn(inputEmployee);
		Employee output = empServ.getEmployeeById(inputEmpId);
		
		assertEquals(output, inputEmployee);
	}
	
	
}
