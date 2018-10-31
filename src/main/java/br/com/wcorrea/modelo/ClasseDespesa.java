package br.com.wcorrea.modelo;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@EqualsAndHashCode
public class ClasseDespesa implements Serializable {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String descricao;

    @Getter
    @Setter
    private Boolean inativo;

    public ClasseDespesa() {
    }
}
