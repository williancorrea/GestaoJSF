package br.com.wcorrea.bean;

import br.com.wcorrea.util.jsf.FacesUtils;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private FacesContext facesContext;

    @Inject
    private HttpServletRequest request;

    @Inject
    private HttpServletResponse response;

    //TODO: Adicionar o lombok
    private String email;

    public void preRender() {
        if ("true".equals(request.getParameter("invalid"))) {
            FacesUtils.addMessageErro(FacesUtils.mensagemInternacionalizada("negocio_usuario_senha_invalido"), false);
        }
    }

    public void login() throws ServletException, IOException {
//        TODO: IMPLEMENTAR COOKIE NO LOGIN
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Login.xhtml");
        dispatcher.forward(request, response);

        facesContext.responseComplete();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
