package it.screwdrivers.payroll.dao;

import it.screwdrivers.payroll.model.historical.HistoricalSalary;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class HistoricalSalaryDao {

	@PersistenceContext
	EntityManager em;

	public void add(HistoricalSalary historical_salary) {
		em.persist(historical_salary);
	}

	public List<HistoricalSalary> findAll() {
		List<HistoricalSalary> historical_salaries = em.createQuery("select p from HistoricalSalary p", HistoricalSalary.class).getResultList();
		return historical_salaries;
	}
	
	public HistoricalSalary update(HistoricalSalary historical_salary) {
		return em.merge(historical_salary);
	}

	public void remove(HistoricalSalary historical_salary) {
		em.remove(em.merge(historical_salary));
	}
	
}
