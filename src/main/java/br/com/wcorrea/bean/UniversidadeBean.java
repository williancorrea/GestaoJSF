package br.com.wcorrea.bean;

import br.com.wcorrea.modelo.Universidade;
import br.com.wcorrea.modelo.filtros.FiltroGlobal;
import br.com.wcorrea.repository.UniversidadeRepository;
import br.com.wcorrea.util.jpa.Transacional;
import br.com.wcorrea.util.jsf.FacesUtils;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class UniversidadeBean implements Serializable {

    @Getter
    @Setter
    @Inject
    private Universidade universidade;

    @Getter
    @Setter
    @Inject
    private FiltroGlobal filtro;

    @Inject
    private UniversidadeRepository universidadeRepository;

    @Getter
    @Setter
    private LazyDataModel<Universidade> model;

    /**
     * METODO EXEXUTADO LOGO APOS A RENDERIZACAO DA TELA
     */
    @PostConstruct
    public void inicio() {
    }

    public void inicializarCadastro() {
        if (universidade == null) {
            universidade = new Universidade();
        }
    }

    public void carregamentoInicial() {
        if (FacesUtils.isNotPostback()) {
            System.out.println("nao é um postback");
        } else {
            System.out.println("ë um postback");
        }
    }

    public void listarUniversidadesCadastradas() {
        if (FacesUtils.isNotPostback()) {
            /**
             * FAZ O CARREGAMENTO LAZYLOADING DE TODAS AS AUDITORIAS CADASTRADAS
             */
            model = new LazyDataModel<Universidade>() {
                private static final long serialVersionUID = 1L;

                @Override
                public List<Universidade> load(int primeiroRegistro, int quantidadeRegistros, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                    //Ordenacao
                    filtro.setPropriedadeOrdenacao("nome");
                    filtro.setAscendente(true);

                    //Paginacao
                    filtro.setPrimeiroRegistro(primeiroRegistro);
                    filtro.setQuantidadeRegistros(quantidadeRegistros);

                    // Quantidade Maxima de Registros por pagina
                    setRowCount(universidadeRepository.quantidadeRegistrosFiltrados(filtro));
                    return universidadeRepository.listar(filtro);
                }
            };
        }
    }

    /**
     * CRIAR UM NOVO ITEM
     */
    public void novo() {
        universidade = new Universidade();
    }

    public void limparFiltros() {
        if (filtro.getPesquisaGlobal().isEmpty()) {
            return;
        }
        filtro.setPesquisaGlobal("");
        listarUniversidadesCadastradas();
    }

    /**
     * SALVAR OBJETO NO BANCO DO DADOS
     */
    @Transacional
    public void salvar() {
        boolean editando = universidade.isEditando();
        universidade = universidadeRepository.salvar(universidade);

        if (editando) {
            FacesUtils.addMessageinfo(universidade.getNome() + " " + FacesUtils.mensagemInternacionalizada("informativo_atualizado_sucesso"), false);
        } else {
            FacesUtils.addMessageinfo(universidade.getNome() + " " + FacesUtils.mensagemInternacionalizada("informativo_cadastrada_sucesso"), false);
        }
        novo();
    }

    /**
     * EXCLUIR ITEM DO BANCO DE DADOS
     *
     * @param obj
     */
    @Transacional
    public void excluir(Universidade obj) {
        universidadeRepository.remover(obj.getId());
        FacesUtils.addMessageinfo(obj.getNome() + " " + FacesUtils.mensagemInternacionalizada("informativo_exlusao_sucesso"), false);
        listarUniversidadesCadastradas();
    }

}
