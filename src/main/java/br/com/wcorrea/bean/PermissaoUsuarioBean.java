package br.com.wcorrea.bean;

import br.com.wcorrea.modelo.autenticacao.PermissoesSistema;
import br.com.wcorrea.modelo.autenticacao.Usuario;
import br.com.wcorrea.repository.UsuarioRepository;
import br.com.wcorrea.seguranca.Permissoes;
import br.com.wcorrea.util.jpa.Transacional;
import lombok.Data;

import javax.annotation.PostConstruct;
import javax.faces.component.UIInput;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
@Data
public class PermissaoUsuarioBean implements Serializable {

    @Inject
    private Usuario usuario;

    @Inject
    protected UsuarioRepository usuarioRepository;

    protected List<PermissoesSistema> permissoesSistemaList;


//    private Seguranca seguranca;

    private boolean CLASSE_DESPESA_PESQUISAR;
    private boolean CLASSE_DESPESA_SALVAR;
    private boolean CLASSE_DESPESA_EXCLUIR;

    private boolean UNIVERSIDADE_PESQUISAR;
    private boolean UNIVERSIDADE_SALVAR;
    private boolean UNIVERSIDADE_EXCLUIR;

    private Map<String, Boolean> map;

    /**
     * METODO EXEXUTADO LOGO APOS A RENDERIZACAO DA TELA
     */
    @PostConstruct
    public void inicio() {
        permissoesSistemaList = usuarioRepository.buscarTodasPermissaoSistema();
        usuario = usuarioRepository.buscarLogin("willian.vag@gmail.com");
        System.out.println("EXECUTOU O METODO INICIAL");
        carregarPermissoesIniciaisTela(usuario);
    }

    public void inicializarCadastro() {
    }

    public void carregarPermissoesIniciaisTela(Usuario user) {
//        TODO: FAZER A TRATATIVA PARA COLOCAR OS INPUTS NA TELA DE PERMISSOES DE - ADMINISTRADOR E PERMISSSOES DE USUARIO

        map = new HashMap<>();
        for (Permissoes permissao : Permissoes.values()) {
            boolean adicionado = false;
            for (PermissoesSistema permissoesSistema : user.getListaPermissoesSistema()) {
                if (permissoesSistema.getNome().equalsIgnoreCase(permissao.toString())) {
                    map.put(permissao.toString(), true);
                    adicionado = true;
                    break;
                }
            }
            if (adicionado == false) {
                map.put(permissao.toString(), false);
            }
        }
    }

    @Transacional
    public void alterarPermissaoUsuario(ValueChangeEvent event) {
        String permissao = ((UIInput) event.getSource()).getAttributes().get("permissao").toString();
        for (PermissoesSistema permissoesSistema : permissoesSistemaList) {
            if (permissoesSistema.getNome().equalsIgnoreCase(permissao)) {
                if (((Boolean) event.getNewValue()) == true) {
                    usuario.getListaPermissoesSistema().add(permissoesSistema);
                } else {
                    usuario.getListaPermissoesSistema().remove(permissoesSistema);
                }
                map.replace(permissao, ((Boolean) event.getNewValue()));
                usuarioRepository.salvar(usuario);
            }
        }
    }

}
