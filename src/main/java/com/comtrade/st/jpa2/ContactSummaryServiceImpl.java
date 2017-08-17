/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.comtrade.st.jpa2;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

/**
 *
 * @author radoo
 */
@Service("ContactSummaryService")
public class ContactSummaryServiceImpl implements ContactSummaryService{
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<ContactSummary> findAll() {
		List<ContactSummary> result = em.createQuery(
			"SELECT new com.comtrade.st.jpa2.ContactSummary(c.firstName, c.lastName, t.telNumber) FROM Contact c left join c.contactTelDetails t"
			+ "WHERE t.telType='Home'", 
			ContactSummary.class).getResultList();
		return result;
	}

}//end ContactSummaryServiceImpl
