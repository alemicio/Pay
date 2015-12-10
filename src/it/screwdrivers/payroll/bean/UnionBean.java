package it.screwdrivers.payroll.bean;

import it.screwdrivers.payroll.controller.HistoricalUnionChargeController;
import it.screwdrivers.payroll.controller.UnionController;
import it.screwdrivers.payroll.controller.UnionServiceAssociationController;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.union.Union;
import it.screwdrivers.payroll.pojo.union.UnionServiceAssociation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named("union")
@SessionScoped
public class UnionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject 
	Union union;
	
	
	
	@Inject
	UnionController u_controller;
	
	@Inject
	UnionServiceAssociationController usa_controller;
	
	@Inject 
	HistoricalUnionChargeController huc_controller;
	
	private String union_name;
	private List<String> associated_unions;
	private List<UnionServiceAssociation> union_service_associations;
	private List<String> service_names;
	private String services_selected;
	private List<UnionServiceAssociation> selected_union_service_associations = new ArrayList<UnionServiceAssociation>();; 
	
	public String getUnion(Employee e) {
		
		
		//SGAMO MICIO --> maybe replaced by @postcustruct
		populateUnionsNames();
		populateUnionServiceAssociations(e);
		
		//here we have populated the list of service name to show in the face
		service_names = usa_controller.getUnionServiceNames(union_service_associations);
		
		//This method returns a union for a given employee.
		String name_union = null;
		boolean is_union_set = u_controller.isUnionSet(e);

		if (is_union_set == true) {
			name_union = u_controller.findUnionName(e.getUnion());
			this.union = e.getUnion();
		} 
		else {
			name_union = "Not setted";
		}
		
		return name_union;
	}
	
	public void setUnion(Employee e){
		
		System.out.println("Union scelta dalla tendina: " + union_name);
		u_controller.setUnion(e, union_name);
	}
	
	private void populateUnionsNames(){
		associated_unions = u_controller.getUnionsNames();
	}
	
	private void populateUnionServiceAssociations(Employee e){
		union_service_associations = usa_controller.retrieveUnionServiceAssociations(e);
	}
	
	public String[] getSelectedUnionServicesNames(){
		
		String[] names;
		
		if(services_selected.contains(",")){
			names = services_selected.split(",");
		} else {
			names = new String[1];
			names[0] = services_selected;
		}
		
		return names;
	}
	
	// We used ActionEvent object to make this method became a listener for
	// ajax requests.
	public void updateSelectedUnionServiceAssociations(ActionEvent actionEvent){
		
		String[] selected_services_names = getSelectedUnionServicesNames();
		
		selected_union_service_associations.clear();
		for(String service_name : selected_services_names){
			selected_union_service_associations.add(usa_controller.getUnionServiceAssociationByUnionAndServiceName(this.union, service_name));
		}
	}
	
	public void confirmOrder(Employee e){
		
		String response = null;
		response = huc_controller.confirmOrder(e, selected_union_service_associations);
		
		System.out.println(response);
		
		
		if (response == "success") {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Congratulations",
					"your order is correctly submitted"));

		}
		else{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error:",
					"impossible to confirm your order for today, another order was submitted for this week"));
		}
			
	}
	
	// ===========================
	// === Getters and Setters ===
	// ===========================
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
		return union_service_associations;
	}

	public void setAssociated_service(
			List<UnionServiceAssociation> associated_service) {
		this.union_service_associations = associated_service;
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

	public List<UnionServiceAssociation> getSelected_union_service_associations() {
		return selected_union_service_associations;
	}
}
