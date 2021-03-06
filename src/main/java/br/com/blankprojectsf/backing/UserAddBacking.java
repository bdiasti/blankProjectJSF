package br.com.blankprojectsf.backing;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.blankprojectsf.model.User;
import br.com.blankprojectsf.service.UserManagerLocal;
import br.com.blankprojectsf.service.exception.UserAlreadyExists;

@Named
@ViewScoped
public class UserAddBacking extends BaseBacking implements Serializable {
    @EJB
    private UserManagerLocal userManager;  
    
    @Named
    @Produces
    @RequestScoped
    private User newUser = new User();
    
    private String infoMessage;
    
    public String getInfoMessage() {
        return infoMessage;
    }

    public void setInfoMessage(String infoMessage) {
        this.infoMessage = infoMessage;
    }    
    
    public String registerUser() {         
        if (! newUser.getPassword().equals(newUser.getPassword2())) {
            getContext().addMessage(null, new FacesMessage("Passwords must be identical"));             
            
            return null;
        }
        
        try {
            userManager.registerUser(newUser);
            infoMessage = "User saved successfully";
            
            newUser = new User(); 
        } catch (UserAlreadyExists ex) {
            Logger.getLogger(UserAddBacking.class.getName()).log(Level.SEVERE, null, ex);
            infoMessage = "Login name already exists";
        } catch (Exception ex) {
            Logger.getLogger(UserAddBacking.class.getName()).log(Level.SEVERE, null, ex);
            getContext().addMessage(null, new FacesMessage("An error occurs while registering user"));             
        }
        
        return null;
    }
}