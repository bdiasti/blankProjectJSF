package br.com.blankprojectsf.service;

import java.util.List;

import javax.ejb.Local;

import br.com.blankprojectsf.model.User;
import br.com.blankprojectsf.service.exception.UserAlreadyExists;
import br.com.blankprojectsf.service.exception.UserNotFound;


@Local
public interface UserManagerLocal {
    public User getUser(String userID) throws UserNotFound;
    public List<User> retrieveUsers();        
    public User registerUser(User user) throws UserAlreadyExists;
    public void removeUser(String userID) throws UserNotFound;    
}
