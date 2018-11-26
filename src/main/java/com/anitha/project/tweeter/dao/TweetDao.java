package com.anitha.project.tweeter.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.anitha.project.tweeter.model.Follower;
import com.anitha.project.tweeter.model.Tweet;
import com.anitha.project.tweeter.model.User;

public class TweetDao extends AbstractDao<Tweet> {

	//private FollowerDao followerDao;
	
	public TweetDao() {
		super(Tweet.class);
	}
	
	public List<Tweet> getTweetsForUser(User user) {
		List<Tweet> tweets = null;
        Session session = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Query<Tweet> query = session.createQuery("from Tweet e where e.user=:user order by createdAt desc");
            query.setParameter("user", user);
            query.setMaxResults(100);
            tweets = query.getResultList();
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

        return tweets;
		
	}
	
	public List<Tweet> getFeedsForUser(User user, FollowerDao followerDao) {
		List<Tweet> tweets = null;
        Session session = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            List<Follower> followingUsers = followerDao.getFollowingUsers(user);

            List<User> following = new ArrayList<>();
            following.add(user);
            for (Follower f : followingUsers) {
            	following.add(f.getId().getFollowsUser());
            }
            Query<Tweet> query = session.createQuery("from Tweet e where e.user in (:users) order by createdAt desc");
            query.setParameterList("users", following);
            query.setMaxResults(100);
            tweets = query.getResultList();
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

        return tweets;
		
	}

}
