package it.screwdrivers.payroll.engine;

import java.util.List;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;


import it.screwdrivers.payroll.pojo.employee.Employee;

import javax.inject.Inject;

public class ContractorPayEngine implements IPayEngine {

	@Inject
	EmployeeDao e_dao;

	@Override
	public void pay() {
		
		List<Employee> employees = e_dao.findAll();
		
		for(Employee c : employees){
			if(c instanceof ContractorEmployee){
				
			}
		}
		
	}
}
