package br.com.wcorrea.modelo.Pessoa;

import br.com.wcorrea.modelo.Universidade;
import br.com.wcorrea.modelo.util.Comum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Table(name = "estudante")
public class Estudante extends Comum implements Serializable {
    private static final long serialVersionUID = 1L;

    //TODO: FAZER ID COMPOSTO
    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "universidade_id")
    private Universidade universidade;
}
