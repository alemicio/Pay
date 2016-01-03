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

	public void changeDetails(Employee retrieved_employee) {
		e_dao.update(retrieved_employee);
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
				break;
			}
		}
	}

	public void updateCommissionedEmployeeMonthlySalarySaleRate(int id_employee, float monthly_salary, float sale_rate) {
		
		//get the employee row from the db
		List<CommissionedEmployee> commissioned_list = e_dao.findAllCommissioned();
				
		for(CommissionedEmployee c: commissioned_list){
			if(c.getId() == id_employee){
				
				c.setMonthly_salary(monthly_salary);
				c.setSale_rate(sale_rate);
				e_dao.update(c);
				break;
			}
		}
	}

	public void updateContractorEmployeeHourlyRate(int id_employee,float hourly_rate) {
		
		//get the employee row from the db
		List<ContractorEmployee> contractor_list = e_dao.findAllContractor();
						
		for(ContractorEmployee c: contractor_list){
			if(c.getId() == id_employee){
						
				c.setHourly_rate(hourly_rate);
				e_dao.update(c);
				break;
			}
		}
	}
	
	public boolean deleteEmployee(int id_employee) {
		
		List<Employee> employees = e_dao.findAll();
		boolean response = false;
		
		for(Employee e: employees){
			if(e.getId() == id_employee){
				
				response = true;	
				e_dao.remove(e);
				break;
			}
		}
		
		return response;
	}
	
	public boolean addEmployee(Employee employee){
		
		boolean available = true;
		
		List<Employee> employee_list = e_dao.findAll();
		for(Employee e : employee_list){
			
			//check if the employee we should be inserted
			// has a username available or not
			if(employee.getUsername().equals(e.getUsername())){
				available = false;
				break;
			}
				
		}
		
		//if the username is available we add the employee
		if(available == true){
			e_dao.add(employee);
		}
		
		return available;
	}
	
}
