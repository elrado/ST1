/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comtrade.st.jpa2;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

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

}//end ContactServiceImpl
