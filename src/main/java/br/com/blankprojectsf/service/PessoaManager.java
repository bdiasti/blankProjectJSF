package br.com.blankprojectsf.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.blankprojectsf.model.Pessoa;
import br.com.blankprojectsf.service.exception.PessoaAlreadyExists;
import br.com.blankprojectsf.service.exception.PessoaNotFound;


@Stateless
public class PessoaManager implements PessoaManagerLocal {

    @PersistenceContext(unitName = "blank")
    EntityManager em;    
    
    public Pessoa getPessoaInformation(Long PessoaID) throws PessoaNotFound {
        Query query = em.createQuery("select pessoa.cpf, pessoa.nome, pessoa.email, pessoa.sexo "
                    + "from Pessoa pessoa where "
                    + "pessoa.cpf = :id");
        
        query.setParameter("id", PessoaID);
        
        Object[] PessoaInfo = null;

        try {
            PessoaInfo = (Object[]) query.getSingleResult();
        } catch (NoResultException exception) {
            throw new PessoaNotFound(exception.getMessage());
        }
        
        Pessoa pessoa = new Pessoa();
        pessoa.setCpf((Long) PessoaInfo[0]);
        pessoa.setNome((String) PessoaInfo[1]);
        pessoa.setEmail((String) PessoaInfo[2]);
        pessoa.setSexo((String) PessoaInfo[3]);
        
        
        return pessoa;
    }

    public void removePessoa(Long PessoaID) throws PessoaNotFound {
        Pessoa Pessoa = em.find(Pessoa.class, PessoaID);

        if (Pessoa == null) {
            throw new PessoaNotFound();
        }
        
        em.remove(Pessoa);
        em.flush();
    }
    
    

    public List<Pessoa> getAllPessoas(Pessoa searchablePessoa) {
        List<Pessoa> Pessoas = new ArrayList<Pessoa>();
        String searchableNome = searchablePessoa.getNome();
        
        Query query = em.createQuery("select pessoa.cpf, pessoa.nome, pessoa.email, pessoa.sexo "
                    + " from Pessoa pessoa where "
                    + "pessoa.nome like :nome");
        
        query.setParameter("nome", "%" + searchableNome + "%");

        List<Object[]> pessoaList = (List<Object[]>) query.getResultList();
        
        if (pessoaList == null) {
            return Pessoas;
        }
        
        for (Object[] pessoaInfo : pessoaList) {
        	
            Pessoa pessoa = new Pessoa();
            pessoa.setCpf((Long) pessoaInfo[0]);
            pessoa.setNome((String) pessoaInfo[1]);
            pessoa.setEmail((String) pessoaInfo[2]);
            pessoa.setSexo((String) pessoaInfo[3]);
               
            
            Pessoas.add(pessoa);
        }
        
        return Pessoas;       
    }

    public Pessoa registerPessoa(Pessoa pessoa) throws PessoaAlreadyExists {
        Query query = em.createQuery("select Pessoa from Pessoa Pessoa where "
                    + "Pessoa.id = :id");
        
        query.setParameter("id", pessoa.getCpf());

        try {
            query.getSingleResult();
            throw new PessoaAlreadyExists();
        } catch (NoResultException exception) {
            Logger.getLogger(PessoaManager.class.getName()).log(Level.FINER, "Não tem Pessoa similares");
        }
        
        em.persist(pessoa);   
        em.flush();
        
        return pessoa;
    }

    public Pessoa updatePessoa(Pessoa Pessoa) throws PessoaNotFound {
        Pessoa updatablePessoa = em.find(Pessoa.class, Pessoa.getCpf());

        if (updatablePessoa == null) {
            throw new PessoaNotFound();
        }
        
        mergePessoaAttrs(Pessoa, updatablePessoa);
        
        em.merge(updatablePessoa);
        em.flush();
                
        return Pessoa;
    }

    private void mergePessoaAttrs(Pessoa pessoa, Pessoa updatablePessoa) {
        if (pessoa.getCpf() != null) {
            updatablePessoa.setCpf(pessoa.getCpf());
        }
        
        if (pessoa.getNome() != null) {
            updatablePessoa.setNome(pessoa.getNome());
        }
        
        if (pessoa.getSexo() != null) {
            updatablePessoa.setSexo(pessoa.getSexo());
        }
        
    }

}
