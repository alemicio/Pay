package it.screwdrivers.payroll.dao;

import it.screwdrivers.payroll.model.card.TimeCard;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TimeCardDao {

	@PersistenceContext
	EntityManager em;

	public void add(TimeCard time_card) {
		em.persist(time_card);
	}

	public List<TimeCard> findAll() {
		List<TimeCard> times_card = em.createQuery("select s from TimeCard s", TimeCard.class).getResultList();
		return times_card;
	}

	public TimeCard update(TimeCard time_card) {
		return em.merge(time_card);
	}

	public void remove(TimeCard time_card) {
		em.remove(em.merge(time_card));
	}
	
}
