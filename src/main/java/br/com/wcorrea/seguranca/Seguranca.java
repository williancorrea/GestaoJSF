package br.com.wcorrea.seguranca;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;

@Named
@RequestScoped
public class Seguranca {

    @Inject
    private ExternalContext externalContext;

    public String getNomeUsuario() {
        String nome = null;
        UsuarioSistema usuarioLogado = getUsuarioLogado();

        if (usuarioLogado != null) {
            nome = usuarioLogado.getUsuario().getNome();
        }
        return nome;
    }

    private UsuarioSistema getUsuarioLogado() {
        UsuarioSistema usuario = null;

        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken)
                FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();

        if (auth != null && auth.getPrincipal() != null) {
            usuario = (UsuarioSistema) auth.getPrincipal();
        }
        return usuario;
    }

    /**
     * VERIFICA SE O USUARIO LOGADO POSSUI A PERMISSAO PASSADA POR PARAMETRO
     *
     * @param permissao
     * @return
     */
    public boolean temPermissao(String permissao) {
        return externalContext.isUserInRole("ROLE_" + Permissoes.ADMINISTRADOR) || externalContext.isUserInRole("ROLE_" + permissao);
    }

    /**
     * VERIFICA SE TEM ALGUMAS DAS PERMISSOES PASSADAS POR PARAMETRO
     * @param lista
     * @return
     */
    public boolean temPermissoes(ArrayList<Permissoes> lista){
        for( Permissoes permissao : lista){
            if(externalContext.isUserInRole("ROLE_" + permissao)){
                return true;
            }
        }
        return externalContext.isUserInRole("ROLE_" + Permissoes.ADMINISTRADOR);
    }

    public boolean naoTemPermissao(String permissao){
        return !temPermissao(permissao);

    }
}
