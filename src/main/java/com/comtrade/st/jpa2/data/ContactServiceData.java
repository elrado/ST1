/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comtrade.st.jpa2.data;

import com.comtrade.st.jpa2.Contact;
import java.util.List;

/**
 *
 * @author radoo
 */
public interface ContactServiceData {

	List<Contact> findAll();

	List<Contact> findByFirstName(String firstName);

	List<Contact> findByFirstNameAndLastName(String firstName, String lastName);
	List<Contact>FindByLastName(String lastName);
}//end ContactServiceData
