package com.revature.services;

import java.util.HashSet;
import java.util.Set;

import com.revature.beans.Comment;
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

public class RequestReviewServiceImpl implements RequestReviewService{
	private EventTypeDAO eventTypeDao = DAOFactory.getEventTypeDAO();
	private GradingFormatDAO gradFormatDao = DAOFactory.getGradingFormatDAO();
	private StatusDAO statusDao = DAOFactory.getStatusDAO();
	private ReimbursementDAO reqDao = DAOFactory.getReimbursementDAO();
	private CommentDAO commentDao = DAOFactory.getCommentDAO();
	private EmployeeDAO empDao = DAOFactory.getEmployeeDAO();

	@Override
	public Set<Reimbursement> getPendingReimbursements(Employee approver) {
		Set<Reimbursement> approverPendingReimbursements = new HashSet<Reimbursement>();
		
		String approver1 = "direct supervisor";
		String approver2 = "department head";
		String approver3 = "benefits coordinator";
		Set<Reimbursement> setReimburseByPendingStatus = reqDao.getAll();
			for(Reimbursement reim : setReimburseByPendingStatus) {
				if(reim.getStatus().getApprover().equals(approver1)  && reim.getRequestor().getSupervisor().getEmpId() == approver.getEmpId()) {
					approverPendingReimbursements.add(reim);
					System.out.println(approverPendingReimbursements);
				}
				if(reim.getStatus().getApprover().equals(approver2) && reim.getRequestor().getSupervisor().getDepartment().getDeptHeadId() == approver.getEmpId()) {
					approverPendingReimbursements.add(reim);
					System.out.println(approverPendingReimbursements);
				}
				if(reim.getStatus().getApprover().equals(approver3) && reim.getRequestor().getSupervisor().getDepartment().getDeptId() == 9) { //DeptId 9 is the HumanResources Dept in Database
					approverPendingReimbursements.add(reim);
					System.out.println(approverPendingReimbursements);
				}
			}
		
		return approverPendingReimbursements;
	}

	@Override
	public void approveRequest(Reimbursement request) {
		
		if(request.getStatus().getStatusId() == 1 || request.getStatus().getStatusId() == 5) {	//StatusId 1 - StatusName: Pending Approval, Approver: direct supervisor 
			request.getStatus().setStatusId(2);	//changes StatusId to 2 - StatusName: Pending Approval, Approver: department head
			reqDao.update(request);
		}
		if(request.getStatus().getStatusId() == 2 || request.getStatus().getStatusId() == 6) {	//StatusId 1 - StatusName: Pending Approval, Approver: department head
			request.getStatus().setStatusId(3);			//changes StatusId to 2 - StatusName: Pending Approval, Approver: benefits coordinator
			reqDao.update(request);
		}
		if(request.getStatus().getStatusId() == 3 || request.getStatus().getStatusId() == 7) {	//If StatusId is at 3, then the benefits coordinator will conclude the approval request
			request.getStatus().setStatusId(4);			//changes StatusId to 4 - StatusName: Approved, Approver: benefits coordinator
			reqDao.update(request);
		}
		
	}

	@Override
	public void rejectRequest(Reimbursement request) {
		if(request.getStatus().getStatusId() == 1) {	//StatusId 1 - StatusName: Pending Approval, Approver: direct supervisor 
			request.getStatus().setStatusId(5);	//changes StatusId to 5 - StatusName: Rejected, Approver: department head
			reqDao.update(request);
		}
		if(request.getStatus().getStatusId() == 2) {	//StatusId 1 - StatusName: Pending Approval, Approver: department head
			request.getStatus().setStatusId(6);			//changes StatusId to 6 - StatusName: Rejected, Approver: benefits coordinator
			reqDao.update(request);
		}
		if(request.getStatus().getStatusId() == 3) {	//If StatusId is at 3, then the benefits coordinator will conclude the approval request
			request.getStatus().setStatusId(7);			//changes StatusId to 7 - StatusName: Rejected, Approver: benefits coordinator
			reqDao.update(request);
		}
		
	}

	@Override
	public void rejectRequest(Reimbursement request, Comment comment) {
		if(request.getStatus().getStatusId() == 1) {	//StatusId 1 - StatusName: Pending Approval, Approver: direct supervisor 
			request.getStatus().setStatusId(5);	//changes StatusId to 5 - StatusName: Rejected, Approver: department head
			if(comment.getRequest().getReqId() == request.getReqId() && request.getRequestor().getSupervisor().getEmpId() == comment.getApprover().getEmpId()) {
				commentDao.create(comment);
			}
			reqDao.update(request);
		}
		if(request.getStatus().getStatusId() == 2) {	//StatusId 1 - StatusName: Pending Approval, Approver: department head
			request.getStatus().setStatusId(6);			//changes StatusId to 6 - StatusName: Rejected, Approver: benefits coordinator
			if(comment.getRequest().getReqId() == request.getReqId() && request.getRequestor().getDepartment().getDeptHeadId() == comment.getApprover().getEmpId()) {
				commentDao.create(comment);
			}
			reqDao.update(request);
		}
		if(request.getStatus().getStatusId() == 3) {	//If StatusId is at 3, then the benefits coordinator will conclude the approval request
			request.getStatus().setStatusId(7);			//changes StatusId to 7 - StatusName: Rejected, Approver: benefits coordinator
			if(comment.getRequest().getReqId() == request.getReqId() && request.getRequestor().getDepartment().getDeptId() == 9) {
				commentDao.create(comment);
			}
			reqDao.update(request);
		}
		
	}

}
