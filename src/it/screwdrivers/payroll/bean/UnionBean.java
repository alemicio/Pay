package it.screwdrivers.payroll.bean;

import it.screwdrivers.payroll.controller.UnionController;


import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.union.Union;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("union")
@SessionScoped
public class UnionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	UnionController u_controller;
	@Inject
	Union unione;
	
	private String union;
	
	private List<String> associated_unions;
	
	
	public String getUnion(Employee e) {
		// // This method returns a paymethod for a given employee.
		String name_union = null;
		boolean is_union_set = u_controller.isUnionSet(e);

		if (is_union_set == true) {
			name_union = u_controller.findUnionName(e.getUnion());
		} 
		else {
			name_union = "Not setted";
		}
		return name_union;
	}
	
	public void setUnion(Employee e,String union_name){
		
		u_controller.setUnion(e,union_name);
		
	}
	
	public void affiliatedUnions(){
		
		associated_unions = u_controller.affiliatedUnions();
		
//		for(String s : associated_unions){
//			System.out.println(s);
//		}
		
	}

	public String getUnion() {
		return union;
	}

	public void setUnion(String union) {
		this.union = union;
	}

	public List<String> getAssociated_unions() {
		return associated_unions;
	}

	public void setAssociated_unions(List<String> associated_unions) {
		this.associated_unions = associated_unions;
	}
	
	
	
	

	
	

}
