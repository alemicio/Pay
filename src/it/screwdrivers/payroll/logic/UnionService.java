package it.screwdrivers.payroll.logic;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.UnionDao;
import it.screwdrivers.payroll.model.employee.Employee;
import it.screwdrivers.payroll.model.union.Union;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UnionService {

	@Inject
	UnionDao u_dao;

	@Inject
	EmployeeDao employee_dao;

	public boolean isUnionSet(Employee employee) {
		if (employee.getUnion() == null) {
			return false;
		} else {
			return true;
		}
	}

	public String getUnionName(Union union) {
		return union.getName();
	}

	public void setUnion(Employee employee, String union_name) {
		List<Union> unions = u_dao.findAll();

		for (Union u : unions) {
			if (u.getName().equals(union_name)) {
				updateUnion(employee, u);
			}
		}
	}

	public List<String> getUnionNames() {
		// this method return all names of associated unions in the db
		List<Union> unions = u_dao.findAll();

		List<String> union_names = new ArrayList<String>();

		for (Union u : unions) {
			union_names.add(u.getName());
		}

		return union_names;
	}

	private void updateUnion(Employee employee, Union u) {
		employee.setUnion(u);
		employee_dao.update(employee);
	}
}
