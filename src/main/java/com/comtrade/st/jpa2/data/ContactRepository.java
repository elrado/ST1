/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comtrade.st.jpa2.data;

import com.comtrade.st.jpa2.Contact;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author radoo
 */
public interface ContactRepository extends CrudRepository<Contact, Long> {

	List<Contact> findByFirstName(String firstName);

	List<Contact> findByFirstNameAndLastName(String firstName, String lastName);
	@Query("Select c FROM Contact c WHERE c.lastName LIKE %:lastName%")
	List<Contact>FindByLastName(@Param("lastName") String lastName);
}//end ContactRepository
