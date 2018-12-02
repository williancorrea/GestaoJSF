package br.com.wcorrea.repository;

import br.com.wcorrea.modelo.ClasseDespesa;
import br.com.wcorrea.modelo.filtros.FiltroPadrao;
import br.com.wcorrea.util.jpa.GenericDao;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

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
        Criteria criteria = criarCriteriaParaFiltro(filtro);

        criteria.setFirstResult(filtro.getPrimeiroRegistro());
        criteria.setMaxResults(filtro.getQuantidadeRegistros());

        if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
            criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
        } else if (filtro.getPropriedadeOrdenacao() != null) {
            criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
        }

        return criteria.list();
    }

    /**
     * RECUPERA A QUANTIDADE DE REGISTRO
     *
     * @param filtro
     * @return
     */
    public int quantidadeRegistrosFiltrados(FiltroPadrao filtro) {
        Criteria criteria = criarCriteriaParaFiltro(filtro);
        criteria.setProjection(Projections.rowCount());
        return ((Number) criteria.uniqueResult()).intValue();
    }

    /**
     * MONTA OS CRITERIOS PARA A PESQUISA
     *
     * @param filtro
     * @return
     */
    private Criteria criarCriteriaParaFiltro(FiltroPadrao filtro) {
        Session session = entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(ClasseDespesa.class);

        if (StringUtils.isNotEmpty(filtro.getDescricao())) {
            criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
        }

        return criteria;
    }
}
