package br.com.blankprojectsf.backing;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import br.com.blankprojectsf.model.People;
import br.com.blankprojectsf.service.PeopleManagerLocal;
import br.com.blankprojectsf.service.exception.PeopleAlreadyExists;

@Named
@ViewScoped
public class PeopleAddBacking extends BaseBacking implements Serializable {
	
	final static Logger logger = Logger.getLogger(PeopleAddBacking.class);
	
    @EJB
    private PeopleManagerLocal pessoaManager;  
    
    @Named
    @Produces
    @RequestScoped
    private People newPessoa = new People();
    
    private String infoMessage;
    private Part filePart;
    
    public String getInfoMessage() {
        return infoMessage;
    }

    public void setInfoMessage(String infoMessage) {
        this.infoMessage = infoMessage;
    }    
    
    public Part getFilePart() {
        return filePart;
    }

    public void setFilePart(Part filePart) {
        this.filePart = filePart;
    }    
    
    public String savePessoa() {
        try {             
        	
        	 logger.info("Salvando pessoa");
            pessoaManager.registerPessoa(newPessoa);
            infoMessage = "Pessoa saved successfully";
            
            newPessoa = new People();
        } catch (PeopleAlreadyExists ex) {
        	 logger.error("Erro "  , ex);
            infoMessage = "A Pessoa with the same ISBN already exists";
        } catch (Exception ex) {
            logger.error("Erro "  , ex);
            getContext().addMessage(null, new FacesMessage("An error occurs while saving Pessoa"));            
        }
        
        return null;
    }
    
}