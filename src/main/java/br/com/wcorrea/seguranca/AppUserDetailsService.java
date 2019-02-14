package br.com.wcorrea.seguranca;

import br.com.wcorrea.modelo.autenticacao.PermissoesSistema;
import br.com.wcorrea.modelo.autenticacao.Usuario;
import br.com.wcorrea.repository.UsuarioRepository;
import br.com.wcorrea.util.cdi.CDIServiceLocator;
import br.com.wcorrea.util.jsf.FacesUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AppUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioRepository usuarioRepository = CDIServiceLocator.getBean(UsuarioRepository.class);
        Usuario usuario = usuarioRepository.porEmail(email);

        UsuarioSistema user = null;
        if (usuario != null) {
            user = new UsuarioSistema(usuario, getGrupos(usuario));
        } else {
            throw new UsernameNotFoundException(FacesUtils.mensagemInternacionalizada("negocio_usuario_nao_encontrado"));
        }
        return user;
    }

    private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (PermissoesSistema permissoesSistema : usuario.getListaPermissoesSistema()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + permissoesSistema.getNome().toUpperCase()));
        }
        return authorities;
    }

}
