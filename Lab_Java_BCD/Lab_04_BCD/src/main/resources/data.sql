-- data.sql - Insere dados INICIAIS

-- 1. Alunos
INSERT INTO aluno (nome, matricula_ativa, data_inicio) VALUES
                                                           ('Mariana Luz', true, '2025-01-01'),
                                                           ('João Silva', false, '2024-08-15');

-- 2. Disciplinas
INSERT INTO disciplina (nome, creditos) VALUES
                                            ('Programação Orientada a Objetos', 4),
                                            ('Banco de Dados', 4);

-- 3. Professores
INSERT INTO professor (nome, area) VALUES
                                       ('Dr. Pedro Alvares', 'Programação'),
                                       ('Dra. Maria Santos', 'Banco de Dados');

-- 4. Matrículas (SEM IDs fixos - use LAST_INSERT_ID() ou SELECT)
INSERT INTO matricula (aluno_id, disciplina_id, nota, data_matricula)
SELECT a.matricula, d.id, 9.8, '2024-03-01'
FROM aluno a, disciplina d
WHERE a.nome = 'Mariana Luz' AND d.nome = 'Programação Orientada a Objetos';

INSERT INTO matricula (aluno_id, disciplina_id, nota, data_matricula)
SELECT a.matricula, d.id, 8.5, '2024-03-01'
FROM aluno a, disciplina d
WHERE a.nome = 'Mariana Luz' AND d.nome = 'Banco de Dados';