package it.screwdrivers.payroll.dao;

import it.screwdrivers.payroll.model.employee.CommissionedEmployee;
import it.screwdrivers.payroll.model.employee.ContractorEmployee;
import it.screwdrivers.payroll.model.employee.Employee;
import it.screwdrivers.payroll.model.employee.EmployeeManager;
import it.screwdrivers.payroll.model.employee.SalariedEmployee;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class EmployeeDao {
	
	@PersistenceContext
	EntityManager em;

	// This method allows to add a new employee object,though the ORM, into the
	// employee table
	public void add(Employee employee) {
		em.persist(employee);
	}

	// This method returns all employee records from employee table
	// The query is made using java persistence query language 
	// (see documentation)
	public List<Employee> findAll() {
		List<Employee> employees = em.createQuery("select p from Employee p", Employee.class).getResultList();
		return employees;
	}

	// This method allows to override an existing employee record
	public Employee update(Employee employee) {
		return em.merge(employee);
	}

	// This method allows to delete an existing employee record
	public void remove(Employee employee) {
		em.remove(em.merge(employee));
	}
	
	public List<ContractorEmployee> findAllContractor() {
		List<ContractorEmployee> employees = em.createQuery("select p from ContractorEmployee p", ContractorEmployee.class).getResultList();
		return employees;
	}
	
	public List<CommissionedEmployee> findAllCommissioned() {
		List<CommissionedEmployee> employees = em.createQuery("select p from CommissionedEmployee p", CommissionedEmployee.class).getResultList();
		return employees;
	}
	
	public List<EmployeeManager> findAllManager() {
		List<EmployeeManager> employees = em.createQuery("select p from EmployeeManager p", EmployeeManager.class).getResultList();
		return employees;
	}
	
	public List<SalariedEmployee> findAllSalaried() {
		List<SalariedEmployee> employees = em.createQuery("select p from SalariedEmployee p", SalariedEmployee.class).getResultList();
		return employees;
	}

}
