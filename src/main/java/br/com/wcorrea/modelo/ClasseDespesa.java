package br.com.wcorrea.modelo;


import br.com.wcorrea.modelo.util.Comum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@ToString
@EqualsAndHashCode
@Entity
@Table
public class ClasseDespesa extends Comum implements Serializable {
    private static final long serialVersionUID = 7180271019059228253L;

    @Size(max = 150)
    @Column(length = 150, nullable = false)
    @NotBlank
    @Getter
    @Setter
    private String descricao;

    @Getter
    @Setter
    private boolean inativo;

    public ClasseDespesa() {
    }
}
