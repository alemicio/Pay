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

import javax.annotation.PostConstruct;
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
	private List<String> services_names;
	private List<String> services_selected;
	private List<UnionServiceAssociation> union_service_associations;
	private List<UnionServiceAssociation> selected_union_service_associations = new ArrayList<UnionServiceAssociation>();

	@PostConstruct
	public void init() {
		union_names = union_service.getUnionNames();
	}

	public String getUnion(Employee e) {

		String union_name = null;
		boolean is_union_set = union_service.isUnionSet(e);

		if (is_union_set) {
			union_service_associations = usa_service
					.getUnionServiceAssociations(e);

			services_names = usa_service
					.getUnionServiceNames(union_service_associations);

			union_name = union_service.getUnionName(e.getUnion());
		} else {
			union_name = "Not setted";
		}

		return union_name;
	}

	public void setUnion(Employee e) {
		union_service.setUnion(e, union_name);
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
					"All the services orders were correctly submitted"));

		} else if (response == "no-working-day") {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
							"The order cannot be registered since this is not a working day"));

		} else if (response != null) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error",
					"It is impossible to submit the order for the following services: "
							+ response));
		}
	}

	public void updateSelectedUnionServiceAssociations() {
		for (String selected_service_name : services_selected) {
			selected_union_service_associations.add(usa_service
					.getUnionServiceAssociationByUnionAndServiceName(
							this.union, selected_service_name));
		}
	}

	public String getUnion_name() {
		return union_name;
	}

	public void setUnion_name(String union_name) {
		this.union_name = union_name;
	}

	public List<String> getUnion_names() {
		return union_names;
	}

	public void setUnion_names(List<String> union_names) {
		this.union_names = union_names;
	}

	public List<UnionServiceAssociation> getUnion_service_associations() {
		return union_service_associations;
	}

	public void setUnion_service_associations(
			List<UnionServiceAssociation> associated_service) {
		this.union_service_associations = associated_service;
	}

	public List<String> getServices_selected() {
		return services_selected;
	}

	public void setServices_selected(List<String> services_selected) {
		this.services_selected = services_selected;
	}

	public List<String> getServices_names() {
		return services_names;
	}

	public void setServices_names(List<String> services_names) {
		this.services_names = services_names;
	}

	public List<UnionServiceAssociation> getSelected_union_service_associations() {
		return selected_union_service_associations;
	}
}
