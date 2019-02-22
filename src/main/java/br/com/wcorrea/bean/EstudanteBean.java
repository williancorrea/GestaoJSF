package br.com.wcorrea.bean;

import br.com.wcorrea.modelo.Pessoa.*;
import br.com.wcorrea.modelo.Universidade;
import br.com.wcorrea.modelo.filtros.FiltroGlobal;
import br.com.wcorrea.repository.EstudanteRepository;
import br.com.wcorrea.repository.PessoaRepository;
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
public class EstudanteBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Inject
    private Estudante estudante;

    @Getter
    @Setter
    @Inject
    private FiltroGlobal filtro;

    @Inject
    private EstudanteRepository estudanteRepository;

    @Getter
    @Setter
    private LazyDataModel<Estudante> model;

    @Getter
    private List<Universidade> universidadeList;

    @Inject
    private UniversidadeRepository universidadeRepository;

    @Inject
    private PessoaRepository pessoaRepository;


    /**
     * METODO EXEXUTADO LOGO APOS A RENDERIZACAO DA TELA
     */
    @PostConstruct
    public void inicio() {
    }

    public void inicializarCadastro() {
        if (estudante == null) {
            novo();
        }
        universidadeList = universidadeRepository.listarTudo();
    }

    public void listarEstudantesCadastradas() {
        if (FacesUtils.isNotPostback()) {
            /**
             * FAZ O CARREGAMENTO LAZYLOADING DE TODAS AS AUDITORIAS
             * CADASTRADAS
             */
            model = new LazyDataModel<Estudante>() {
                private static final long serialVersionUID = 1L;

                @Override
                public List<Estudante> load(int primeiroRegistro, int quantidadeRegistros, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                    //Ordenacao
                    filtro.setPropriedadeOrdenacao("descricao");
                    filtro.setAscendente(true);

                    //Paginacao
                    filtro.setPrimeiroRegistro(primeiroRegistro);
                    filtro.setQuantidadeRegistros(quantidadeRegistros);

                    // Quantidade Maxima de Registros por pagina
                    setRowCount(estudanteRepository.quantidadeRegistrosFiltrados(filtro));
                    return estudanteRepository.listar(filtro);
                }
            };
        }
    }

    /**
     * CRIAR UM NOVO ITEM
     */
    public void novo() {
        estudante = new Estudante();
        estudante.setPessoa(new Pessoa());
        estudante.getPessoa().setPessoaTipo(PessoaTipo.FISICA);
        estudante.getPessoa().setPessoaFisica(new PessoaFisica());
    }

    public void limparFiltros() {
        if (filtro.getPesquisaGlobal().isEmpty()) {
            return;
        }
        filtro.setPesquisaGlobal("");
        listarEstudantesCadastradas();
    }

    /**
     * SALVAR OBJETO NO BANCO DO DADOS
     */
    @Transacional
    public void salvar() {
        boolean editando = estudante.isEditando();
        estudante.getPessoa().getPessoaFisica().setPessoa(estudante.getPessoa());

        if(!estudante.isEditando()){
            Pessoa pessoa = pessoaRepository.buscarCPF(estudante.getPessoa().getPessoaFisica().getCpf());
            if(pessoa != null || pessoa.getId() == estudante.getPessoa().getId()){
                if(pessoa.getPessoaFisica().getCpf().equalsIgnoreCase(estudante.getPessoa().getPessoaFisica().getCpf())){
                    FacesUtils.addMessageErro(estudante.getPessoa().getPessoaFisica().getCpf() + " - " + FacesUtils.mensagemInternacionalizada("erro_cpf_esta_sendo_utilizado"), false);
                    return;
                }
            }
        }

        estudante = estudanteRepository.salvar(estudante);

        if (editando) {
            FacesUtils.addMessageinfo(estudante.getPessoa().getNome() + " " + FacesUtils.mensagemInternacionalizada("informativo_atualizado_sucesso"), false);
        } else {
            FacesUtils.addMessageinfo(estudante.getPessoa().getNome() + " " + FacesUtils.mensagemInternacionalizada("informativo_cadastrada_sucesso"), false);
        }
        novo();
    }

    /**
     * EXCLUIR ITEM DO BANCO DE DADOS
     *
     * @param obj
     */
    @Transacional
    public void excluir(Estudante obj) {
        estudanteRepository.remover(obj.getId());
        FacesUtils.addMessageinfo(obj.getPessoa().getNome() + " " + FacesUtils.mensagemInternacionalizada("informativo_exlusao_sucesso"), false);
        listarEstudantesCadastradas();
    }

    public PessoaSexo[] getPessoaSexo(){
        return PessoaSexo.values();
    }
}
