package br.com.wcorrea.modelo.filtros;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class FiltroGlobal extends Filtro implements Serializable {
    private static final long serialVersionUID = -1782479360841397312L;

    private String pesquisaGlobal;

    public FiltroGlobal() {
    }

}
