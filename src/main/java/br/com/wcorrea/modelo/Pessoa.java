package br.com.wcorrea.modelo;

import br.com.wcorrea.modelo.util.Comum;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@ToString
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pessoa extends Comum implements Serializable {
    private static final long serialVersionUID = -8977509125836117620L;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private PessoaTipo pessoaTipo;

    @NotBlank
    @Size(min = 5, max = 150)
    @Column(nullable = false, length = 150)
    private String nome;

    //TODO: ADICIONAR UMA LISTA DE EMAIL

    @Size(max = 250)
    @Column(length = 250)
    private String site;

    private boolean cliente;
    private boolean fornecedor;
    private boolean estudante;
//    private boolean transportadora;


    public Pessoa() {
    }
}
