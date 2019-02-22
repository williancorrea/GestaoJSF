package br.com.wcorrea.converter;

import br.com.wcorrea.modelo.Pessoa.Estudante;
import br.com.wcorrea.repository.EstudanteRepository;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = Estudante.class)
public class EstudanteConverter implements Converter<Object> {

    @Inject
    private EstudanteRepository objDao;

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
            Estudante obj = (Estudante) value;
            return obj.getId() != null && obj.getId() == 0 ? null : String.valueOf(obj.getId());
        }
        return "";
    }
}
