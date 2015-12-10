package it.screwdrivers.payroll.controller;

import java.util.List;

import it.screwdrivers.payroll.dao.HistoricalUnionChargeDao;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.historical.HistoricalUnionCharge;
import it.screwdrivers.payroll.pojo.union.UnionServiceAssociation;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class HistoricalUnionChargeController {

	@Inject
	HistoricalUnionChargeDao huc_dao;

	public String confirmOrder(Employee e, List<UnionServiceAssociation> selected_union_service_associations) {
		
		for(UnionServiceAssociation susa : selected_union_service_associations){
			
			HistoricalUnionCharge huc = new HistoricalUnionCharge();
			huc.setUnion_service_association(susa);
			huc.setEmployee(e);
			
			huc_dao.add(huc);
		}
		
		return "success";
	}	
}
