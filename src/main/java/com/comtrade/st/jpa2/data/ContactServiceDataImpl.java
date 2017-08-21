/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comtrade.st.jpa2.data;

import com.comtrade.st.jpa2.Contact;
import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author radoo
 */
@Service("springJpaContactService")
@Repository
@Transactional
public class ContactServiceDataImpl implements ContactServiceData {

	@Autowired
	private ContactRepository contactRepository;

	@Transactional(readOnly = true)
	public List<Contact> findAll() {
		return Lists.newArrayList(contactRepository.findAll());
	}

	@Transactional(readOnly = true)
	public List<Contact> findByFirstName(String firstName) {
		return contactRepository.findByFirstName(firstName);
	}

	@Transactional(readOnly = true)
	public List<Contact> findByFirstNameAndLastName(
		String firstName, String lastName) {
		return contactRepository.findByFirstNameAndLastName(
			firstName, lastName);
	}
}//end ContactServiceDataImpl
