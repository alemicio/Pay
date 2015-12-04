package it.screwdrivers.payroll.controller;

import it.screwdrivers.payroll.dao.UnionDao;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.union.Union;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UnionController {
	
	@Inject
	UnionDao u_dao;

	public boolean isUnionSet(Employee employee) {
		if (employee.getUnion() == null) {
			return false;
		} else {
			return true;
		}
	}

	public String findUnionName(Union union) {
		
		return union.getName();
	}

}
