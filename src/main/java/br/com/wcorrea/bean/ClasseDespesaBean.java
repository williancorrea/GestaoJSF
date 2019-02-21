package br.com.wcorrea.bean;

import br.com.wcorrea.modelo.ClasseDespesa;
import br.com.wcorrea.modelo.filtros.FiltroGlobal;
import br.com.wcorrea.repository.ClasseDespesaRepository;
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
public class ClasseDespesaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter
    @Setter
    @Inject
    private ClasseDespesa classeDespesa;

    @Getter
    @Setter
    @Inject
    private FiltroGlobal filtro;

    @Inject
    private ClasseDespesaRepository classeDespesaRepository;

    @Getter
    @Setter
    private LazyDataModel<ClasseDespesa> model;

    /**
     * METODO EXEXUTADO LOGO APOS A RENDERIZACAO DA TELA
     */
    @PostConstruct
    public void inicio() {
    }

    public void inicializarCadastro() {
        if (classeDespesa == null) {
            classeDespesa = new ClasseDespesa();
        }
    }

    public void carregamentoInicial() {
        if (FacesUtils.isNotPostback()) {
            System.out.println("nao é um postback");
        } else {
            System.out.println("ë um postback");
        }
    }

    public void listarClasseDespesasCadastradas() {
        if (FacesUtils.isNotPostback()) {
            /**
             * FAZ O CARREGAMENTO LAZYLOADING DE TODAS AS AUDITORIAS
             * CADASTRADAS
             */
            model = new LazyDataModel<ClasseDespesa>() {
                private static final long serialVersionUID = 1L;

                @Override
                public List<ClasseDespesa> load(int primeiroRegistro, int quantidadeRegistros, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                    //Ordenacao
                    filtro.setPropriedadeOrdenacao("descricao");
                    filtro.setAscendente(true);

                    //Paginacao
                    filtro.setPrimeiroRegistro(primeiroRegistro);
                    filtro.setQuantidadeRegistros(quantidadeRegistros);

                    // Quantidade Maxima de Registros por pagina
                    setRowCount(classeDespesaRepository.quantidadeRegistrosFiltrados(filtro));
                    return classeDespesaRepository.listar(filtro);
                }
            };
        }
    }

    /**
     * CRIAR UM NOVO ITEM
     */
    public void novo() {
        classeDespesa = new ClasseDespesa();
    }

    public void limparFiltros() {
        if (filtro.getPesquisaGlobal().isEmpty()) {
            return;
        }
        filtro.setPesquisaGlobal("");
        listarClasseDespesasCadastradas();
    }

    /**
     * SALVAR OBJETO NO BANCO DO DADOS
     */
    @Transacional
    public void salvar() {
        boolean editando = classeDespesa.isEditando();
        classeDespesa = classeDespesaRepository.salvar(classeDespesa);

        if (editando) {
            FacesUtils.addMessageinfo(classeDespesa.getDescricao() + " " + FacesUtils.mensagemInternacionalizada("informativo_atualizado_sucesso"), false);
        } else {
            FacesUtils.addMessageinfo(classeDespesa.getDescricao() + " " + FacesUtils.mensagemInternacionalizada("informativo_cadastrada_sucesso"), false);
        }
        novo();
    }

    /**
     * EXCLUIR ITEM DO BANCO DE DADOS
     *
     * @param obj
     */
    @Transacional
    public void excluir(ClasseDespesa obj) {
        classeDespesaRepository.remover(obj.getId());
        FacesUtils.addMessageinfo(obj.getDescricao() + " " + FacesUtils.mensagemInternacionalizada("informativo_exlusao_sucesso"), false);
        listarClasseDespesasCadastradas();
    }

}
