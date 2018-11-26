package com.anitha.project.tweeter.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="followers")
public class Follower implements Serializable {
	
	@EmbeddedId
	private FollowerPK id;

	public FollowerPK getId() {
		return id;
	}

	public void setId(FollowerPK id) {
		this.id = id;
	}
	
	public Follower() {
		this.id = new FollowerPK();
	}
	
}
