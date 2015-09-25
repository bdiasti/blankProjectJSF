package br.com.blankprojectsf.backing;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Part;

import sun.misc.IOUtils;
import br.com.blankprojectsf.model.Pessoa;
import br.com.blankprojectsf.service.DepartamentoManagerLocal;
import br.com.blankprojectsf.service.PessoaManagerLocal;
import br.com.blankprojectsf.service.exception.PessoaAlreadyExists;

@Named
@ViewScoped
public class PessoaAddBacking extends BaseBacking implements Serializable {
    @EJB
    private PessoaManagerLocal pessoaManager;  
    
    @Named
    @Produces
    @RequestScoped
    private Pessoa newPessoa = new Pessoa();
    
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
            
            pessoaManager.registerPessoa(newPessoa);
            infoMessage = "Pessoa saved successfully";
            
            newPessoa = new Pessoa();
        } catch (PessoaAlreadyExists ex) {
            Logger.getLogger(PessoaAddBacking.class.getName()).log(Level.SEVERE, null, ex);
            infoMessage = "A Pessoa with the same ISBN already exists";
        } catch (Exception ex) {
            Logger.getLogger(PessoaAddBacking.class.getName()).log(Level.SEVERE, null, ex);
            getContext().addMessage(null, new FacesMessage("An error occurs while saving Pessoa"));            
        }
        
        return null;
    }
    
    public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
      List<FacesMessage> msgs = new ArrayList<FacesMessage>();
      
      Part file = (Part) value;
      
      if (file.getSize() > 1048576) {
          msgs.add(new FacesMessage("file size must not exceed 1 MB"));
      }
      
      if (! "application/pdf".equals(file.getContentType())) {
          msgs.add(new FacesMessage("Pessoa format must be PDF"));
      }
      
      if (!msgs.isEmpty()) {
          throw new ValidatorException(msgs);
      }
    }    
}