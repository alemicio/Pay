package it.screwdrivers.payroll.bean;

import it.screwdrivers.payroll.controller.UnionController;


import it.screwdrivers.payroll.pojo.employee.Employee;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("union")
@SessionScoped
public class UnionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	UnionController u_controller;
	
	private String union;
	
	
	public String getUnion(Employee e) {
		// // This method returns a paymethod for a given employee.
		String name_union = null;
		boolean is_union_set = u_controller.isUnionSet(e);
		
		System.out.println(is_union_set);

		if (is_union_set == true) {
			name_union = u_controller.findUnionName(e.getUnion());
			
			System.out.println(name_union);
			
			return name_union;
		} else {
			name_union = "Not setted";
		}
		
		return name_union;
	}

	public String getUnion() {
		return union;
	}

	public void setUnion(String union) {
		this.union = union;
	}
	
	

	
	

}
