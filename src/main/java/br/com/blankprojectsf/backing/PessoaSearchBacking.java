package br.com.blankprojectsf.backing;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.blankprojectsf.model.Pessoa;
import br.com.blankprojectsf.service.PessoaManagerLocal;
import br.com.blankprojectsf.service.exception.PessoaNotFound;

@Named
@ViewScoped
public class PessoaSearchBacking extends BaseBacking implements Serializable {

	
    
    @EJB
    private PessoaManagerLocal pessoaManager;

    private List<Pessoa> pessoaList;    
    private String searchNome;
    private String infoMessage;
    private Pessoa selectedPessoa;
    

    public String getsearchNome() {
        return searchNome;
    }

    public void setsearchNome(String searchNome) {
        this.searchNome = searchNome;
    }
    
    public List<Pessoa> getPessoaList() {
        return pessoaList;
    }

    public void setPessoaList(List<Pessoa> pessoaList) {
        this.pessoaList = pessoaList;
    }    
    
    public String getInfoMessage() {
        return infoMessage;
    }

    public void setInfoMessage(String infoMessage) {
        this.infoMessage = infoMessage;
    }    
    
    public Pessoa getSelectedPessoa() {
        return selectedPessoa;
    }

    public void setSelectedPessoa(Pessoa selectedPessoa) {
        this.selectedPessoa = selectedPessoa;
    }    
    
    public String retrievePessoaList() {
        Pessoa searchablePessoa = new Pessoa();
        
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
            Pessoa currentSelectedPessoa = getSelectedPessoa();
            
            pessoaManager.removePessoa(currentSelectedPessoa.getCpf());
            pessoaList.remove(currentSelectedPessoa);
            
            infoMessage = "Pessoa deleted successfully";
        } catch (PessoaNotFound ex) {
            Logger.getLogger(PessoaSearchBacking.class.getName()).log(Level.SEVERE, null, ex);
            getContext().addMessage(null, new FacesMessage("An error occurs while deleting the Pessoa"));            
        }
        
        return null;
    }    
    
}