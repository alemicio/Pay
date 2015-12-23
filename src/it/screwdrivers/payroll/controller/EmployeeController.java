package it.screwdrivers.payroll.controller;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.pojo.employee.CommissionedEmployee;
import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.employee.EmployeeManager;
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
				continue;
			}
			else{
				only_salaried.add(s);
			}
		}
		
		return only_salaried;
	}
	
	public List<CommissionedEmployee> getAllCommissioned(){
		
		List<CommissionedEmployee> comm_list = e_dao.findAllCommissioned();
		return comm_list;
	}
	
	public List<ContractorEmployee> getAllContractors(){
		
		List<ContractorEmployee> con_list = e_dao.findAllContractor();
		return con_list;
	}
	
	public List<EmployeeManager> getAllManagers(){
		
		List<EmployeeManager> man_list = e_dao.findAllManager();
		return man_list;
	}
	
	public void updateSalariedEmployeeMonthlySalary(int id_employee,float monthly_salary){
		
		//get the employee row from the db
		List<SalariedEmployee> salaried_list = e_dao.findAllSalaried();
		
		for(SalariedEmployee s: salaried_list){
			if(s.getId() == id_employee){
				
				s.setMonthly_salary(monthly_salary);
				e_dao.update(s);
			}
		}

	}
}
