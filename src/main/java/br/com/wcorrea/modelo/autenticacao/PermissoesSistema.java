package br.com.wcorrea.modelo.autenticacao;

import br.com.wcorrea.modelo.util.Comum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "permissoes_sistema")
public class PermissoesSistema extends Comum implements Serializable {
    private static final long serialVersionUID = 1L;

    @Size(min = 3, max = 60)
    @Column(nullable = false, length = 60)
    private String nome;


    @NotBlank
    @Size(min = 3, max = 120)
    @Column(nullable = false, length = 120)
    private String descricao;

    public PermissoesSistema() {
    }
}
