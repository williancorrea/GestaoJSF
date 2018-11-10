package br.com.wcorrea.bean;

import br.com.wcorrea.modelo.ClasseDespesa;
import br.com.wcorrea.service.ClasseDespesaService;
import br.com.wcorrea.util.jsf.FacesUtils;
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

    @Inject
    private ClasseDespesaService classeDespesaService;


    /**
     * METODO EXEXUTADO LOGO APOS A RENDERIZACAO DA TELA
     */
    @PostConstruct
    public void inicio() {
    }

    /**
     * CRIAR UM NOVO ITEM
     */
    public void novo() {
        classeDespesa = new ClasseDespesa();
    }

    /**
     * SALVAR OBJETO NO BANCO DO DADOS
     */
    public void salvar() {
        boolean editando = classeDespesa.isEditando();
        classeDespesa = classeDespesaService.salvar(classeDespesa);

        novo();
        if (editando) {
            FacesUtils.addMessageinfo("Classe de Despesa (" + classeDespesa.getDescricao() + ") atualizada com sucesso!", false);
            return;
        }
        FacesUtils.addMessageinfo("Classe de Despesa (" + classeDespesa.getDescricao() + ") cadastrada com sucesso!", false);
    }


}
