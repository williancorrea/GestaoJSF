package br.com.wcorrea.modelo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@ToString
@EqualsAndHashCode
@MappedSuperclass
public abstract class Comum implements Serializable {

    private static final long serialVersionUID = 5281788002420675899L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Transient
    private String key;

//    private Date dataAlteracao;
//    private Date dataCriacao;
}
