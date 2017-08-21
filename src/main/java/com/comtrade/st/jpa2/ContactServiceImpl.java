/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comtrade.st.jpa2;

import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author radoo
 */
@Service("ContactService")
@Repository
@Transactional
public class ContactServiceImpl implements ContactService {

	private Log log = LogFactory.getLog(ContactServiceImpl.class);
	final static String ALL_CONTACT_NATIVE_QUERY
		= "select c.id, c.first_name, c.last_name, c.birth_date, c.version, ct.TEL_NUMBER from contact c inner join contact_Tel_Detail ct on c.ID = ct.CONTACT_ID";

	@PersistenceContext(unitName = "st")
	EntityManager em;

	@Transactional(readOnly = true)
	@Override
	public List<Contact> findAll() {
		List<Contact> contacts = em.createNamedQuery("Contact.findAll", Contact.class).getResultList();
		return contacts;
	}

	@Override
	public List<Contact> findAllWithDetail() {
		List<Contact> contacts = em.createNamedQuery("Contact.findAllWithDetail", Contact.class).getResultList();
		return contacts;
	}

	@Override
	public Contact findById(Long id) {
		TypedQuery<Contact> query = em.createNamedQuery("Contact.findById", Contact.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}

	@Override
	public Contact save(Contact contact) {
		if (contact.getId() == null) {
			em.persist(contact);
		} else {
			em.merge(contact);
		}
		return contact;
	}

	@Override
	public void delete(Contact contact) {
		em.remove(em.contains(contact) ? contact : em.merge(contact));
	}

	@Override
	public List<Contact> findAllByNativeQuery() {
		return em.createNativeQuery(ALL_CONTACT_NATIVE_QUERY, Contact.class).getResultList();
	}

	@Override
	public List<Contact> findAllByNativeQueryRsMapper() {
		return em.createNativeQuery(ALL_CONTACT_NATIVE_QUERY, "contactResult").getResultList();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Contact> findByCriteriaQuery(String firstName, String lastName) {
		log.info("Finding contact for firstName: " + firstName
			+ " and lastName: " + lastName);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Contact> criteriaQuery = cb.createQuery(Contact.class);
		Root<Contact> contactRoot = criteriaQuery.from(Contact.class);
		contactRoot.fetch(Contact_.contactTelDetails, JoinType.LEFT);
		contactRoot.fetch(Contact_.hobbies, JoinType.LEFT);
		criteriaQuery.select(contactRoot).distinct(true);
		Predicate criteria = cb.conjunction();
		if (firstName != null) {
			Predicate p = cb.equal(contactRoot.get(Contact_.firstName),
				firstName);
			criteria = cb.and(criteria, p);
		}
		if (lastName != null) {
			Predicate p = cb.equal(contactRoot.get(Contact_.lastName),
				lastName);
			criteria = cb.and(criteria, p);
		}
		criteriaQuery.where(criteria);
		return em.createQuery(criteriaQuery).getResultList();
	}

}//end ContactServiceImpl
