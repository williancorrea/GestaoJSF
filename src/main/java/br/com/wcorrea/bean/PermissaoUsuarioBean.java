package br.com.wcorrea.bean;

import br.com.wcorrea.modelo.Universidade;
import br.com.wcorrea.modelo.autenticacao.PermissoesSistema;
import br.com.wcorrea.modelo.autenticacao.Usuario;
import br.com.wcorrea.modelo.filtros.FiltroGlobal;
import br.com.wcorrea.repository.UniversidadeRepository;
import br.com.wcorrea.repository.UsuarioRepository;
import br.com.wcorrea.seguranca.Permissoes;
import br.com.wcorrea.seguranca.Seguranca;
import br.com.wcorrea.seguranca.UsuarioSistema;
import br.com.wcorrea.util.jpa.Transacional;
import br.com.wcorrea.util.jsf.FacesUtils;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
@Data
public class PermissaoUsuarioBean implements Serializable {

    @Inject
    private Usuario usuario;

    @Inject
    private UsuarioRepository usuarioRepository;

//    private Seguranca seguranca;

    private boolean CLASSE_DESPESA_SALVAR;
    private boolean CLASSE_DESPESA_EXCLUIR;
    private boolean CLASSE_DESPESA_PESQUISAR;

    private boolean UNIVERSIDADE_SALVAR;
    private boolean UNIVERSIDADE_EXCLUIR;
    private boolean UNIVERSIDADE_PESQUISAR;

    /**
     * METODO EXEXUTADO LOGO APOS A RENDERIZACAO DA TELA
     */
    @PostConstruct
    public void inicio() {
        //TODO: PEGAR A PERMISSAO DO USUARIO QUE FOR ESCOLHIDO NA TELA
//        usuario = seguranca..getUsuario();
    }

    public void inicializarCadastro() {

    }

    public void carregamentoInicial() {
        if (FacesUtils.isNotPostback()) {
            System.out.println("nao é um postback");
        } else {
            System.out.println("ë um postback");
        }
    }

    public void alterarPermissaoUsuario(Permissoes permissao, boolean valor){

//        Verifica se a permissao foi adicionada
        for(PermissoesSistema permissaoSistema : usuario.getListaPermissoesSistema()){
            if(permissaoSistema.getNome().equalsIgnoreCase(permissao.toString())){

                if(valor){
                    usuario.getListaPermissoesSistema().add(permissaoSistema);
                    usuarioRepository.salvar(usuario);

                }else{
                    usuario.getListaPermissoesSistema().remove(permissaoSistema);
                    usuarioRepository.salvar(usuario);
                }

                break;
            }
        }
    }

    public void handleChange(ValueChangeEvent event){
        System.out.println("New value: " + event.getNewValue());
    }


}
