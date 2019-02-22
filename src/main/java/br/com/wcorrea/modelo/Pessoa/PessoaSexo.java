package br.com.wcorrea.modelo.Pessoa;

public enum PessoaSexo {
    MAXCULINO("Masculino"), FEMININO("Feminino");

    private String descricao;

    PessoaSexo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
