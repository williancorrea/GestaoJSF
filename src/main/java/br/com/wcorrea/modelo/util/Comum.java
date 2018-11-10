package br.com.wcorrea.modelo.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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

    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_alteracao")
    private Date dataAlteracao;

    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_criacao")
    private Date dataCriacao;

    /**
     * VERIFICA SE O ID EXISTE
     *
     * @return
     */
    public boolean isEditando() {
        return this.id == null ? false : true;
    }

    @PostPersist
    public void postPersist() {
        this.dataAlteracao = new Date();
        this.dataCriacao = new Date();
    }

    @PostUpdate
    public void postUpdate() {
        this.dataAlteracao = new Date();
    }
}
