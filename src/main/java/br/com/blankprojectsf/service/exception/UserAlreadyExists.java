package br.com.blankprojectsf.service.exception;

import javax.ejb.ApplicationException;

/**
 *
 * @author Hazem Saleh <hazems@apache.org>
 */
@ApplicationException(rollback=true)
public class UserAlreadyExists extends Exception {
    public UserAlreadyExists () {
        this.message = "User already exists";
    }
    
    public UserAlreadyExists(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return this.message; 
    }
    
    private String message;
}
