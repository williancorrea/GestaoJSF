package br.com.wcorrea.repository;

import br.com.wcorrea.modelo.ClasseDespesa;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public class ClasseDespesaRepository implements Serializable {

    private static final long serialVersionUID = 2169956831708235539L;

    @Inject
    private EntityManager entityManager;

    public ClasseDespesa salvar(ClasseDespesa obj) {
        return entityManager.merge(obj);
    }

    public void remover(ClasseDespesa obj) {
        entityManager.remove(entityManager.getReference(ClasseDespesa.class, obj.getId()));
    }

    public ClasseDespesa buscar(Long id) {
        return entityManager.find(ClasseDespesa.class, id);
    }

    public List<ClasseDespesa> todos() {
        return entityManager.createQuery("from ClasseDespesa", ClasseDespesa.class).getResultList();
    }

    public List<ClasseDespesa> pesquisar(String consulta) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ClasseDespesa> criteriaQuery = criteriaBuilder.createQuery(ClasseDespesa.class);
        Root<ClasseDespesa> root = criteriaQuery.from(ClasseDespesa.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.like(root.get("descricao"), "%" + consulta + "%"));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
