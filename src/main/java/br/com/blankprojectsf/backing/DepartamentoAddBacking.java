package br.com.blankprojectsf.backing;

import br.com.blankprojectsf.model.Departamento;
import br.com.blankprojectsf.service.DepartamentoManagerLocal;
import br.com.blankprojectsf.service.exception.DepartamentoAlreadyExists;




import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named
@ViewScoped
public class DepartamentoAddBacking extends BaseBacking implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
    private DepartamentoManagerLocal departamentoManager;  
    
    @Named
    @Produces
    @RequestScoped
    private Departamento departamento = new Departamento();
    
    private String infoMessage;
    
    public String getInfoMessage() {
        return infoMessage;
    }

    public void setInfoMessage(String infoMessage) {
        this.infoMessage = infoMessage;
    }    
    
    public String registerDepartamento() {         
    	
        try {
            departamentoManager.registerDepartamento(departamento);
            infoMessage = "Departamento Salvo com sucesso";
            
            departamento = new Departamento(); 
            
        } catch (DepartamentoAlreadyExists ex) {
            Logger.getLogger(DepartamentoAddBacking.class.getName()).log(Level.SEVERE, null, ex);
            infoMessage = "Departamento já existe";
        } catch (Exception ex) {
            Logger.getLogger(DepartamentoAddBacking.class.getName()).log(Level.SEVERE, null, ex);
            getContext().addMessage(null, new FacesMessage("Ocorreu um erro ao inserir um departamento"));             
        }
        
        return null;
    }
}