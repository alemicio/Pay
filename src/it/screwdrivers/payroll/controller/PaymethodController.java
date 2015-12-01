package it.screwdrivers.payroll.controller;

import java.util.List;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.PaymethodDao;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.payment.BankPaymethod;
import it.screwdrivers.payroll.pojo.payment.Paymethod;
import it.screwdrivers.payroll.pojo.payment.PostalPaymethod;
import it.screwdrivers.payroll.pojo.payment.WithDrawPaymethod;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class PaymethodController {
	
	@Inject
	PaymethodDao paymethod_dao;
	EmployeeDao employee_dao;
	
	//method that check if the employee has already set a paymethod type
	public Paymethod checkPaymethodForEmployee(Employee employee){
		
		List<Employee> employees = employee_dao.findAll();
		
		//this method return the paymethod for a given employee.
		for (Employee e : employees) {
			if(e.getUsername().equals(employee.getUsername())){
				return e.getPaymethod();
			}
		}
		return null;

	}
	public String findType(Paymethod p){
		String type = null;
		
		if(p instanceof BankPaymethod)
			type = "Bank account";
		if(p instanceof PostalPaymethod)
			type = "Postal account";
		if(p instanceof WithDrawPaymethod)
			type = "WithDraw account";
		
		return type;
	}
}
