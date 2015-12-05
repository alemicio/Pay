package it.screwdrivers.payroll.dao;

import it.screwdrivers.payroll.pojo.union.UnionService;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UnionServiceDao {

	@PersistenceContext
	EntityManager em;

	public void add(UnionService union_service) {
		em.persist(union_service);
	}

	public List<UnionService> findAll() {
		List<UnionService> union_services = em.createQuery("select s from UnionService s", UnionService.class).getResultList();
		return union_services;
	}

	public UnionService update(UnionService union_service) {
		return em.merge(union_service);
	}

	public void remove(UnionService union_service) {
		em.remove(em.merge(union_service));
	}
}
