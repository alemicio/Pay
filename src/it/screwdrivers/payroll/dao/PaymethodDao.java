package it.screwdrivers.payroll.dao;

import it.screwdrivers.payroll.pojo.payment.Paymethod;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PaymethodDao {

	@PersistenceContext
	EntityManager em;

	public void add(Paymethod paymethod) {
		em.persist(paymethod);
	}

	public List<Paymethod> findAll() {
		List<Paymethod> paymethods = em.createQuery("select p from Paymethod s", Paymethod.class).getResultList();
		return paymethods;
	}

	public Paymethod update(Paymethod paymethod) {
		return em.merge(paymethod);
	}

	public void remove(Paymethod paymethod) {
		em.remove(em.merge(paymethod));
	}
}
