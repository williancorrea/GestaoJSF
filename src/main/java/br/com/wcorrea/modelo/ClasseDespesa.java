package br.com.wcorrea.modelo;


import br.com.wcorrea.modelo.util.Comum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@ToString
@Entity
@Table
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClasseDespesa extends Comum implements Serializable {
    private static final long serialVersionUID = 7180271019059228253L;

    @Size(min = 3, max = 150)
    @Column(length = 150, nullable = false)
    @NotBlank
    @EqualsAndHashCode.Include
    private String descricao;

    private boolean inativo;

    public ClasseDespesa() {
    }
}
