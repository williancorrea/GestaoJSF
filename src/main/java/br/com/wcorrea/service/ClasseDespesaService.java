package br.com.wcorrea.service;

import br.com.wcorrea.modelo.ClasseDespesa;
import br.com.wcorrea.repository.ClasseDespesaRepository;
import br.com.wcorrea.util.jpa.Transacional;

import javax.inject.Inject;
import java.io.Serializable;

public class ClasseDespesaService implements Serializable {
    private static final long serialVersionUID = 2844537127484993031L;

    public ClasseDespesaService() {
    }

    @Inject
    private ClasseDespesaRepository classeDespesaRepository;

    @Transacional
    public ClasseDespesa salvar(ClasseDespesa classeDespesa) {
        return classeDespesaRepository.salvar(classeDespesa);
    }
}
