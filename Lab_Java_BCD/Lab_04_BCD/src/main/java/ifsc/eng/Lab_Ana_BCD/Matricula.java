package ifsc.eng.Lab_Ana_BCD;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
// Excluímos as entidades relacionadas para evitar recursão
@EqualsAndHashCode(exclude = {"aluno", "disciplina"})
@ToString(exclude = {"aluno", "disciplina"})
public class Matricula {

    // --- CHAVE PRIMÁRIA COMPOSTA ---
    @EmbeddedId
    private MatriculaId id;

    @NonNull
    private Double nota; // ATRIBUTO PRÓPRIO

    @NonNull
    @Temporal(TemporalType.DATE)
    private Date dataMatricula;

    // --- Mapeamento N:1 com Aluno ---
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("alunoId") // Mapeia o campo 'alunoId' da chave composta para a PK de Aluno
    @JoinColumn(name = "aluno_id") // Nome da coluna FK no banco
    private Aluno aluno;

    // --- Mapeamento N:1 com Disciplina ---
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("disciplinaId") // Mapeia o campo 'disciplinaId' da chave composta para a PK de Disciplina
    @JoinColumn(name = "disciplina_id") // Nome da coluna FK no banco
    private Disciplina disciplina;

    // Construtor adicional para facilitar a criação da entidade
    public Matricula(@NonNull Double nota, @NonNull Date dataMatricula, Aluno aluno, Disciplina disciplina) {
        this.nota = nota;
        this.dataMatricula = dataMatricula;
        this.aluno = aluno;
        this.disciplina = disciplina;

        // Ação crucial que o Lombok NÃO faz:
        // Inicializa o ID composto usando as chaves primárias das entidades relacionadas.
        this.id = new MatriculaId(aluno.getMatricula(), disciplina.getId());
    }
}