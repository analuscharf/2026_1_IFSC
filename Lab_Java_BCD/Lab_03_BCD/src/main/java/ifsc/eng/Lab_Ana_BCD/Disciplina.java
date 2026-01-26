package ifsc.eng.Lab_Ana_BCD;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = {"matriculas", "professores"})
@ToString(exclude = {"matriculas", "professores"})
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String nome;

    @NonNull
    private Integer creditos;

    // --- RELACIONAMENTO N:M (com nota) ---
    // Mapeamento 1:N com a entidade de junção Matricula
    @OneToMany(mappedBy = "disciplina", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Matricula> matriculas = new HashSet<>();

    // --- RELACIONAMENTO N:M (simples) ---
    // Lado que define a tabela de junção disciplina_professor
    @ManyToMany
    @JoinTable(
            name = "disciplina_professor",
            joinColumns = @JoinColumn(name = "disciplina_id"),
            inverseJoinColumns = @JoinColumn(name = "professor_id")
    )
    private Set<Professor> professores = new HashSet<>();

    // Métodos de ajuda
    public void adicionarMatricula(Matricula matricula) {
        this.matriculas.add(matricula);
        matricula.setDisciplina(this);
    }

    public void adicionarProfessor(Professor professor) {
        this.professores.add(professor);
    }
}