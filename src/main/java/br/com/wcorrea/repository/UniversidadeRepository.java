package br.com.wcorrea.repository;

import br.com.wcorrea.modelo.Universidade;
import br.com.wcorrea.modelo.filtros.FiltroGlobal;
import br.com.wcorrea.util.jpa.GenericDao;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.List;

public class UniversidadeRepository extends GenericDao<Universidade, Long> implements Serializable {
    private static final long serialVersionUID = -4669657125949454834L;

    public UniversidadeRepository() {
        super(Universidade.class);
    }

    public List<Universidade> listar(FiltroGlobal filtro) {
        Criteria criteria = criarCriteriaParaFiltro(filtro);
        criteria = gerarCriteriosFiltros(filtro, criteria);
        return criteria.list();
    }

    public int quantidadeRegistrosFiltrados(FiltroGlobal filtro) {
        Criteria criteria = criarCriteriaParaFiltro(filtro);
        criteria.setProjection(Projections.rowCount());
        return ((Number) criteria.uniqueResult()).intValue();
    }

    private Criteria criarCriteriaParaFiltro(FiltroGlobal filtro) {
        Session session = entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Universidade.class);

        if (StringUtils.isNotEmpty(filtro.getPesquisaGlobal())) {
            criteria.add(Restrictions.ilike("nome", filtro.getPesquisaGlobal(), MatchMode.ANYWHERE));
        }

        return criteria;
    }
}
