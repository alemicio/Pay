package it.screwdrivers.payroll.controller;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.PaymethodDao;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.payment.Paymethod;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class PaymethodController {
	
	@Inject
	PaymethodDao paymethod_dao;
	EmployeeDao employee_dao;
	
	//method that check if the employee has already set a paymethod type
	public Paymethod checkPaymethodForEmployee(Employee e){
		
		if(e.getPaymethod() == null){
			return null;
		}
		else{
			return e.getPaymethod();
		}
			
		
		
	}
}
