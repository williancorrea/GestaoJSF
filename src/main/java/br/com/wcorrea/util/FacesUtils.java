package br.com.wcorrea.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesUtils {

    public FacesUtils() {
    }

    public static void addMessageinfo(String titulo, String mensagem, boolean manterMensagem) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, mensagem));
        manterMensagens(manterMensagem);
    }

    public static void addMessageWarn(String titulo, String mensagem, boolean manterMensagem) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, mensagem));
        manterMensagens(manterMensagem);
    }

    public static void addMessageErro(String titulo, String mensagem, boolean manterMensagem) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, mensagem));
        manterMensagens(manterMensagem);
    }

    public static void addMessageFatal(String titulo, String mensagem, boolean manterMensagem) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, titulo, mensagem));
        manterMensagens(manterMensagem);
    }

    private static void manterMensagens(boolean manterMensagem) {
        if (manterMensagem) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

}
