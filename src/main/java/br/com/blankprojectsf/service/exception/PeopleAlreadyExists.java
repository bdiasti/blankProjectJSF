package br.com.blankprojectsf.service.exception;

import javax.ejb.ApplicationException;

/**
 *
 * @author Hazem Saleh <hazems@apache.org>
 */
@ApplicationException(rollback=true)
public class PeopleAlreadyExists extends Exception {
    public PeopleAlreadyExists () {
        this.message = "Pessoa já existes";
    }
    
    public PeopleAlreadyExists(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return this.message; 
    }
    
    private String message;
}
