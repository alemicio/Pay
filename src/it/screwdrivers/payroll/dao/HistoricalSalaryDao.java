package it.screwdrivers.payroll.dao;

import it.screwdrivers.payroll.pojo.employee.Employee;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class HistoricalSalaryDao {

	@PersistenceContext
	EntityManager em;

	public void add(Employee employee) {
		em.persist(employee);
	}

	public List<Employee> findAll() {
		List<Employee> employees = em.createQuery("select p from Employee p", Employee.class).getResultList();
		return employees;
	}
	
	public Employee update(Employee employee) {
		return em.merge(employee);
	}

	public void remove(Employee employee) {
		em.remove(em.merge(employee));
	}
}
