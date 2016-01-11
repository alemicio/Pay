package it.screwdrivers.payroll.logic;

import java.util.ArrayList;
import java.util.List;

import it.screwdrivers.payroll.dao.UnionServiceAssociationDao;
import it.screwdrivers.payroll.model.employee.Employee;
import it.screwdrivers.payroll.model.union.UnionServiceAssociation;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UnionServiceAssociationService {

	@Inject
	UnionServiceAssociationDao usa_dao;

	@Inject
	CalendarService calendar_service;

	public List<String> getUnionServiceNames(Employee employee) {

		List<String> service_names = new ArrayList<String>();

		List<UnionServiceAssociation> union_service_associations = getUnionServiceAssociations(employee);
		for (UnionServiceAssociation usa : union_service_associations) {
			service_names.add(usa.getUnion_service().getName());
		}

		return service_names;
	}

	private List<UnionServiceAssociation> getUnionServiceAssociations(
			Employee employee) {

		List<UnionServiceAssociation> filtered_list = new ArrayList<UnionServiceAssociation>();

		// retrive all unionservice associated with the union of the employee
		List<UnionServiceAssociation> usa_list = usa_dao.findAll();
		for (UnionServiceAssociation usa : usa_list) {

			// fetch out the union associated with the employee
			// check if the union id of the employee matches with the union id
			// in the db
			if (employee.getUnion().getId() == usa.getUnion().getId()) {

				// if is not associated to the correct union
				// it is removed from the list
				// NB: only from the list and not from the db
				// usa_list.remove(u);
				filtered_list.add(usa);
			}
		}

		return filtered_list;
	}

	public UnionServiceAssociation getUnionServiceAssociationByUnionNameAndServiceName(
			String union_name, String service_name) {

		List<UnionServiceAssociation> union_service_associations = usa_dao
				.findAll();

		for (UnionServiceAssociation usa : union_service_associations) {
			if (usa.getUnion().getName().equals(union_name)
					&& usa.getUnion_service().getName().equals(service_name)) {

				return usa;
			}
		}

		return null;
	}
}
