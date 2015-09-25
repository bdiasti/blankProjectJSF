package br.com.blankprojectsf.service.exception;

import javax.ejb.ApplicationException;

/**
 *
 * @author Hazem Saleh <hazems@apache.org>
 */
@ApplicationException(rollback=true)
public class UserNotFound extends Exception {
    public UserNotFound () {
        this.message = "User not found";
    }
    
    public UserNotFound(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return this.message; 
    }
    
    private String message;
}
