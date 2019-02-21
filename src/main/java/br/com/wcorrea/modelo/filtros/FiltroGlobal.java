package br.com.wcorrea.modelo.filtros;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true, onlyExplicitlyIncluded= true)
public class FiltroGlobal extends Filtro implements Serializable {
    private static final long serialVersionUID = -1782479360841397312L;

    private String pesquisaGlobal;

    public FiltroGlobal() {
    }

}
