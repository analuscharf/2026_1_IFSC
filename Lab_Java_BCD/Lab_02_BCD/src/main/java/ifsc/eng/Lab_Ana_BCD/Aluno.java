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

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor // Gera construtor com campos @NonNull
@EqualsAndHashCode(exclude = "orientador")
@ToString(exclude = "orientador")
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

    // ... Mapeamento @ManyToOne (orientador) permanece o mesmo ...
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orientador_siape", nullable = true)
    private Orientador orientador;
}