/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comtrade.st.jpa2;

import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author radoo
 */
@Service("jpaContactSummaryUntypeImpl")
@Repository
@Transactional
public class ContactSummaryUntypeImpl {

	private EntityManager em;

	/**
	 * <p>
	 *
	 * </p>
	 * Changes:
	 *
	 * @author author_name date created
	 */
	public void displayAllContactsSummary() {
		List result = em
			.createQuery("SELECT c.firstName, c.lastName, t.telNumber "
				+ " from Contact c left join c.contactTelDetails t "
				+ " WHERE t.telType='Home'").getResultList();
		int count = 0;
		for (Iterator i = result.iterator(); i.hasNext();) {
			Object[] values = (Object[]) i.next();
			System.out.println(++count + ": " + values[0] + ", "
				+ values[1] + ", " + values[2]);
		}
	}//end displayAllContactsSummary

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}
}//end ContactSummaryUntypeImpl
