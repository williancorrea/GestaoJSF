package br.com.wcorrea.util.jsf;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesUtils {

    public FacesUtils() {
    }

    public static void addMessageinfo(String mensagem, boolean manterMensagem) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem));
        manterMensagens(manterMensagem);
    }

    public static void addMessageWarn(String mensagem, boolean manterMensagem) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, mensagem));
        manterMensagens(manterMensagem);
    }

    public static void addMessageErro(String mensagem, boolean manterMensagem) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, mensagem));
        manterMensagens(manterMensagem);
    }

    public static void addMessageFatal(String mensagem, boolean manterMensagem) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, mensagem, mensagem));
        manterMensagens(manterMensagem);
    }

    private static void manterMensagens(boolean manterMensagem) {
        if (manterMensagem) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

}
