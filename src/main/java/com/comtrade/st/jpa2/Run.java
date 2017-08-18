/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comtrade.st.jpa2;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author radoo
 */
public class Run {

	private static final Logger logger = Logger.getLogger(Run.class);

	public static void main(String[] args) {
		ApplicationContext ctx
			= new AnnotationConfigApplicationContext(Config.class);
				//List all contacts
		ContactService jpaContactService = (ContactService) ctx.getBean("ContactService", ContactService.class);
		ContactSummaryUntypeImpl jpaContactSummaryUntypeImpl = (ContactSummaryUntypeImpl) ctx.getBean("jpaContactSummaryUntypeImpl", ContactSummaryUntypeImpl.class);
		ContactSummaryService jpaContactSummaryService = (ContactSummaryService) ctx.getBean("ContactSummaryService", ContactSummaryService.class);
		List<Contact> contacts = jpaContactService.findAll();
		Set<ContactTelDetail> contactTelDetails;

		System.out.println("****************************contacts********************************************");
		for (Contact c : contacts) {
			System.out.println("Listing all contacts with details");
			System.out.println(c);
		}
		System.out.println("****************************contacts with details********************************************");
		contacts = jpaContactService.findAllWithDetail();
		//List all contacts with details
		for (Contact c : contacts) {
			System.out.println("Listing all contacts with details");
			System.out.println(c);
			contactTelDetails = c.getContactTelDetails();
			if (contactTelDetails != null) {
				for (ContactTelDetail cd : contactTelDetails) {
					System.out.print("	");
					System.out.println(cd);
				}//end for
				if (c.getHobbies() != null) {
					for (Hobby hobby : c.getHobbies()) {
						System.out.println(hobby);
					}
				}
			}//end if
		}
		System.out.println("****************************contacts findById********************************************");
		Contact contact = jpaContactService.findById(4L);
		System.out.println("Listing contact with id 4");
		System.out.println(contact);

		System.out.println("****************************Untyped result*******************************************");
		jpaContactSummaryUntypeImpl.displayAllContactsSummary();

		System.out.println("****************************ContactSummaryService findAll (TYPED RESULT)********************************************");
		jpaContactSummaryUntypeImpl.displayAllContactsSummary();

		System.out.println("****************************Insert Contact********************************************");
		contact = new Contact();
		contact.setFirstName("Ime");
		contact.setLastName("Priimek");
		contact.setBirthDate(new Date());
		jpaContactService.save(contact);
		Long id = contact.getId();
		contact = jpaContactService.findById(id);
		System.out.println("Listing contact with id " + id);
		System.out.println(contact);
		contact.setBirthDate(new Date(1980, 7, 1));
		jpaContactService.save(contact);
		contact = jpaContactService.findById(id);
		System.out.println("Listing contact with id " + id);
		System.out.println(contact);
		System.out.println(contacts.size());
		jpaContactService.delete(contact);
		System.out.println(contacts.size());

		System.out.println("****************************ContactSummaryService findAllByNativeQuery ********************************************");
		contacts = jpaContactService.findAllByNativeQuery();
		for (Contact c : contacts) {
			System.out.println("Listing all contacts from nativeQ");
			System.out.println(c);
		}
		
		System.out.println("****************************ContactSummaryService findAllByNativeQueryRsMapper ********************************************");
		contacts = jpaContactService.findAllByNativeQueryRsMapper();
		for (Contact c : contacts) {
			System.out.println("Listing all contacts from nativeQ Rs Mapper");
			System.out.println(c);
		}
	}//end main
}//end Run
