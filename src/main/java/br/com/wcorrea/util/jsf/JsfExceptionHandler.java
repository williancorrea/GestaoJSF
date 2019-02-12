package br.com.wcorrea.util.jsf;

import br.com.wcorrea.util.jsf.exception.NegocioException;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import java.io.IOException;
import java.util.Iterator;

/**
 * MANIPULA AS EXCEPTION DO JSF
 */
public class JsfExceptionHandler extends ExceptionHandlerWrapper {

    private ExceptionHandler wrapped;

    public JsfExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    @Override
    public void handle() throws FacesException {
        Iterator<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents().iterator();

        while (events.hasNext()) {
            ExceptionQueuedEvent event = events.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

            Throwable exception = context.getException();

            NegocioException negocioException = getNegocioException(exception);

            boolean handled = false;
            try {
                if (exception instanceof ViewExpiredException) {
                    handled = true;
                    redirect("/");
                } else if (negocioException != null) {
                    handled = true;
                    FacesUtils.addMessageErro(negocioException.getMessage(), true);
                } else {
                    handled = true;
//                    TODO: MOSTRAR A MENSAGEM DE ERRO QUANDO FOR REDIRECIONADO
//                    TODO: VERIFICA A POSSIBILIDADE DE ENVIAR EMAIL CONTENDO O TIPO DO ERRO - REPORTAR ERRO AO DESENVOLVEDOR
                    redirect("/error.xhtml");
                }
            } finally {
                if (handled) {
                    events.remove();
                }
            }
        }

        getWrapped().handle();
    }

    private NegocioException getNegocioException(Throwable exception) {
        if (exception instanceof NegocioException) {
            return (NegocioException) exception;
        } else if (exception.getCause() != null) {
            return getNegocioException(exception.getCause());
        }
        return null;
    }

    private void redirect(String page) {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            String contextPath = externalContext.getRequestContextPath();

            externalContext.redirect(contextPath + page);
            facesContext.responseComplete();
        } catch (IOException e) {
            throw new FacesException(e);
        }
    }
}
