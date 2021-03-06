package it.screwdrivers.payroll.dao;

import java.util.List;

import it.screwdrivers.payroll.model.card.SalesCard;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SalesCardDao {

	@PersistenceContext
	EntityManager em;

	public void add(SalesCard sale_card) {
		em.persist(sale_card);
	}

	public List<SalesCard> findAll() {
		List<SalesCard> sales_card = em.createQuery("select s from SalesCard s",SalesCard.class).getResultList();
		return sales_card;
	}

	public SalesCard update(SalesCard sale_card) {
		return em.merge(sale_card);
	}

	public void remove(SalesCard sale_card) {
		em.remove(em.merge(sale_card));
	}
	
}
