package br.com.wcorrea.modelo.filtros;

import lombok.Data;

import java.io.Serializable;

@Data
public class Filtro implements Serializable {
	private static final long serialVersionUID = 1L;

	private int primeiroRegistro;
	private int quantidadeRegistros;
	private String propriedadeOrdenacao;
	private boolean ascendente;
}
