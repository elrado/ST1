/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comtrade.st.jpa2;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 *
 * @author radoo
 */
@Entity
@Table(name = "hobby")
public class Hobby implements Serializable {

	private String hobbyId;
	private Set<Contact> contacts = new HashSet<Contact>();

	@Id
	@Column(name = "HOBBY_ID")
	public String getHobbyId() {
		return this.hobbyId;
	}

	public void setHobbyId(String hobbyId) {
		this.hobbyId = hobbyId;
	}

	@ManyToMany
	@JoinTable(name = "contact_hobby_detail",
		joinColumns = @JoinColumn(name = "HOBBY_ID"),
		inverseJoinColumns = @JoinColumn(name = "CONTACT_ID"))
	public Set<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}

	public String toString() {
		return "Hobby :" + getHobbyId();
	}
}//end Hobby
