package br.com.wcorrea.converter;

import br.com.wcorrea.modelo.ClasseDespesa;
import br.com.wcorrea.repository.ClasseDespesaRepository;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = ClasseDespesa.class)
public class ClasseDespesaConverte implements Converter {

    @Inject
    private ClasseDespesaRepository objDao;

//    public CombustivelConverter() {
//        objDao = CDIServiceLocator.getBean(CombustivelDao.class);
//    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        ClasseDespesa retorno = null;

        if (value != null) {
            Long id = new Long(value);
            retorno = objDao.buscarPorID(id);
        }
        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            ClasseDespesa obj = (ClasseDespesa) value;
            return obj.getId() == 0 ? null : String.valueOf(obj.getId());
        }
        return "";
    }

}
