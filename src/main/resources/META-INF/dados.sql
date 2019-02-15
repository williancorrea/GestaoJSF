# USUARIOS
insert into usuario (nome, email, senha, data_criacao, data_alteracao) values ('Willian Vagner Vicente Corrêa','willian.vag@gmail.com', '123', now(), now());

# PERMISSOES
insert into permissoes_sistema (nome, descricao, data_criacao, data_alteracao) values ('DESENVOLVEDOR', 'Desenvolvedor do Sistema', now(), now());
insert into permissoes_sistema (nome, descricao, data_criacao, data_alteracao) values ('ADMINISTRADOR', 'Usuário administrador do sistema', now(), now());

# USUARIOS E PERMISSOES
INSERT INTO usuario_permissao (id_permissao_sistema, id_usuario) VALUES ((select id from permissoes_sistema p where p.nome = 'DESENVOLVEDOR'), (select id from usuario u where u.email='willian.vag@gmail.com'));
INSERT INTO usuario_permissao (id_permissao_sistema, id_usuario) VALUES ((select id from permissoes_sistema p where p.nome = 'ADMINISTRADOR'), (select id from usuario u where u.email='willian.vag@gmail.com'));
