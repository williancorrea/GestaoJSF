package br.com.wcorrea.modelo.filtros;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class FiltroPadrao extends Filtro implements Serializable {

    private static final long serialVersionUID = -1782479360841397312L;

    @Getter
    @Setter
    private String descricao;

    public FiltroPadrao() {
    }
}
