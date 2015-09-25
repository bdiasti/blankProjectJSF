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

import br.com.blankprojectsf.model.Departamento;
import br.com.blankprojectsf.service.exception.DepartamentoAlreadyExists;
import br.com.blankprojectsf.service.exception.DepartamentoNotFound;


@Stateless
public class DepartamentoManager implements DepartamentoManagerLocal {

    @PersistenceContext(unitName = "blank")
    EntityManager em;    
    
    public Departamento getDepartamentoInformation(Long DepartamentoID) throws DepartamentoNotFound {
        Query query = em.createQuery("select departamento.id, departamento.nome, departamento.sigla "
                    + "from Departamento departamento where "
                    + "departamento.id = :id");
        
        query.setParameter("id", DepartamentoID);
        
        Object[] departamentoInfo = null;

        try {
            departamentoInfo = (Object[]) query.getSingleResult();
        } catch (NoResultException exception) {
            throw new DepartamentoNotFound(exception.getMessage());
        }
        
        Departamento departamento = new Departamento();
        departamento.setId((Long) departamentoInfo[0]);
        departamento.setNome((String) departamentoInfo[1]);
        departamento.setSigla((String) departamentoInfo[2]);
        
        return departamento;
    }

    public void removeDepartamento(Long DepartamentoID) throws DepartamentoNotFound {
        Departamento departamento = em.find(Departamento.class, DepartamentoID);

        if (departamento == null) {
            throw new DepartamentoNotFound();
        }
        
        em.remove(departamento);
        em.flush();
    }
    
    
    public byte[] getDepartamentoContent(Long DepartamentoID) throws DepartamentoNotFound {
        byte[] content = null;
        
        try {
            content = (byte[]) em.createQuery("Select departamento.content from Departamento departamento where departamento.id=:id")
                                 .setParameter("id", DepartamentoID)
                                 .getSingleResult();
        } catch (NoResultException exception) {
            throw new DepartamentoNotFound(exception.getMessage());
        }
        
        return content;
    }

    public List<Departamento> getAllDepartamentos(Departamento searchableDepartamento) {
        List<Departamento> departamentos = new ArrayList<Departamento>();
        String searchableNome = searchableDepartamento.getNome();
        
        Query query = em.createQuery("select departamento.id, departamento.nome, departamento.sigla "
                    + " from Departamento departamento where "
                    + "Departamento.nome like :nome");
        
        query.setParameter("nome", "%" + searchableNome + "%");

        List<Object[]> DepartamentoList = (List<Object[]>) query.getResultList();
        
        if (DepartamentoList == null) {
            return departamentos;
        }
        
        for (Object[] departamentoInfo : DepartamentoList) {
        	   
        	   Departamento departamento = new Departamento();
               departamento.setId((Long) departamentoInfo[0]);
               departamento.setNome((String) departamentoInfo[1]);
               departamento.setSigla((String) departamentoInfo[2]);
               
            
            departamentos.add(departamento);
        }
        
        return departamentos;       
    }

    public Departamento registerDepartamento(Departamento departamento) throws DepartamentoAlreadyExists {
        Query query = em.createQuery("select departamento from Departamento departamento where "
                    + "departamento.id = :id");
        
        query.setParameter("id", departamento.getId());

        try {
            query.getSingleResult();
            throw new DepartamentoAlreadyExists();
        } catch (NoResultException exception) {
            Logger.getLogger(DepartamentoManager.class.getName()).log(Level.FINER, "Não tem departamento similares");
        }
        
        em.persist(departamento);   
        em.flush();
        
        return departamento;
    }

    public Departamento updateDepartamento(Departamento departamento) throws DepartamentoNotFound {
        Departamento updatableDepartamento = em.find(Departamento.class, departamento.getId());

        if (updatableDepartamento == null) {
            throw new DepartamentoNotFound();
        }
        
        mergeDepartamentoAttrs(departamento, updatableDepartamento);
        
        em.merge(updatableDepartamento);
        em.flush();
                
        return departamento;
    }

    private void mergeDepartamentoAttrs(Departamento departamento, Departamento updatableDepartamento) {
        if (departamento.getId() != null) {
            updatableDepartamento.setId(departamento.getId());
        }
        
        if (departamento.getNome() != null) {
            updatableDepartamento.setNome(departamento.getNome());
        }
        
        if (departamento.getSigla() != null) {
            updatableDepartamento.setSigla(departamento.getSigla());
        }
        
    }

}
