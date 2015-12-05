package it.screwdrivers.payroll.dao;

import it.screwdrivers.payroll.pojo.historical.HistoricalUnionCharge;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class HistoricalUnionChargeDao {

	@PersistenceContext
	EntityManager em;

	public void add(HistoricalUnionCharge historical_union_charge) {
		em.persist(historical_union_charge);
	}

	public List<HistoricalUnionCharge> findAll() {
		List<HistoricalUnionCharge> historical_union_charges = em.createQuery("select p from HistoricalUnionCharge p", HistoricalUnionCharge.class).getResultList();
		return historical_union_charges;
	}
	
	public HistoricalUnionCharge update(HistoricalUnionCharge historical_union_charge) {
		return em.merge(historical_union_charge);
	}

	public void remove(HistoricalUnionCharge historical_union_charge) {
		em.remove(em.merge(historical_union_charge));
	}
}
