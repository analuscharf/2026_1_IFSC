package ifsc.eng.Lab_Ana_BCD;


import ifsc.eng.Lab_Ana_BCD.Aluno;
import ifsc.eng.Lab_Ana_BCD.AlunoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootApplication
public class LabAnaBcdApplication {

	public static void main(String[] args) {
		SpringApplication.run(LabAnaBcdApplication.class, args);
	}


	// O Spring injeta (passa) automaticamente o AlunoRepository para este método.
	@Bean
	public CommandLineRunner brincarComDados(AlunoRepository repository) {
		return args -> {

			// --- 1. CREATE: Inserindo (save) ---
			System.out.println("--- 1. INSERT: Salvando Alunos ---");

			// Usamos o construtor gerado pelo Lombok @AllArgsConstructor
			// null para 'matricula' (PK), pois ela é gerada pelo banco.
			Aluno alunoA = new Aluno(null, "Mariana Luz", true, LocalDate.of(2024, 2, 1));
			Aluno alunoB = new Aluno(null, "Pedro Rocha", true, LocalDate.of(2023, 8, 15));

			repository.save(alunoA);
			repository.save(alunoB);

			System.out.println("Aluno A salvo. Matrícula: " + alunoA.getMatricula());
			System.out.println("Aluno B salvo. Matrícula: " + alunoB.getMatricula());

			// --- 2. READ ALL: Buscando todos (findAll) ---
			System.out.println("\n--- 2. SELECT ALL: Todos os Alunos ---");
			repository.findAll().forEach(System.out::println);

			// --- 3. READ BY ID: Buscando por Chave Primária (findById) ---
			System.out.println("\n--- 3. SELECT BY ID: Buscando aluno 1 ---");
			Optional<Aluno> encontrado = repository.findById(1L);

			encontrado.ifPresent(a -> {
				System.out.println("Aluno encontrado: " + a);

				// --- 4. UPDATE: Atualizando um registro ---
				System.out.println("\n--- 4. UPDATE: Mudando o status do aluno 1 ---");
				a.setMatriculaAtiva(false); // Usa o Setter gerado pelo Lombok
				repository.save(a); // O save() atualiza se a PK já existe

				System.out.println("Status atualizado! Novo dado: " + repository.findById(1L).get());
			});

			// --- 5. DELETE: Removendo um registro ---
			if (alunoB.getMatricula() != null) {
				System.out.println("\n--- 5. DELETE: Removendo o aluno B ---");
				repository.deleteById(alunoB.getMatricula());
				System.out.println("Aluno B removido. Total de alunos restantes: " + repository.count());
			}

		};
	}
}