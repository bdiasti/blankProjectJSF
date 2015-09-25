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

/**
 *
 * @author Hazem Saleh <hazems@apache.org>
 */
@Stateless
public class UserManager implements UserManagerLocal {
    
    @PersistenceContext(unitName = "blank")
    EntityManager em;  
    
    public User getUser(String userID) throws UserNotFound {
        Query query = em.createQuery("select megaUser.id, megaUser.firstName"
                                     + ", megaUser.lastName from MegaUser megaUser where "
                                     + "megaUser.id = :id");
        
        query.setParameter("id", userID);
    
        Object[] megaUserInfo;
        
        try {
            megaUserInfo = (Object[]) query.getSingleResult();
        } catch (NoResultException exception) {
            throw new UserNotFound(exception.getMessage());
        }
        
        User megaUser = new User(
                (String) megaUserInfo[0],
                (String) megaUserInfo[1], 
                (String) megaUserInfo[2], 
                null);
        
        return megaUser;        
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
        User megaUser = em.find(User.class, userID);

        if (megaUser == null) {
            throw new UserNotFound();
        }
        
        em.remove(megaUser);
        em.flush();
    }

    public List<User> retrieveUsers() {
        Query query = em.createQuery("select megaUser from MegaUser megaUser", User.class);

        List<User> result = query.getResultList();        
        
        if (result == null) {
            return new ArrayList<User>();
        }
        
        return result;
    }

}
