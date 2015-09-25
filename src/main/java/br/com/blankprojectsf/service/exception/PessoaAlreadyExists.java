package br.com.blankprojectsf.service.exception;

import javax.ejb.ApplicationException;

/**
 *
 * @author Hazem Saleh <hazems@apache.org>
 */
@ApplicationException(rollback=true)
public class PessoaAlreadyExists extends Exception {
    public PessoaAlreadyExists () {
        this.message = "Pessoa já existes";
    }
    
    public PessoaAlreadyExists(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return this.message; 
    }
    
    private String message;
}
