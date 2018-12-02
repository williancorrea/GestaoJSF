package br.com.wcorrea.bean;

import br.com.wcorrea.modelo.ClasseDespesa;
import br.com.wcorrea.modelo.filtros.FiltroPadrao;
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

    @Getter
    @Setter
    @Inject
    private ClasseDespesa classeDespesa;

    @Getter
    @Setter
    @Inject
    private FiltroPadrao filtroPadrao;

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

                    filtroPadrao.setPrimeiroRegistro(primeiroRegistro);
                    filtroPadrao.setQuantidadeRegistros(quantidadeRegistros);
                    filtroPadrao.setPropriedadeOrdenacao(sortField);
                    filtroPadrao.setAscendente(SortOrder.ASCENDING.equals(sortOrder));

                    // Quantidade Maxima de Registros
                    setRowCount(classeDespesaRepository.quantidadeRegistrosFiltrados(filtroPadrao));
                    return classeDespesaRepository.listar(filtroPadrao);
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

    /**
     * SALVAR OBJETO NO BANCO DO DADOS
     */
    @Transacional
    public void salvar() {
        boolean editando = classeDespesa.isEditando();
        classeDespesa = classeDespesaRepository.salvar(classeDespesa);

        //TODO: COLOCAR TELA DE LOADING NO MEIO DA TELA

        if (editando) {
            FacesUtils.addMessageinfo("Classe de Despesa (" + classeDespesa.getDescricao() + ") atualizada com sucesso!", false);
            return;
        }
        FacesUtils.addMessageinfo("Classe de Despesa (" + classeDespesa.getDescricao() + ") cadastrada com sucesso!", false);
        novo();
    }

    @Transacional
    public void excluir() {
        //TODO: Traduzir mensagens
        classeDespesaRepository.remover(classeDespesa.getId());
        FacesUtils.addMessageinfo("Classe de Despesa (" + classeDespesa.getDescricao() + ") excluida com sucesso!", false);
    }

}
