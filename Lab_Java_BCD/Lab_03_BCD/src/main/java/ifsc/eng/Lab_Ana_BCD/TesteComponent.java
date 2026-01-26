package ifsc.eng.Lab_Ana_BCD;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.List; // Novo import necessário

@Component
@RequiredArgsConstructor
public class TesteComponent {

    // Repositórios injetados
    private final AlunoRepository alunoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final ProfessorRepository professorRepository;
    private final MatriculaRepository matriculaRepository;

    @Transactional // ESSENCIAL: Garante que as consultas LAZY funcionem
    public void rodarTestes() {
        System.out.println("=================================================");
        System.out.println("         INICIANDO TESTES JPA N:M              ");
        System.out.println("=================================================");

        // ----------------------------------------------------
        // I. CRIAÇÃO DE ENTIDADES BÁSICAS
        // NOTA: É CRUCIAL SALVAR PRIMEIRO para garantir que os IDs sejam gerados
        // ----------------------------------------------------

        // 1. Alunos (IDs gerados aqui)
        Aluno alunoA = new Aluno("Mariana Luz", true, LocalDate.of(2024, 2, 1));
        Aluno alunoB = new Aluno("Pedro Rocha", true, LocalDate.of(2023, 8, 15));
        alunoA = alunoRepository.save(alunoA); // Atribuir de volta o objeto salvo com ID
        alunoB = alunoRepository.save(alunoB); // Atribuir de volta o objeto salvo com ID
        System.out.println("Alunos salvos. Matrícula A: " + alunoA.getMatricula() + ", Matrícula B: " + alunoB.getMatricula());

        // 2. Professores
        Professor profPedro = new Professor("Dr. Pedro Alvares", "Banco de Dados");
        Professor profAna = new Professor("Dra. Ana Costa", "Programação");
        profPedro = professorRepository.save(profPedro);
        profAna = professorRepository.save(profAna);
        System.out.println("Professores salvos: " + profPedro.getNome() + ", " + profAna.getNome());

        // 3. Disciplinas (IDs gerados aqui)
        Disciplina discBD = new Disciplina("Banco de Dados II", 4);
        Disciplina discPOO = new Disciplina("Programação Orientada a Objetos", 6);
        discBD = disciplinaRepository.save(discBD);
        discPOO = disciplinaRepository.save(discPOO);
        System.out.println("Disciplinas salvas: " + discBD.getNome() + ", " + discPOO.getNome());


        // ----------------------------------------------------
        // II. TESTE N:M SIMPLES (DISCIPLINA <=> PROFESSOR)
        // ----------------------------------------------------
        System.out.println("\n--- 1. TESTE N:M SIMPLES (Professor/Disciplina) ---");

        discBD.adicionarProfessor(profPedro);
        discPOO.adicionarProfessor(profAna);
        discPOO.adicionarProfessor(profPedro);

        // O save no lado "proprietário" atualiza a tabela de junção
        disciplinaRepository.save(discBD);
        disciplinaRepository.save(discPOO);

        // CONSULTA LAZY: Buscar e acessar a lista de Professores
        Optional<Disciplina> dPOO = disciplinaRepository.findById(discPOO.getId());
        if (dPOO.isPresent()) {
            Disciplina d = dPOO.get();
            System.out.println("Disciplina: " + d.getNome());
            System.out.print("Lecionada por: ");
            d.getProfessores().forEach(p -> System.out.print(p.getNome() + " | "));
            System.out.println();
        }

        // ----------------------------------------------------
        // III. TESTE N:M COM ATRIBUTO (ALUNO <=> DISCIPLINA via MATRICULA)
        // ----------------------------------------------------
        System.out.println("\n--- 2. TESTE N:M COM ATRIBUTO (Aluno/Disciplina/Matricula) ---");

        // 1. Criar as Matrículas (o construtor cria o MatriculaId com os IDs gerados)
        Matricula m1 = new Matricula(8.5, new Date(), alunoA, discBD);
        Matricula m2 = new Matricula(9.8, new Date(), alunoA, discPOO);
        Matricula m3 = new Matricula(6.0, new Date(), alunoB, discBD);

        // 2. [AÇÃO CORRETIVA] Manter a bidirecionalidade na memória
        // Isso é crucial para que, ao final da transação, o Hibernate saiba que
        // a coleção 'matriculas' do Aluno tem esses itens
        alunoA.adicionarMatricula(m1);
        alunoA.adicionarMatricula(m2);
        alunoB.adicionarMatricula(m3);

        // 3. Salvar as Matrículas no banco
        matriculaRepository.saveAll(List.of(m1, m2, m3));
        System.out.println("Matrículas salvas. Nota M1: " + m1.getNota());

        // 4. [AÇÃO CORRETIVA] Recarregar o Aluno do banco
        // Isto garante que o objeto Aluno 'a' lido abaixo tenha sua coleção 'matriculas'
        // carregada corretamente pelo Hibernate do banco, não apenas da memória do objeto 'alunoA'.
        Optional<Aluno> aA = alunoRepository.findById(alunoA.getMatricula());

        // CONSULTA: Acessar a coleção que estava vazia
        if (aA.isPresent()) {
            Aluno a = aA.get();
            System.out.println("Aluno: " + a.getNome());
            System.out.println("Histórico de Notas (Matrículas):");

            // O loop AGORA DEVE IMPRIMIR OS DADOS
            a.getMatriculas().forEach(m ->
                    System.out.println("- Disciplina: " + m.getDisciplina().getNome() + ", Nota: " + m.getNota())
            );
        }

        System.out.println("\n=================================================");
        System.out.println("         TESTES CONCLUÍDOS COM SUCESSO           ");
        System.out.println("=================================================");
    }
}