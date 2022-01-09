package com.revature.app;

import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.Set;

import com.revature.beans.Employee;
import com.revature.beans.Reimbursement;
import com.revature.controllers.RequestsController;
import com.revature.data.ReimbursementDAO;
import com.revature.services.RequestReviewService;
import com.revature.services.RequestReviewServiceImpl;
import com.revature.utils.DAOFactory;

public class TRMSApp {

	public static void main(String[] args) {
		Javalin app = Javalin.create(config -> {
			
			config.enableCorsForAllOrigins();
		}).start();

		
		app.routes(() -> {
			path("trmsback/requests", () -> {
				post(RequestsController::submitReimbursementRequest);
				path("/requestor/{id}", () -> {
					get(RequestsController::getRequestsByRequestor);
				});
			});
		});
	
	
//	Employee employee = new Employee();
//	Employee supervisor = new Employee();
//	supervisor.setEmpId(2);
//	employee.setSupervisor(supervisor);
//	ReimbursementDAO reqDao = DAOFactory.getReimbursementDAO();
//	Set<Reimbursement> setReimburseByPendingStatus = reqDao.getAll();
//	System.out.println(setReimburseByPendingStatus);
//	
//	
//	
//	
//	RequestReviewService rrServ = new RequestReviewServiceImpl();
//	
//	Set<Reimbursement> output = rrServ.getPendingReimbursements(supervisor);
//	//System.out.println(output);
		
	}

	
}
