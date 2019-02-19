package br.com.wcorrea.modelo.autenticacao;

import br.com.wcorrea.modelo.util.Comum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Usuario extends Comum implements Serializable {

    @NotBlank
    @Size(min = 3, max = 120)
    @Column(nullable = false, length = 120)
    private String nome;

    @NotBlank
    @Size(min = 3, max = 255)
    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @NotBlank
    @Size(min = 4, max = 20)
    @Column(nullable = false, length = 20)
    private String senha;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_permissao",
            joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_permissao_sistema"))
    private List<PermissoesSistema> listaPermissoesSistema = new ArrayList<>();

}
