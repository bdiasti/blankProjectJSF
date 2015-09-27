package br.com.blankprojectsf.service;

import java.util.List;

import javax.ejb.Local;

import br.com.blankprojectsf.model.People;
import br.com.blankprojectsf.service.exception.PeopleAlreadyExists;
import br.com.blankprojectsf.service.exception.PeopleNotFound;


@Local
public interface PeopleManagerLocal {
	
		public People getPessoaInformation(Long PessoaID) throws PeopleNotFound;    
	    public People registerPessoa(People Pessoa) throws PeopleAlreadyExists;    
	    public People updatePessoa(People Pessoa) throws PeopleNotFound;    
	    public void removePessoa(Long PessoaID) throws PeopleNotFound;    
	    public List<People> getAllPessoas(People Pessoa);    
}
