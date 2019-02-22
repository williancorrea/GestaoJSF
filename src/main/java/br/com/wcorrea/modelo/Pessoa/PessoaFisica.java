package br.com.wcorrea.modelo.Pessoa;

import br.com.wcorrea.modelo.util.Comum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Table(name = "pessoa_fisica")
public class PessoaFisica extends Comum implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @CPF
    @Column(unique = true, nullable = false)
    private String cpf;

    @Size(max = 12)
    private String rg;
    private String orgaoRg;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_emissao_rg")
    private Date dataEmissaoRg;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_nascimento")
    private Date dataNascimento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30, name = "sexo")
    private PessoaSexo sexo;

    private String naturalidade;

    private String nacionalidade;

    @Column(name = "tipo_sangue")
    private String tipoSangue;

    @Column(name = "cnh_numero")
    private String cnhNumero;

    @Column(name = "cnh_categoria")
    private String cnhCategoria;

    @Temporal(TemporalType.DATE)
    @Column(name = "cnh_vencimento")
    private Date cnhVencimento;

    @Column(name = "titulo_eleitoral_numero")
    private String tituloEleitoralNumero;

    @Column(name = "titulo_eleitoral_zona")
    private Integer tituloEleitoralZona;

    @Column(name = "titulo_eleitoral_secao")
    private Integer tituloEleitoralSecao;

    @Column(name = "reservista_numero")
    private String reservistaNumero;

    @Column(name = "reservista_categoria")
    private Integer reservistaCategoria;

    @Column(name = "nome_mae")
    private String nomeMae;

    @Column(name = "nome_pai")
    private String nomePai;

//    @JoinColumn(name = "ID_ESTADO_CIVIL", referencedColumnName = "ID")
//    @ManyToOne(optional = false)
//    private EstadoCivil estadoCivil;

    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    @OneToOne(optional = false)
    private Pessoa pessoa;

    public PessoaFisica() {
    }
}
