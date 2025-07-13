package com.jtspringproject.JtSpringProject.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.sound.midi.Soundbank;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.jtspringproject.JtSpringProject.models.User;


@Repository
public class userDao {
	@Autowired
    private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }
   @Transactional
    public List<User> getAllUser() {
        Session session = this.sessionFactory.getCurrentSession();
		List<User>  userList = session.createQuery("from User").list();
        return userList;
    }
    
    @Transactional
	public User saveUser(User user) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(user);
		System.out.println("User added" + user.getId());
        return user;
	}
    
//    public User checkLogin() {
//    	this.sessionFactory.getCurrentSession().
//    }


	@Transactional
	public User getUser(String username, String password) {
		Query<User> query = sessionFactory.getCurrentSession()
				.createQuery("from User where username = :username", User.class);
		query.setParameter("username", username);

		try {
			User user = query.getSingleResult();

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			if (encoder.matches(password, user.getPassword())) {
				return user;
			} else {
				System.out.println("Invalid password for user: " + username);
				return new User(); // or return null based on your logic
			}

		} catch (Exception e) {
			System.out.println("User not found: " + e.getMessage());
			return new User();
		}
	}


	@Transactional
	public boolean userExists(String username) {
		Query query = sessionFactory.getCurrentSession().createQuery("from User where username = :username");
		query.setParameter("username",username);
		return !query.getResultList().isEmpty();
	}
	@Transactional
	public User getUserByUsername(String username) {
		Query<User> query = sessionFactory.getCurrentSession()
				.createQuery("from User where username = :username", User.class);
		query.setParameter("username", username);

		List<User> result = query.getResultList();

		if (result.size() == 1) {
			return result.get(0);
		} else if (result.isEmpty()) {
			System.out.println("No user found with username: " + username);
			return null;
		} else {
			System.err.println("Multiple users found with username: " + username);
			return result.get(0); // TEMP fallback
		}
	}


//	@Transactional
//	public User getUserByUsername(String username) {
//	        Query<User> query = sessionFactory.getCurrentSession().createQuery("from User where username = :username", User.class);
//	        query.setParameter("username", username);
//
//	        try {
//	            return query.getSingleResult();
//	        } catch (Exception e) {
//	            System.out.println(e.getMessage());
//	            return null;
//	        }
//    	}

}