package ifsc.eng.Lab_Ana_BCD;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor // Gera construtor com campos @NonNull
// Excluímos a nova coleção 'matriculas' do Equals/HashCode e ToString
@EqualsAndHashCode(exclude = {"matriculas"})
@ToString(exclude = {"matriculas"})
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matricula;

    @NonNull // ESTE campo vai para o construtor
    private String nome;

    @NonNull // ESTE campo vai para o construtor
    private Boolean matriculaAtiva;

    @NonNull // ESTE campo vai para o construtor
    private LocalDate dataInicio;

    // --- NOVO RELACIONAMENTO N:M (com atributo 'nota') ---
    // Mapeamento 1:N com a entidade de junção Matricula
    @OneToMany(
            mappedBy = "aluno",
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<Matricula> matriculas = new HashSet<>();
    // Método de ajuda para gerenciar a relação bidirecional
    public void adicionarMatricula(Matricula matricula) {
        this.matriculas.add(matricula);
        matricula.setAluno(this);
    }
}