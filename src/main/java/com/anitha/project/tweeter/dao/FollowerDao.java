package com.anitha.project.tweeter.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.anitha.project.tweeter.model.Follower;
import com.anitha.project.tweeter.model.Tweet;
import com.anitha.project.tweeter.model.User;

public class FollowerDao extends AbstractDao<Follower> {

	public FollowerDao() {
		super(Follower.class);
	}
	
	public List<Follower> getFollowingUsers(User user) {
		List<Follower> followingUsers = null;
		Session session = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Follower e where e.id.user=:user");
            query.setParameter("user", user);
            followingUsers = query.getResultList();
            session.getTransaction().commit();
        }
        catch (Exception exception) {
           if (session != null) {
               session.getTransaction().rollback();
           }
        }
        finally {
            if (session != null) {
               session.close();
            }
        }
		
		return followingUsers;
		
	}

}
