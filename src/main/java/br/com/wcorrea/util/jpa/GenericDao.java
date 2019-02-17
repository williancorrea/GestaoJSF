package br.com.wcorrea.util.jpa;

import br.com.wcorrea.modelo.filtros.FiltroGlobal;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

public abstract class GenericDao <T, I extends Serializable> {

    @Inject
    transient protected EntityManager entityManager;

    private Class<T> persistedClass;

    protected GenericDao() {
    }

    protected GenericDao(Class<T> persistedClass) {
        this();
        this.persistedClass = persistedClass;
    }

    /**
     * BUSCAR ITEM PELO ID
     * @param id
     * @return
     */
    public T buscarPorID(I id) {
        return entityManager.find(persistedClass, id);
    }

    /**
     * SALVAR OS DADOS DO ITEM
     * @param entity
     * @return
     */
    public T salvar(@Valid T entity){
        entity = entityManager.merge(entity);
        entityManager.flush();
        return entity;
    }

    /**
     * REMOVER ITEM
     * @param id
     */
    public void remover(I id) {
        T entity = buscarPorID(id);
        entityManager.remove(entity);
        entityManager.flush();
    }

    /**
     * LISTA TODOS OS REGISTRO
     * @return
     */
    public List<T> listarTudo() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(persistedClass);
        query.from(persistedClass);
        return entityManager.createQuery(query).getResultList();
    }

    protected Criteria gerarCriteriosFiltros(FiltroGlobal filtro, Criteria criteria) {
        criteria.setFirstResult(filtro.getPrimeiroRegistro());
        criteria.setMaxResults(filtro.getQuantidadeRegistros());

        if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
            criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
        } else if (filtro.getPropriedadeOrdenacao() != null) {
            criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
        }
        return criteria;
    }

}
