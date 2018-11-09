package br.com.wcorrea.bean;

import br.com.wcorrea.modelo.ClasseDespesa;
import br.com.wcorrea.repository.ClasseDespesaRepository;
import br.com.wcorrea.util.FacesUtils;
import br.com.wcorrea.util.jpa.Transacional;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class ClasseDespesaBean implements Serializable {

    @Getter
    @Setter
    @Inject
    private ClasseDespesa classeDespesa;


    @Getter
    @Setter
    @Inject
    private ClasseDespesaRepository classeDespesaRepository;

    @PostConstruct
    public void inicio() {
        classeDespesa.setDescricao("Willian Vagner Vicente Correa");
        FacesUtils.addMessageinfo("Titulo", "Mensagem", false);
    }

    @Transacional
    public void salvarTeste() {
        classeDespesaRepository.salvar(classeDespesa);
//        classeDespesaRepository.buscar(1L);
    }

}
