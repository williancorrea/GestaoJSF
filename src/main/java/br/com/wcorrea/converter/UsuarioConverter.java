package br.com.wcorrea.converter;

import br.com.wcorrea.modelo.autenticacao.Usuario;
import br.com.wcorrea.repository.UsuarioRepository;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter {

    @Inject
    private UsuarioRepository objDao;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Usuario retorno = null;

        if (!value.isEmpty()) {
            Long id = new Long(value);
            retorno = objDao.buscarPorID(id);
        }
        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Usuario obj = (Usuario) value;
            return obj.getId() != null && obj.getId() == 0 ? null : String.valueOf(obj.getId());
        }
        return "";
    }
}
