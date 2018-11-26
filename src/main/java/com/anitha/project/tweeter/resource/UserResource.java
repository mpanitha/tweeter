package com.anitha.project.tweeter.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.anitha.project.tweeter.dao.FollowerDao;
import com.anitha.project.tweeter.dao.TweetDao;
import com.anitha.project.tweeter.dao.UserDao;
import com.anitha.project.tweeter.model.Follower;
import com.anitha.project.tweeter.model.FollowerPK;
import com.anitha.project.tweeter.model.Tweet;
import com.anitha.project.tweeter.model.User;


@Path("users/{user_id}")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
	
	 private UserDao userDao = new UserDao();
	 
	 private TweetDao tweetDao = new TweetDao();
	 
	 private FollowerDao followerDao = new FollowerDao();
	
	@GET
	@Path("/tweets")
	public Response list(@PathParam(value = "user_id") long userId) {
		User user = userDao.get(userId);
		if (user != null) {			
			List<Tweet> tweets = tweetDao.getTweetsForUser(user);		
		    GenericEntity<List<Tweet>> entity = new GenericEntity<List<Tweet>>(tweets) { };
		    return Response.status(Response.Status.OK).entity(entity).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	@GET
	@Path("/feed")
	public Response getFeed(@PathParam(value = "user_id") long userId) {
		User user = userDao.get(userId);
		if (user != null) {
			List<Tweet> tweets = tweetDao.getFeedsForUser(user, followerDao);
		    GenericEntity<List<Tweet>> entity = new GenericEntity<List<Tweet>>(tweets) { };
			return Response.status(Response.Status.OK).entity(entity).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	@POST
	@Path("/tweets")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response tweetMessage(@PathParam(value = "user_id") long userId, Tweet t) {
		User user = userDao.get(userId);
		if (user != null) {
			t.setUser(user);
			boolean success = tweetDao.create(t);
			if (success) {
				return Response.status(Response.Status.CREATED).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	@POST
	@Path("/follow/{follow_id}")
	public Response followUser(@PathParam(value ="user_id") long userId, @PathParam(value="follow_id") long followId) {
		User user = userDao.get(userId);
		User followsUser = userDao.get(followId);	
		if (user != null && followsUser != null) {
			FollowerPK fPK = new FollowerPK();
			fPK.setUser(user);
			fPK.setFollowsUser(followsUser);
			Follower f = new Follower();
			f.setId(fPK);
			boolean success = followerDao.create(f);
			if (success) {
				return Response.status(Response.Status.CREATED).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	@DELETE
	@Path("/follow/{follow_id}")
	public Response unfollowUser(@PathParam(value ="user_id") long userId, @PathParam(value="follow_id") long followId) {
		User user = userDao.get(userId);
		User followsUser = userDao.get(followId);	
		if (user != null && followsUser != null) {
			FollowerPK fPK = new FollowerPK();
			fPK.setUser(user);
			fPK.setFollowsUser(followsUser);
			Follower f = new Follower();
			f.setId(fPK);
			boolean success = followerDao.delete(f);
			if (success) {
				return Response.status(Response.Status.NO_CONTENT).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
	}

}
