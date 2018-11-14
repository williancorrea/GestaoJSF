package br.com.wcorrea.modelo.filtros;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class Filtro implements Serializable {

    private static final long serialVersionUID = -1305167199759520660L;

    @Getter
    @Setter
    private int primeiroRegistro;

    @Getter
    @Setter
    private int quantidadeRegistros;
}
