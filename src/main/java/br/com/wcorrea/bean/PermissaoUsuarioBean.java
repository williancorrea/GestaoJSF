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
    private Map<String, Boolean> map;
    private List<Usuario> usuarioList;

    /**
     * METODO EXEXUTADO LOGO APOS A RENDERIZACAO DA TELA
     */
    @PostConstruct
    public void inicio() {
        permissoesSistemaList = usuarioRepository.buscarTodasPermissaoSistema();
        usuarioList = usuarioRepository.listarTudo();
        usuario = null;
    }

    public void carregarPermissoesIniciaisTela() {
//        TODO: FAZER A TRATATIVA PARA COLOCAR OS INPUTS NA TELA DE PERMISSOES DE - ADMINISTRADOR E PERMISSSOES DE USUARIO

        if (usuario != null) {
            map = new HashMap<>();
            for (Permissoes permissao : Permissoes.values()) {
                boolean adicionado = false;
                for (PermissoesSistema permissoesSistema : usuario.getListaPermissoesSistema()) {
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
                //TODO: Verificar se vai manter as permissoes quando trocar de usuario e voltar para o mesmo
                usuarioRepository.salvar(usuario);
            }
        }
    }

    public boolean isUsuarioSelecionado() {
        return usuario != null;
    }
}
