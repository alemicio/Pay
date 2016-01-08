package it.screwdrivers.payroll.controller;

import it.screwdrivers.payroll.logic.HistoricalUnionChargeService;
import it.screwdrivers.payroll.logic.UnionService;
import it.screwdrivers.payroll.logic.UnionServiceAssociationService;
import it.screwdrivers.payroll.model.employee.Employee;
import it.screwdrivers.payroll.model.union.Union;
import it.screwdrivers.payroll.model.union.UnionServiceAssociation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named("union")
@SessionScoped
public class UnionController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	UnionService union_service;

	@Inject
	UnionServiceAssociationService usa_service;

	@Inject
	HistoricalUnionChargeService huc_service;
	
	@Inject
	Union union;

	private String union_name;
	private List<String> union_names;
	private List<String> service_names;
	private List<String> services_selected;
	private List<UnionServiceAssociation> union_service_associations;
	private List<UnionServiceAssociation> selected_union_service_associations = new ArrayList<UnionServiceAssociation>();;

	public String getUnion(Employee e) {
		
		union_names = union_service.getUnionNames();
		
		// This method returns a union for a given employee.
		String name_union = null;
		boolean is_union_set = union_service.isUnionSet(e);

		if (is_union_set) {
			union_service_associations = usa_service
					.getUnionServiceAssociations(e);
			
			// here we have populated the list of service name to show in the
			// face
			service_names = usa_service
					.getUnionServiceNames(union_service_associations);

			name_union = union_service.getUnionName(e.getUnion());
			this.union = e.getUnion();
		} else {
			name_union = "Not setted";
		}

		return name_union;
	}

	public void setUnion(Employee e) {
		union_service.setUnion(e, union_name);
	}

	public void updateSelectedUnionServiceAssociations() {
		for (String selected_service_name : services_selected) {
			selected_union_service_associations.add(usa_service
					.getUnionServiceAssociationByUnionAndServiceName(
							this.union, selected_service_name));
		}
	}

	public void confirmOrder(Employee e) {
		
		updateSelectedUnionServiceAssociations();

		String response = null;
		response = huc_service.confirmOrder(e,
				selected_union_service_associations);

		if (response == "success") {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Congratulations",
					"your order is correctly submitted"));
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Error:",
							"impossible to confirm your order for today, another order was submitted for this week"));
		}
	}

	public String getUnion_name() {
		return union_name;
	}

	public void setUnion_name(String union_name) {
		this.union_name = union_name;
	}

	public List<String> getAssociated_unions() {
		return union_names;
	}

	public void setAssociated_unions(List<String> associated_unions) {
		this.union_names = associated_unions;
	}

	public List<UnionServiceAssociation> getAssociated_service() {
		return union_service_associations;
	}

	public void setAssociated_service(
			List<UnionServiceAssociation> associated_service) {
		this.union_service_associations = associated_service;
	}

	public List<String> getServices_selected() {
		return services_selected;
	}

	public void setServices_selected(List<String> services_selected) {
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
