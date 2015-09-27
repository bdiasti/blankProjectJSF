package br.com.blankprojectsf.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import br.com.blankprojectsf.backing.PeopleAddBacking;
import br.com.blankprojectsf.model.People;
import br.com.blankprojectsf.service.exception.PeopleAlreadyExists;
import br.com.blankprojectsf.service.exception.PeopleNotFound;


@Stateless
public class PeopleManager implements PeopleManagerLocal {

    @PersistenceContext(unitName = "blank")
    EntityManager em;   
    
    final static Logger logger = Logger.getLogger(PeopleManager.class);
    
    public People getPessoaInformation(Long PessoaID) throws PeopleNotFound {
        Query query = em.createQuery("select pessoa.cpf, pessoa.nome, pessoa.email, pessoa.sexo "
                    + "from Pessoa pessoa where "
                    + "pessoa.cpf = :id");
        
        query.setParameter("id", PessoaID);
        
        Object[] PessoaInfo = null;

        try {
            PessoaInfo = (Object[]) query.getSingleResult();
        } catch (NoResultException exception) {
            throw new PeopleNotFound(exception.getMessage());
        }
        
        People pessoa = new People();
        pessoa.setCpf((Long) PessoaInfo[0]);
        pessoa.setNome((String) PessoaInfo[1]);
        pessoa.setEmail((String) PessoaInfo[2]);
        pessoa.setSexo((String) PessoaInfo[3]);
        
        
        return pessoa;
    }

    public void removePessoa(Long PessoaID) throws PeopleNotFound {
        People Pessoa = em.find(People.class, PessoaID);

        if (Pessoa == null) {
            throw new PeopleNotFound();
        }
        
        em.remove(Pessoa);
        em.flush();
    }
    
    

    public List<People> getAllPessoas(People searchablePessoa) {
        List<People> pessoas = new ArrayList<People>();
        String searchableNome = searchablePessoa.getNome();
        
        TypedQuery<People>  query = em.createQuery("select pessoa"
                    + " from People pessoa where "
                    + "pessoa.nome like :pesquisa", People.class);
        
        query.setParameter("pesquisa", "%" + searchableNome + "%");

        List<People>  pessoaList =  query.getResultList();
        
        if (pessoaList == null) {
            return pessoas;
        }
        
        for (People p : pessoaList) {
            pessoas.add(p);
        }
        
        return pessoas;       
    }

    public People registerPessoa(People pessoa) throws PeopleAlreadyExists {
        Query query = em.createQuery("select pessoa from People pessoa where "
                    + "pessoa.cpf = :id");
        
        logger.info("Salvando pessoa");
        query.setParameter("id", pessoa.getCpf());

        try {
            query.getSingleResult();
            throw new PeopleAlreadyExists();
        } catch (NoResultException exception) {
            logger.error( "Não tem Pessoa similares");
        }
        
        em.persist(pessoa);   
        em.flush();
        
        return pessoa;
    }

    public People updatePessoa(People Pessoa) throws PeopleNotFound {
        People updatablePessoa = em.find(People.class, Pessoa.getCpf());

        if (updatablePessoa == null) {
            throw new PeopleNotFound();
        }
        
        mergePessoaAttrs(Pessoa, updatablePessoa);
        
        em.merge(updatablePessoa);
        em.flush();
                
        return Pessoa;
    }

    private void mergePessoaAttrs(People pessoa, People updatablePessoa) {
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
