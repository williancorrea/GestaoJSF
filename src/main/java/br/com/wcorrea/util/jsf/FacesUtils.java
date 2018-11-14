package br.com.wcorrea.util.jsf;

import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.File;

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


    /**
     * Adiciona o separador padrão de cada sistema operacional para os diretorios
     *
     * @return - Separador padrao do Sistema Operacional
     */
    public static String separadorCaminhoAplicacao() {
        return File.separator;
    }

    /**
     * Recupera a mensagem internacionalizada da aplicacao
     *
     * @param chave - Caminho dentro da aplicacao que quer chegar
     * @return
     */
    public static String mensagemInternacionalizada(final String chave) {
        final FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().getResourceBundle(context, "app").getString(chave);
    }

    /**
     * Fecha o dialog, executa o valor hide ao dialogo passado por parametro
     *
     * @param dialog
     */
    public static void fecharDialogXhtml(String dialog) {
        RequestContext.getCurrentInstance().execute("PF('" + dialog + "').hide();");
    }

    /**
     * Atualiza um determinado componente da tela xhtml
     *
     * @param componente
     */
    public static void atualizarComponenteXhtml(String componente) {
        RequestContext.getCurrentInstance().update(componente);
    }

    /**
     * Executa um determinado javascript Ex: funcao()
     *
     * @param nomeJavaScript
     */
    public static void executarJavaScript(String nomeJavaScript) {
        RequestContext.getCurrentInstance().execute(nomeJavaScript + ";");
    }

    /**
     * Quando reenvia os dados para ela mesma
     *
     * @return
     */
    public static boolean isPostback() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    /**
     * Quando NAO reenvia os dados para ela mesma
     *
     * @return
     */
    public static boolean isNotPostback() {
        return !isPostback();
    }
}
