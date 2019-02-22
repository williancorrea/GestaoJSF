package br.com.wcorrea.modelo.util;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@ToString
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@MappedSuperclass
public abstract class Comum implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    //TODO: COLOCAR A CRIPTOGRAFIA
    @Transient
    private String key;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_ALTERACAO")
    private Date dataAlteracao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_CRIACAO")
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
