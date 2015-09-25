package br.com.blankprojectsf.service.exception;

import javax.ejb.ApplicationException;

/**
 *
 * @author Hazem Saleh <hazems@apache.org>
 */
@ApplicationException(rollback=true)
public class DepartamentoAlreadyExists extends Exception {
    public DepartamentoAlreadyExists () {
        this.message = "Departamento já existe";
    }
    
    public DepartamentoAlreadyExists(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return this.message; 
    }
    
    private String message;
}
