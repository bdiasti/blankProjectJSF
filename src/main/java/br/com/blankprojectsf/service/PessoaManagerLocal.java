package br.com.blankprojectsf.service;

import java.util.List;

import javax.ejb.Local;

import br.com.blankprojectsf.model.Pessoa;
import br.com.blankprojectsf.service.exception.PessoaAlreadyExists;
import br.com.blankprojectsf.service.exception.PessoaNotFound;


@Local
public interface PessoaManagerLocal {
	
		public Pessoa getPessoaInformation(Long PessoaID) throws PessoaNotFound;    
	    public Pessoa registerPessoa(Pessoa Pessoa) throws PessoaAlreadyExists;    
	    public Pessoa updatePessoa(Pessoa Pessoa) throws PessoaNotFound;    
	    public void removePessoa(Long PessoaID) throws PessoaNotFound;    
	    public List<Pessoa> getAllPessoas(Pessoa Pessoa);    
}
