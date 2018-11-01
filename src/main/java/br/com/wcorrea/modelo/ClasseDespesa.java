package br.com.wcorrea.modelo;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@ToString
@EqualsAndHashCode
@Entity
@Table(name = "classe_despesa")
public class ClasseDespesa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Column(length = 150)
    @Getter
    @Setter
    private String descricao;

    @Getter
    @Setter
    private Boolean inativo;

    public ClasseDespesa() {
    }
}
