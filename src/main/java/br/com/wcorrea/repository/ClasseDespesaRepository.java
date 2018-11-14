package br.com.wcorrea.repository;

import br.com.wcorrea.modelo.ClasseDespesa;
import br.com.wcorrea.modelo.filtros.FiltroPadrao;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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


    /**
     * LISTAR AS AUDITORIAS DE ACORDO COM O PARAMETRO
     *
     * @param filtro
     * @return
     */
    public List<ClasseDespesa> listar(FiltroPadrao filtro) {
        try {
            String sql = criarSQL(filtro, false);
            TypedQuery<ClasseDespesa> consulta = entityManager.createQuery(sql, ClasseDespesa.class);

            consulta.setFirstResult(filtro.getPrimeiroRegistro());
            consulta.setMaxResults(filtro.getQuantidadeRegistros());

            return consulta.getResultList();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * RECUPERA A QUANTIDADE DE REGISTRO
     *
     * @param filtro
     * @return
     */
    public int quantidadeRegistrosFiltrados(FiltroPadrao filtro) {
        try {
            String sql = criarSQL(filtro, true);
            TypedQuery<Long> consulta = entityManager.createQuery(sql, Long.class);
            return consulta.getSingleResult().intValue();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * CRIA A SQL COM OS PARAMETROS DO FILTRO
     *
     * @param filtro
     * @return
     */
    private String criarSQL(FiltroPadrao filtro, boolean count) {
        String sql = "";
        if (count) {
            sql = "select count(a) from ClasseDespesa a where 1=1";
        } else {
            sql = "from ClasseDespesa a where 1=1 ";
        }

        /**
         * FILTROS
         */
        if (StringUtils.isNotBlank(filtro.getDescricao())) {
            sql += " and upper(a.descricao) like '%" + filtro.getDescricao().toUpperCase() + "%'";
        }
        return sql;
    }
}
