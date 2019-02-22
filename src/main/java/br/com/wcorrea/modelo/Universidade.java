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
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Table(name = "universidade")
public class Universidade extends Comum implements Serializable {
	private static final long serialVersionUID = 1L;

    @Size(min = 3, max = 150)
    @Column(length = 150, nullable = false)
    @NotBlank
    private String nome;

    private boolean inativo;

    public Universidade() {
    }
}
