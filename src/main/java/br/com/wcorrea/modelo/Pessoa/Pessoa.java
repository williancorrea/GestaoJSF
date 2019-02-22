package br.com.wcorrea.modelo.Pessoa;

import br.com.wcorrea.modelo.util.Comum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@ToString
@Entity
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Pessoa extends Comum implements Serializable {
	private static final long serialVersionUID = 1L;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30, name = "pessoa_tipo")
    private PessoaTipo pessoaTipo;

    @NotBlank
    @Size(min = 5, max = 150)
    @Column(nullable = false, length = 150)
    private String nome;


    @Size(min = 5, max = 255)
    @Column(name = "site", length = 255)
    private String site;

    @Email
    @Size(max = 255)
    @Column(length = 255)
    private String email;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "pessoa", cascade = CascadeType.ALL)
    private PessoaFisica pessoaFisica;

//    private boolean cliente;
//    private boolean fornecedor;
//    private boolean estudante;
//    private boolean transportadora;


    public Pessoa() {
    }
}
