package br.com.blankprojectsf.service;

import br.com.blankprojectsf.model.User;
import br.com.blankprojectsf.service.exception.UserAlreadyExists;
import br.com.blankprojectsf.service.exception.UserNotFound;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class UserManager implements UserManagerLocal {
    
    @PersistenceContext(unitName = "blank")
    EntityManager em;  
    
    public User getUser(String userID) throws UserNotFound {
        Query query = em.createQuery("select user.id, user.firstName"
                                     + ", user.lastName from User user where "
                                     + "user.id = :id");
        
        query.setParameter("id", userID);
    
        Object[] userInfo;
        
        try {
            userInfo = (Object[]) query.getSingleResult();
        } catch (NoResultException exception) {
            throw new UserNotFound(exception.getMessage());
        }
        
        User user = new User();
        user.setFirstName((String) userInfo[0]);
        user.setId((String) userInfo[1]);
        user.setLastName((String) userInfo[2]);
        
        return user;        
    }

    public User registerUser(User user) throws UserAlreadyExists {
        Query query = em.createQuery("select User from User user where "
                    + "user.id = :userID");
        
        query.setParameter("userID", user.getId());

        try {
            query.getSingleResult();
            throw new UserAlreadyExists();
        } catch (NoResultException exception) {
            Logger.getLogger(UserManager.class.getName()).log(Level.FINER, "No user found");
        }
        
        em.persist(user);  
        em.flush();        
        
        return user;
    }

    public void removeUser(String userID) throws UserNotFound {
        User user = em.find(User.class, userID);

        if (user == null) {
            throw new UserNotFound();
        }
        
        em.remove(user);
        em.flush();
    }

    public List<User> retrieveUsers() {
        Query query = em.createQuery("select user from user User", User.class);

        List<User> result = query.getResultList();        
        
        if (result == null) {
            return new ArrayList<User>();
        }
        
        return result;
    }

}
