package br.com.wcorrea.util;

import javax.faces.context.FacesContext;

public class WCorreaUtils {

    /**
     * RECUPERA O CONTEXTO DA APLICACAO
     * EX: http://www.teste.com.br
     *
     * @return
     */
    public static String getContextPath() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
    }
}
