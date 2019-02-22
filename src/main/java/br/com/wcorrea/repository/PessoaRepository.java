package br.com.wcorrea.repository;

import br.com.wcorrea.modelo.Pessoa.Pessoa;
import br.com.wcorrea.modelo.autenticacao.PermissoesSistema;
import br.com.wcorrea.modelo.autenticacao.Usuario;
import br.com.wcorrea.util.jpa.GenericDao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public class PessoaRepository extends GenericDao<Pessoa, Long> implements Serializable {
    private static final long serialVersionUID = -7287545229105230071L;

    @Inject
    private EntityManager m;

    public PessoaRepository() {
        super(Pessoa.class);
    }


    public Pessoa buscarCPF(String cpf) {
        try {
            return this.m.createQuery("select p from Pessoa p where lower(p.pessoaFisica.cpf) = :cpf", Pessoa.class)
                    .setParameter("cpf", cpf.toLowerCase())
                    .getSingleResult();
        } catch (Exception e) {
        }
        return null;
    }
}
