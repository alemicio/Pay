package it.screwdrivers.payroll.dao;

import it.screwdrivers.payroll.pojo.union.Union;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UnionDao {

	@PersistenceContext
	EntityManager em;

	public void add(Union union) {
		em.persist(union);
	}

	public List<Union> findAll() {
		List<Union> unions = em.createQuery("select p from Union s", Union.class).getResultList();
		return unions;
	}

	public Union update(Union union) {
		return em.merge(union);
	}

	public void remove(Union union) {
		em.remove(union);
	}
}
