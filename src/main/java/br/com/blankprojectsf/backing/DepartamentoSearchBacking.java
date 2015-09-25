package br.com.blankprojectsf.backing;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.blankprojectsf.model.Departamento;
import br.com.blankprojectsf.service.DepartamentoManagerLocal;
import br.com.blankprojectsf.service.exception.DepartamentoNotFound;

@Named
@ViewScoped
public class DepartamentoSearchBacking extends BaseBacking implements Serializable {
    
    @EJB
    private DepartamentoManagerLocal departamentoManager;

    private List<Departamento> departamentoList;    
    private String infoMessage;
    private Departamento selectedDepartamento;

    
    public List<Departamento> getDepartamentoList() {
        return departamentoList;
    }

    public void setDepartamentoList(List<Departamento> DepartamentoList) {
        this.departamentoList = DepartamentoList;
    }    
    
    public String getInfoMessage() {
        return infoMessage;
    }

    public void setInfoMessage(String infoMessage) {
        this.infoMessage = infoMessage;
    }    
    
    public Departamento getSelectedDepartamento() {
        return selectedDepartamento;
    }

    public void setSelectedDepartamento(Departamento selectedDepartamento) {
        this.selectedDepartamento = selectedDepartamento;
    }    
    
    public String retrieveDepartamentoList() {
    	
        departamentoList = departamentoManager.getAllDepartamentos(new Departamento());
        
        if (departamentoList.isEmpty()) {
            infoMessage = "No Departamentos found!";
        } else {
            infoMessage = departamentoList.size() + " Departamento(s) found";
        }
        
        return null;
    }
   
    public String deleteDepartamento() {
        Departamento currentSelectedDepartamento = getSelectedDepartamento();
        try {
            departamentoManager.removeDepartamento(currentSelectedDepartamento.getId());
            departamentoList.remove(currentSelectedDepartamento);    
            infoMessage = "Departamento deleted successfully";            
        } catch (DepartamentoNotFound ex) {
            Logger.getLogger(DepartamentoSearchBacking.class.getName()).log(Level.SEVERE, null, ex);
            getContext().addMessage(null, new FacesMessage("An error occurs while deleting Departamento"));              
        }

        return null;
    }    
}