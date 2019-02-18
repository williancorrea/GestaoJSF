package br.com.wcorrea.repository;

import br.com.wcorrea.modelo.autenticacao.PermissoesSistema;
import br.com.wcorrea.modelo.autenticacao.Usuario;
import br.com.wcorrea.util.jpa.GenericDao;
import br.com.wcorrea.util.jsf.exception.NegocioException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.io.Serializable;

public class UsuarioRepository extends GenericDao<Usuario, Long> implements Serializable {
    private static final long serialVersionUID = -7287545229105230071L;

    @Inject
    private EntityManager m;

    public UsuarioRepository() {
        super(Usuario.class);
    }

//        if (habilitado) {
//            return m.createQuery("SELECT M FROM Usuario M WHERE M.habilitado = :habilitado ORDER BY M.nome")
//                    .setParameter("habilitado", habilitado)
//                    .getResultList();
//        } else {
//            return m.createQuery("SELECT M FROM Usuario M ORDER BY M.nome")
//                    .getResultList();
//        }
//    }



//    @Transactional
//    public void excluir(Usuario obj) {
//        try {
//            obj = buscarID(obj.getId());
//            m.remove(obj);
//            m.flush();
//        } catch (PersistenceException e) {
//            throw new NegocioException("O Usuario " + obj.getLogin()
//                    + ", não pode ser excluída, pois está sendo utilizado como referência em outro cadastro!");
//        }
//    }

    public Usuario buscarLogin(String login) {
        //TODO: ANALISAR METODO - VERIFICAR REFATORACAO
        Usuario usuario = null;
        try {
            usuario = this.m.createQuery("select m from Usuario m where lower(m.email) = :login", Usuario.class)
                    .setParameter("login", login.toLowerCase())
                    .getSingleResult();
        } catch (Exception e) {
            //Nenhum resultado encontrado com o login informado
        }
        return usuario;
    }

    //
    public PermissoesSistema buscarPermissaoSistema(Long Permissoes_ID) {
        //TODO: ANALISAR METODO - VERIFICAR REFATORACAO
        PermissoesSistema permissoes = null;
        try {
            permissoes = this.m.createQuery("select m from PermissoesSistema m where m.id = :permissao", PermissoesSistema.class)
                    .setParameter("permissao", Permissoes_ID)
                    .getSingleResult();
        } catch (Exception e) {
            //Nenhum resultado encontrado com o login informado
        }
        return permissoes;
    }

    public Usuario porEmail(String login) {
        //TODO: ANALISAR METODO - VERIFICAR REFATORACAO
        Usuario usuario = null;
        try {
            usuario = this.m.createQuery("select m from Usuario m where lower(m.email) = :email", Usuario.class)
                    .setParameter("email", login.toLowerCase())
                    .getSingleResult();
        } catch (Exception e) {
            //Nenhum resultado encontrado com o login informado
        }
        return usuario;
    }

}
