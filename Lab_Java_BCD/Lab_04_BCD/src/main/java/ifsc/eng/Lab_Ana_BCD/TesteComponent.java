package ifsc.eng.Lab_Ana_BCD;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class TesteComponent implements CommandLineRunner {

    @Autowired private AlunoRepository alunoRepository;
    @Autowired private DisciplinaRepository disciplinaRepository;
    @Autowired private ProfessorRepository professorRepository;
    @Autowired private MatriculaRepository matriculaRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Iniciando testes...");
    }

    @Transactional
    public void rodarTestes() {
        System.out.println("\n=== LABORATORIO JPA - ENTIDADES E RELACIONAMENTOS ===");

        // -----------------------------------------------------
        // PARTE 1: DADOS DO ARQUIVO SQL (schema.sql + data.sql)
        // -----------------------------------------------------
        System.out.println("\n--- PARTE 1: Dados carregados do arquivo SQL ---");

        System.out.println("Quantidade de registros encontrados:");
        System.out.println("Alunos: " + alunoRepository.count());
        System.out.println("Disciplinas: " + disciplinaRepository.count());
        System.out.println("Professores: " + professorRepository.count());
        System.out.println("Matriculas: " + matriculaRepository.count());

        // Mostra alunos do SQL
        System.out.println("\nAlunos inseridos via SQL:");
        for (Aluno aluno : alunoRepository.findAll()) {
            System.out.println("  Nome: " + aluno.getNome());
            System.out.println("  Matricula: " + aluno.getMatricula());
            System.out.println("  Ativo: " + aluno.getMatriculaAtiva());

            if (!aluno.getMatriculas().isEmpty()) {
                System.out.println("  Disciplinas matriculadas:");
                for (Matricula mat : aluno.getMatriculas()) {
                    System.out.println("    - " + mat.getDisciplina().getNome() +
                            " (Nota: " + mat.getNota() + ")");
                }
            }
            System.out.println();
        }

        // Mostra disciplinas do SQL
        System.out.println("\nDisciplinas inseridas via SQL:");
        for (Disciplina disc : disciplinaRepository.findAll()) {
            System.out.println("  " + disc.getNome() + " - " + disc.getCreditos() + " creditos");
        }

        // Mostra professores do SQL
        System.out.println("\nProfessores inseridos via SQL:");
        for (Professor prof : professorRepository.findAll()) {
            System.out.println("  " + prof.getNome() + " - " + prof.getArea());
        }

        // -----------------------------------------------------
        // PARTE 2: INSERIR NOVOS DADOS VIA OBJETOS JAVA
        // -----------------------------------------------------
        System.out.println("\n--- PARTE 2: Inserindo novos dados via Java ---");

        System.out.println("\nCriando novos registros...");

        try {
            // 1. Novo aluno
            System.out.println("1. Criando novo aluno:");
            Aluno novoAluno = new Aluno("Carlos Eduardo", true, LocalDate.of(2024, 6, 1));
            alunoRepository.save(novoAluno);
            System.out.println("   Aluno: " + novoAluno.getNome());
            System.out.println("   Matricula: " + novoAluno.getMatricula());

            // 2. Nova disciplina
            System.out.println("\n2. Criando nova disciplina:");
            Disciplina novaDisc = new Disciplina("Inteligencia Artificial", 6);
            disciplinaRepository.save(novaDisc);
            System.out.println("   Disciplina: " + novaDisc.getNome());
            System.out.println("   ID: " + novaDisc.getId());

            // 3. Novo professor
            System.out.println("\n3. Criando novo professor:");
            Professor novoProf = new Professor("Ana Beatriz", "Machine Learning");
            professorRepository.save(novoProf);
            System.out.println("   Professor: " + novoProf.getNome());
            System.out.println("   ID: " + novoProf.getId());

            // 4. Associar professor a disciplina
            System.out.println("\n4. Associando professor a disciplina:");
            novaDisc.adicionarProfessor(novoProf);
            disciplinaRepository.save(novaDisc);
            System.out.println("   " + novoProf.getNome() + " agora leciona " + novaDisc.getNome());

            // 5. Nova matricula
            System.out.println("\n5. Criando nova matricula:");
            Matricula novaMat = new Matricula(9.2, new Date(), novoAluno, novaDisc);
            matriculaRepository.save(novaMat);
            System.out.println("   Aluno: " + novoAluno.getNome());
            System.out.println("   Disciplina: " + novaDisc.getNome());
            System.out.println("   Nota: " + novaMat.getNota());

            // 6. Matricular aluno existente
            System.out.println("\n6. Matriculando aluno existente:");
            alunoRepository.findById(1L).ifPresent(mariana -> {
                Matricula matMariana = new Matricula(8.7, new Date(), mariana, novaDisc);
                matriculaRepository.save(matMariana);
                System.out.println("   " + mariana.getNome() + " matriculada em " + novaDisc.getNome());
            });

            // 7. Atualizar registros
            System.out.println("\n7. Atualizando registros existentes:");

            // Ativar aluno
            alunoRepository.findById(2L).ifPresent(joao -> {
                if (!joao.getMatriculaAtiva()) {
                    joao.setMatriculaAtiva(true);
                    alunoRepository.save(joao);
                    System.out.println("   Aluno " + joao.getNome() + " ativado");
                }
            });

            // Aumentar creditos
            disciplinaRepository.findById(1L).ifPresent(poo -> {
                int antigo = poo.getCreditos();
                poo.setCreditos(antigo + 1);
                disciplinaRepository.save(poo);
                System.out.println("   Disciplina " + poo.getNome() + ": " +
                        antigo + " -> " + poo.getCreditos() + " creditos");
            });

            System.out.println("\nNovos dados inseridos com sucesso.");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        // -----------------------------------------------------
        // PARTE 3: VERIFICACAO FINAL
        // -----------------------------------------------------
        System.out.println("\n--- PARTE 3: Verificacao final ---");

        System.out.println("\nTotais finais no banco:");
        System.out.println("Alunos: " + alunoRepository.count());
        System.out.println("Disciplinas: " + disciplinaRepository.count());
        System.out.println("Professores: " + professorRepository.count());
        System.out.println("Matriculas: " + matriculaRepository.count());

        // Verificar aluno criado via Java
        System.out.println("\nVerificando aluno criado via Java:");
        for (Aluno aluno : alunoRepository.findAll()) {
            if (aluno.getNome().equals("Carlos Eduardo")) {
                System.out.println("  Encontrado: " + aluno.getNome());
                System.out.println("  Matricula: " + aluno.getMatricula());
                System.out.println("  Numero de matriculas: " + aluno.getMatriculas().size());
                break;
            }
        }

        // Verificar disciplina criada via Java
        System.out.println("\nVerificando disciplina criada via Java:");
        for (Disciplina disc : disciplinaRepository.findAll()) {
            if (disc.getNome().equals("Inteligencia Artificial")) {
                System.out.println("  Encontrada: " + disc.getNome());
                System.out.println("  Numero de professores: " + disc.getProfessores().size());
                System.out.println("  Numero de matriculas: " + disc.getMatriculas().size());
                break;
            }
        }

        System.out.println("\n=== FIM DOS TESTES ===");
           }
}