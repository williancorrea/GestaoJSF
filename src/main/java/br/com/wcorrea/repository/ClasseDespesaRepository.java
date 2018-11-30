package br.com.wcorrea.repository;

import br.com.wcorrea.modelo.ClasseDespesa;
import br.com.wcorrea.modelo.filtros.FiltroPadrao;
import br.com.wcorrea.util.jpa.GenericDao;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

public class ClasseDespesaRepository extends GenericDao<ClasseDespesa, Long> implements Serializable {
    private static final long serialVersionUID = -4669657125949454834L;

    public ClasseDespesaRepository() {
        super(ClasseDespesa.class);
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
