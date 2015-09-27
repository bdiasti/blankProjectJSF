package br.com.blankprojectsf.backing;
import java.io.Serializable;
import java.util.List;


import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.blankprojectsf.model.People;
import br.com.blankprojectsf.service.PeopleManager;
import br.com.blankprojectsf.service.PeopleManagerLocal;
import br.com.blankprojectsf.service.exception.PeopleNotFound;

@Named
@ViewScoped
public class PeopleSearchBacking extends BaseBacking implements Serializable {

	
	final static Logger logger = Logger.getLogger(PeopleSearchBacking.class);
	
    @EJB
    private PeopleManagerLocal pessoaManager;

    private List<People> pessoaList;    
    private String searchNome;
    private String infoMessage;
    private People selectedPessoa;
    

    public String getsearchNome() {
        return searchNome;
    }

    public void setsearchNome(String searchNome) {
        this.searchNome = searchNome;
    }
    
    public List<People> getPessoaList() {
        return pessoaList;
    }

    public void setPessoaList(List<People> pessoaList) {
        this.pessoaList = pessoaList;
    }    
    
    public String getInfoMessage() {
        return infoMessage;
    }

    public void setInfoMessage(String infoMessage) {
        this.infoMessage = infoMessage;
    }    
    
    public People getSelectedPessoa() {
        return selectedPessoa;
    }

    public void setSelectedPessoa(People selectedPessoa) {
        this.selectedPessoa = selectedPessoa;
    }    
    
    public String retrievePessoaList() {
        People searchablePessoa = new People();
        
        logger.info("Buscando nome " + searchNome);
        searchablePessoa.setNome(searchNome);
        
        pessoaList = pessoaManager.getAllPessoas(searchablePessoa);
        
        if (pessoaList.isEmpty()) {
            infoMessage = "No Pessoa results found";
        } else {
            infoMessage = pessoaList.size() + " Pessoa(s) found";
        }
        
        return null;
    }
    
    
    public String deletePessoa() {
        try {
            People currentSelectedPessoa = getSelectedPessoa();
            
            pessoaManager.removePessoa(currentSelectedPessoa.getCpf());
            pessoaList.remove(currentSelectedPessoa);
            
            infoMessage = "Pessoa deleted successfully";
        } catch (PeopleNotFound ex) {
            getContext().addMessage(null, new FacesMessage("An error occurs while deleting the Pessoa"));            
        }
        
        return null;
    }    
    
}