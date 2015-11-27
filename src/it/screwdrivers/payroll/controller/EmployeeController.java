package it.screwdrivers.payroll.controller;

import java.util.List;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.pojo.employee.Employee;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class EmployeeController {
	@Inject
	EmployeeDao e_dao;

	
	public Boolean checkLogin(String username, String password) {
		
		boolean find = false;
		//check if the employee witch tries to enter in the web app has an registered account
		List<Employee> employees = e_dao.findAll();
		
		//itaerator over the list
		for(Employee e : employees ){
			
			if(e.getUsername().equals(username) && e.getPassword().equals(password)){
				find = true;
			}
		}
		
		return find;
	}
	
	
}
