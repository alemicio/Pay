package it.screwdrivers.payroll.controller;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.pojo.employee.CommissionedEmployee;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class EmployeeController {
	
	@Inject
	EmployeeDao e_dao;
	
	public Employee retrieveEmployee(String username, String password) {

		Employee emp = null;
		
		// check if the employee witch tries to enter in the web app has an
		// registered account
		List<Employee> employees = e_dao.findAll();

		// iterator over the list
		for (Employee e : employees) {
			if (e.getUsername().equals(username) && e.getPassword().equals(password)) {
				emp = e;
				break;
			}
		}
		
		return emp;
	}
	
	public String findType(Employee e) {
		String type;
		type = e.getClass().getSimpleName();
		return type;
	}
	
	public List<SalariedEmployee> getAllSalaried(){
		
		List<SalariedEmployee> sal_list = e_dao.findAllSalaried();
		List<SalariedEmployee> only_salaried = new ArrayList<SalariedEmployee>();
		
		
		for(SalariedEmployee s: sal_list){
			
			//get only the salaried memebers that are not commissioned
			if(s instanceof CommissionedEmployee ){
				
				System.out.println("Sono un commissioned --> non devi aggiungermi al db");
				System.out.println(s.getUsername());
				continue;
			}
			else{
				only_salaried.add(s);
			}
		}
		
		return only_salaried;
	}
}
