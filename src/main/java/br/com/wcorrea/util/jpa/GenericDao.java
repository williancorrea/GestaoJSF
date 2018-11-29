package br.com.wcorrea.util.jpa;

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

    public T salvar(@Valid T entity){
        entity = entityManager.merge(entity);
        entityManager.flush();
        return entity;
    }

    public void remover(I id) {
        T entity = encontrar(id);
//        EntityTransaction tx = entityManager.getTransaction();
//        tx.begin();
        T mergedEntity = entityManager.merge(entity);
        entityManager.remove(mergedEntity);
        entityManager.flush();
//        tx.commit();
    }

    public List<T> getList() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(persistedClass);
        query.from(persistedClass);
        return entityManager.createQuery(query).getResultList();
    }

    public T encontrar(I id) {
        return entityManager.find(persistedClass, id);
    }
}