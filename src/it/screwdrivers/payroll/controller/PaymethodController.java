package it.screwdrivers.payroll.controller;

import java.util.List;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.PaymethodDao;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.payment.BankPaymethod;
import it.screwdrivers.payroll.pojo.payment.Paymethod;
import it.screwdrivers.payroll.pojo.payment.PostalPaymethod;
import it.screwdrivers.payroll.pojo.payment.WithDrawPaymethod;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class PaymethodController {

	@Inject
	PaymethodDao paymethod_dao;
	EmployeeDao employee_dao;

	public void setBankPaymethod(Employee employee, BankPaymethod bank_paymethod) {

		clearPaymethod(employee.getId());

		// Add the new paymethod in the db
		paymethod_dao.add(bank_paymethod);

		updatePaymethod(employee, bank_paymethod);

	}

	// MICIO
	// This method checks if the employee has already set a paymethod type
	public boolean isPaymethodSet(Employee employee) {
		if (employee.getPaymethod() == null) {
			return false;
		} else {
			return true;
		}
	}

	// MICIO
	// Return the type of the paymetod
	public String findType(Paymethod p) {
		String type = null;
		if (p instanceof BankPaymethod)
			type = "Bank account";
		if (p instanceof PostalPaymethod)
			type = "Postal account";
		if (p instanceof WithDrawPaymethod)
			type = "WithDraw account";
		return type;
	}

	private void clearPaymethod(int id) {
		// delete all references of paymethod for the given employee
		List<Employee> employees = employee_dao.findAll();
		for (Employee e : employees) {
			if (id == e.getId()) {
				paymethod_dao.remove(e.getPaymethod());
			}
		}
	}

	private void updatePaymethod(Employee employee, Paymethod p) {
		employee.setPaymethod(p);
		employee_dao.update(employee);
	}
}
