package ifsc.eng.Lab_Ana_BCD;



import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
/**
 * Chave Primária Composta para a entidade Matricula,
 * baseada nas chaves de Aluno e Disciplina.
 */
public class MatriculaId implements Serializable {

    // A chave primária do Aluno (FK para Aluno)
    private Long alunoId;

    // A chave primária da Disciplina (FK para Disciplina)
    private Long disciplinaId;
}