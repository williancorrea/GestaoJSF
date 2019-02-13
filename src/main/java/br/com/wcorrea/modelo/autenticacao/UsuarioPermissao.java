package br.com.wcorrea.modelo.autenticacao;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "usuario_permissao")
public class UsuarioPermissao implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @Fetch(FetchMode.JOIN)
    private Usuario fkUsuario;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_permissao_sistema", nullable = false)
    @Fetch(FetchMode.JOIN)
    private PermissoesSistema fkPermissoesSistema;

    public UsuarioPermissao() {
    }
}
