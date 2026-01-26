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
@EqualsAndHashCode(exclude = "disciplinas")
@ToString(exclude = "disciplinas")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String nome;

    @NonNull
    private String area;

    // Relação N:M Simples: Mapeado pelo campo 'professores' em Disciplina
    @ManyToMany(mappedBy = "professores")
    private Set<Disciplina> disciplinas = new HashSet<>();
}