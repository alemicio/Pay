package it.screwdrivers.payroll.controller;

import java.util.List;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.pojo.employee.CommissionedEmployee;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.employee.EmployeeManager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.sun.tools.classfile.Annotation.element_value;

@Stateless
public class EmployeeController {
	@Inject
	EmployeeDao e_dao;

	
	
	

	public Employee checkLogin(String username, String password) {

		Employee emp = null;
		
		// check if the employee witch tries to enter in the web app has an
		// registered account
		List<Employee> employees = e_dao.findAll();

		// itaerator over the list
		for (Employee e : employees) {

			if (e.getUsername().equals(username) && e.getPassword().equals(password)) {
				
				emp = e;
				
				break;

			}
		}
	
		return emp;
	}
	
	
	
	
	
	
	private String findType(Employee e){
		String ritorno;
		ritorno = e.getClass().getSimpleName();
		return ritorno;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
