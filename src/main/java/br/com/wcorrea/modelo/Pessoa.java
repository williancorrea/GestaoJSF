package br.com.wcorrea.modelo;

import br.com.wcorrea.modelo.util.Comum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@ToString
@EqualsAndHashCode
@Entity
public class Pessoa extends Comum implements Serializable {
    private static final long serialVersionUID = -8977509125836117620L;

    @Getter
    @Setter
    @NotBlank
    @Enumerated(EnumType.STRING)
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private PessoaTipo pessoaTipo;

}
