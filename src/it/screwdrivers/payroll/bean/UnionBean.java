package it.screwdrivers.payroll.bean;

import it.screwdrivers.payroll.controller.UnionController;


import it.screwdrivers.payroll.controller.UnionServiceAssociationController;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.union.Union;
import it.screwdrivers.payroll.pojo.union.UnionServiceAssociation;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

@Named("union")
@SessionScoped
public class UnionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	UnionController u_controller;
	
	@Inject
	UnionServiceAssociationController usa_controller;
	
	@Inject
	Union unione;
	
	private String union_name;
	private List<String> associated_unions;
	private List<UnionServiceAssociation> associated_service;
	private List<String> service_names;
	
	@NotNull
	private String services_selected;
	
	
	public String generateUnionInfo(Employee e) {
		
		//firstly we populate the list of all unions available on db
		//SGAMO MICIO --> maybe replaced by @postcustruct
		affiliatedUnions();
		getUnionServiceAvailable(e);
		
		//here we have populated the list of service name to show in the face
		service_names = usa_controller.getUnionServiceNames(associated_service);
		
		//This method returns a union for a given employee.
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
	
	public void setUnion(Employee e){
		
		System.out.println("unione scelta dalla tendina"+union_name);
		u_controller.setUnion(e,union_name);
		
		
	}
	
	private void affiliatedUnions(){
		associated_unions = u_controller.affiliatedUnions();
	}
	
	private void getUnionServiceAvailable(Employee e){
		associated_service = usa_controller.retrieveUnionServiceAssociations(e);
	}
	

	

	public String getUnion_name() {
		return union_name;
	}

	public void setUnion_name(String union_name) {
		this.union_name = union_name;
	}

	public List<String> getAssociated_unions() {
		return associated_unions;
	}

	public void setAssociated_unions(List<String> associated_unions) {
		this.associated_unions = associated_unions;
	}

	

	public List<UnionServiceAssociation> getAssociated_service() {
		return associated_service;
	}

	public void setAssociated_service(
			List<UnionServiceAssociation> associated_service) {
		this.associated_service = associated_service;
	}

	public String getServices_selected() {
		return services_selected;
	}

	public void setServices_selected(String services_selected) {
		this.services_selected = services_selected;
	}

	public List<String> getService_names() {
		return service_names;
	}

	public void setService_names(List<String> service_names) {
		this.service_names = service_names;
	}
	
	
	
	
	
	
	
	

	
	

}
