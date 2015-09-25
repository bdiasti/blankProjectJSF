package br.com.blankprojectsf.service;

import br.com.blankprojectsf.model.Departamento;
import br.com.blankprojectsf.service.exception.DepartamentoAlreadyExists;
import br.com.blankprojectsf.service.exception.DepartamentoNotFound;

import java.util.List;

import javax.ejb.Local;


@Local
public interface DepartamentoManagerLocal {   
    public Departamento getDepartamentoInformation(Long DepartamentoID) throws DepartamentoNotFound;    
    public Departamento registerDepartamento(Departamento Departamento) throws DepartamentoAlreadyExists;    
    public Departamento updateDepartamento(Departamento Departamento) throws DepartamentoNotFound;    
    public void removeDepartamento(Long DepartamentoID) throws DepartamentoNotFound;    
    public byte[] getDepartamentoContent(Long DepartamentoID) throws DepartamentoNotFound;
    public List<Departamento> getAllDepartamentos(Departamento Departamento);    
}
