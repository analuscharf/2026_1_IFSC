package ifsc.eng.Lab_Ana_BCD;



import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor // Gera construtor com campos @NonNull e final
@EqualsAndHashCode(exclude = "alunos")
@ToString(exclude = "alunos")
public class Orientador {

    @Id
    private String siape; // Não precisa ser @NonNull, pois pode ser injetado manualmente

    @NonNull // ESTE campo vai para o construtor
    private String nome;

    @NonNull // ESTE campo vai para o construtor
    private String area;

    // --- Mapeamento do Relacionamento 1:N (O lado 'One') ---

    // 1. @OneToMany: Indica que um Orientador tem MÚLTIPLOS Alunos
    @OneToMany(mappedBy = "orientador",
            cascade = CascadeType.ALL, // Se deletar o Orientador, deleta todos os Alunos
            fetch = FetchType.LAZY) // Carrega a lista de alunos somente quando acessada
    private List<Aluno> alunos = new ArrayList<>();


    // Método de ajuda para manter a relação bi-direcional consistente
    public void adicionarAluno(Aluno aluno) {
        this.alunos.add(aluno);
        aluno.setOrientador(this);
    }
}