package br.com.wcorrea.repository;

import br.com.wcorrea.modelo.ClasseDespesa;
import br.com.wcorrea.util.jpa.GenericDao;

import java.io.Serializable;

public class ClasseDespesaRepository2 extends GenericDao<ClasseDespesa, Long> implements Serializable {
    private static final long serialVersionUID = -4669657125949454834L;

    public ClasseDespesaRepository2() {
        super(ClasseDespesa.class);
    }
}
