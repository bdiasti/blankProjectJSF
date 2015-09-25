package br.com.blankprojectsf.backing;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.blankprojectsf.model.User;
import br.com.blankprojectsf.service.UserManagerLocal;
import br.com.blankprojectsf.service.exception.UserNotFound;

@Named
@ViewScoped
public class UserSearchBacking extends BaseBacking implements Serializable {
    
    @EJB
    private UserManagerLocal userManager;

    private List<User> userList;    
    private String infoMessage;
    private User selectedUser;

    
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }    
    
    public String getInfoMessage() {
        return infoMessage;
    }

    public void setInfoMessage(String infoMessage) {
        this.infoMessage = infoMessage;
    }    
    
    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }    
    
    public String retrieveUserList() {
        userList = userManager.retrieveUsers();
        
        if (userList.isEmpty()) {
            infoMessage = "No Users found!";
        } else {
            infoMessage = userList.size() + " user(s) found";
        }
        
        return null;
    }
   
    public String deleteUser() {
        User currentSelectedUser = getSelectedUser();
        try {
            userManager.removeUser(currentSelectedUser.getId());
            userList.remove(currentSelectedUser);    
            infoMessage = "User deleted successfully";            
        } catch (UserNotFound ex) {
            Logger.getLogger(UserSearchBacking.class.getName()).log(Level.SEVERE, null, ex);
            getContext().addMessage(null, new FacesMessage("An error occurs while deleting user"));              
        }

        return null;
    }    
}