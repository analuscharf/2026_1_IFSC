-- schema.sql - Cria as tabelas MANUALMENTE
DROP TABLE IF EXISTS matricula;
DROP TABLE IF EXISTS disciplina_professor;
DROP TABLE IF EXISTS professor;
DROP TABLE IF EXISTS disciplina;
DROP TABLE IF EXISTS aluno;

-- Aluno
CREATE TABLE aluno (
                       matricula BIGINT PRIMARY KEY AUTO_INCREMENT,
                       nome VARCHAR(255) NOT NULL,
                       matricula_ativa BOOLEAN NOT NULL,
                       data_inicio DATE NOT NULL
);

-- Disciplina
CREATE TABLE disciplina (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            nome VARCHAR(255) NOT NULL,
                            creditos INT NOT NULL
);

-- Professor
CREATE TABLE professor (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           nome VARCHAR(255) NOT NULL,
                           area VARCHAR(255) NOT NULL
);

-- Tabela de junção Professor-Disciplina (N:M)
CREATE TABLE disciplina_professor (
                                      disciplina_id BIGINT NOT NULL,
                                      professor_id BIGINT NOT NULL,
                                      PRIMARY KEY (disciplina_id, professor_id),
                                      FOREIGN KEY (disciplina_id) REFERENCES disciplina(id),
                                      FOREIGN KEY (professor_id) REFERENCES professor(id)
);

-- Matrícula (chave composta)
CREATE TABLE matricula (
                           aluno_id BIGINT NOT NULL,
                           disciplina_id BIGINT NOT NULL,
                           nota DOUBLE,
                           data_matricula DATE NOT NULL,
                           PRIMARY KEY (aluno_id, disciplina_id),
                           FOREIGN KEY (aluno_id) REFERENCES aluno(matricula),
                           FOREIGN KEY (disciplina_id) REFERENCES disciplina(id)
);