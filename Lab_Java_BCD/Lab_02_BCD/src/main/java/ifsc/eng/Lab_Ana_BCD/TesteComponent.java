package ifsc.eng.Lab_Ana_BCD;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Component // Indica ao Spring que esta é uma classe gerenciada
public class TesteComponent {

    private final AlunoRepository alunoRepository;
    private final OrientadorRepository orientadorRepository;

    // Injeção de dependências no construtor
    public TesteComponent(AlunoRepository alunoRepository, OrientadorRepository orientadorRepository) {
        this.alunoRepository = alunoRepository;
        this.orientadorRepository = orientadorRepository;
    }

    // O @Transactional APLICADO AQUI GARANTE QUE A SESSÃO DO HIBERNATE FIQUE ABERTA
    @Transactional
    public void rodarTestes() {

        // --- 1. CREATE ORIENTADOR ---
        System.out.println("--- 1. INSERT: Salvando Orientador ---");
        Orientador drPedro = new Orientador("Dr. Pedro Álvares", "Banco de Dados");
        drPedro.setSiape("123456");
        orientadorRepository.save(drPedro);
        System.out.println("Orientador salvo: " + drPedro.getNome());

        // --- 2. CREATE ALUNOS E RELACIONAR ---
        System.out.println("\n--- 2. INSERT: Salvando Alunos e relacionando ---");
        Aluno alunoA = new Aluno("Mariana Luz", true, LocalDate.of(2024, 2, 1));
        Aluno alunoB = new Aluno("Pedro Rocha", true, LocalDate.of(2023, 8, 15));

        drPedro.adicionarAluno(alunoA);
        drPedro.adicionarAluno(alunoB);

        alunoRepository.save(alunoA);
        alunoRepository.save(alunoB);

        System.out.println("Alunos salvos! Matrículas: " + alunoA.getMatricula() + ", " + alunoB.getMatricula());

        // --- 3. READ: Buscando um aluno e acessando o Orientador ---
        System.out.println("\n--- 3. SELECT: Aluno 1 e seu Orientador ---");
        Aluno alunoEncontrado = alunoRepository.findById(alunoA.getMatricula())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // ACESSO LAZY FUNCIONA AQUI!
        System.out.println("Aluno: " + alunoEncontrado.getNome());
        System.out.println("Orientador do Aluno: " + alunoEncontrado.getOrientador().getNome());

        // --- 4. READ: Buscando um orientador e acessando a lista de alunos ---
        System.out.println("\n--- 4. SELECT: Orientador e sua lista de Alunos ---");
        Orientador orientadorEncontrado = orientadorRepository.findById("123456")
                .orElseThrow(() -> new RuntimeException("Orientador não encontrado"));

        System.out.println("Orientador: " + orientadorEncontrado.getNome());
        System.out.println("Alunos sob orientação: ");
        // ACESSO LAZY FUNCIONA AQUI!
        orientadorEncontrado.getAlunos().forEach(a -> System.out.println("- " + a.getNome()));
    }
}