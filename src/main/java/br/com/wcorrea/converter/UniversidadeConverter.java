package br.com.wcorrea.converter;

import br.com.wcorrea.modelo.Universidade;
import br.com.wcorrea.repository.UniversidadeRepository;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = Universidade.class)
public class UniversidadeConverter implements Converter<Object> {

    @Inject
    private UniversidadeRepository objDao;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (!value.isEmpty()) {
            Long id = new Long(value);
            return objDao.buscarPorID(id);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Universidade obj = (Universidade) value;
            return obj.getId() != null && obj.getId() == 0 ? null : String.valueOf(obj.getId());
        }
        return "";
    }
}
