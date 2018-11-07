package br.com.wcorrea.bean;

import br.com.wcorrea.modelo.ClasseDespesa;
import br.com.wcorrea.util.FacesUtils;
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

    @PostConstruct
    public void inicio() {
        classeDespesa.setDescricao("Willian Vagner Vicente Correa");
        FacesUtils.addMessageinfo("Titulo", "Mensagem", false);
    }

}
