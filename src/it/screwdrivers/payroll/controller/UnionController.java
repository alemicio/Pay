package it.screwdrivers.payroll.controller;

import java.util.List;

import it.screwdrivers.payroll.dao.UnionDao;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.payment.BankPaymethod;
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
	
	public void setUnion(Employee employee,String union_name) {

	}

	@SuppressWarnings("null")
	public List<String> affiliatedUnions() {
		
		List<String> a_unions = null;
		
		//this method return all names of associated unions in the db
		List<Union> unions = u_dao.findAll();
		
		//this cycle popolate the list with names of all unions
		for(Union u : unions){
			a_unions.add(u.getName().toString());
			
		}
		
//		for(int i=0; i < unions.size(); i++){
//			System.out.println(unions.get(i).getName().toString());
//		}
		
		return a_unions;
	}

}
