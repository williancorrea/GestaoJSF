# USUARIOS
insert into usuario (nome, email, senha, data_criacao, data_alteracao) values ('Willian Vagner Vicente Corrêa','willian.vag@gmail.com', '123', now(), now());

# PERMISSOES
insert into permissoes_sistema (nome, descricao, data_criacao, data_alteracao) values ('DESENVOLVEDOR', 'Desenvolvedor do Sistema', now(), now());
insert into permissoes_sistema (nome, descricao, data_criacao, data_alteracao) values ('ADMINISTRADOR', 'Usuário administrador do sistema', now(), now());
insert into permissoes_sistema (nome, descricao, data_criacao, data_alteracao) values ('ALTERAR_PERMISSOES_USUARIO', 'Alterar Permissoes de Usuarios', now(), now());

insert into permissoes_sistema (nome, descricao, data_criacao, data_alteracao) values ('CLASSE_DESPESA_SALVAR', '', now(), now());
insert into permissoes_sistema (nome, descricao, data_criacao, data_alteracao) values ('CLASSE_DESPESA_EXCLUIR', '', now(), now());
insert into permissoes_sistema (nome, descricao, data_criacao, data_alteracao) values ('CLASSE_DESPESA_PESQUISAR', '', now(), now());

insert into permissoes_sistema (nome, descricao, data_criacao, data_alteracao) values ('UNIVERSIDADE_SALVAR', '', now(), now());
insert into permissoes_sistema (nome, descricao, data_criacao, data_alteracao) values ('UNIVERSIDADE_EXCLUIR', '', now(), now());
insert into permissoes_sistema (nome, descricao, data_criacao, data_alteracao) values ('UNIVERSIDADE_PESQUISAR', '', now(), now());

# USUARIOS E PERMISSOES
INSERT INTO usuario_permissao (id_permissao_sistema, id_usuario) VALUES ((select id from permissoes_sistema p where p.nome = 'ALTERAR_PERMISSOES_USUARIO'), (select id from usuario u where u.email='willian.vag@gmail.com'));
