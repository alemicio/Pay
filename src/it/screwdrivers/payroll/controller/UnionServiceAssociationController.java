package it.screwdrivers.payroll.controller;

import java.util.ArrayList;
import java.util.List;

import it.screwdrivers.payroll.dao.UnionServiceAssociationDao;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.union.UnionServiceAssociation;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UnionServiceAssociationController {
	
	@Inject
	UnionServiceAssociationDao usa_dao;
	
	public List<UnionServiceAssociation> retriveUnionService(Employee employee){

		//retrive all unionservice associated with the union of the employee
		List<UnionServiceAssociation> usa_list = usa_dao.findAll();
		
		List<UnionServiceAssociation> filtered_list = new ArrayList<UnionServiceAssociation>();
		
		for( UnionServiceAssociation u : usa_list){
			
			//fetch out the union associated with the employee
			// check if the union id of the employee matches with the union id in the db
			if(employee.getUnion().getId() == u.getUnion_id()){
				
				//if is not associated to the correct union 
				//it is removed from the list
				//NB: only from the list and not from the db 
				//usa_list.remove(u);
				filtered_list.add(u);
			}
		}
		return filtered_list;	
	}
}
