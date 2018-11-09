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
@Table
public class ClasseDespesa implements Serializable {

    //    public class ClasseDespesa extends Comum implements Serializable {
    private static final long serialVersionUID = 7180271019059228253L;

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
