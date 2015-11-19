package it.screwdrivers.payroll.dao;

import java.util.List;

import it.screwdrivers.payroll.pojo.Employee;

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
	// The query is made using java persistance query language(see
	// documentation)
	public List<Employee> findAll() {
		List<Employee> employees = em.createQuery("select p from Employee p",
				Employee.class).getResultList();
		return employees;
	}

	
	// This method allows to override an existing employee record
	public Employee update(Employee employee) {
		return em.merge(employee);
	}

	// This method allows to delete an existing employee record
	public void remove(Employee employee) {
		em.remove(employee);
	}
}
