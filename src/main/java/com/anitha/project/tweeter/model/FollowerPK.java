package com.anitha.project.tweeter.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class FollowerPK implements Serializable {

	@ManyToOne
    @JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "follows_id")
	private User followsUser;
	
	public FollowerPK() {
		user = new User();
		followsUser = new User();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getFollowsUser() {
		return followsUser;
	}

	public void setFollowsUser(User followsUser) {
		this.followsUser = followsUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((followsUser == null) ? 0 : followsUser.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FollowerPK other = (FollowerPK) obj;
		if (followsUser == null) {
			if (other.followsUser != null)
				return false;
		} else if (!followsUser.equals(other.followsUser))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	

}
