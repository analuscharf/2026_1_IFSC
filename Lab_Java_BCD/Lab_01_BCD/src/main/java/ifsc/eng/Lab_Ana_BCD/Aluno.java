package ifsc.eng.Lab_Ana_BCD;


import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity // Diz ao JPA que esta classe é uma tabela
@Data // Gera Getters, Setters, ToString, etc.
@NoArgsConstructor // Construtor padrão (OBRIGATÓRIO para o JPA)
@AllArgsConstructor // Construtor com todos os campos (útil para criar objetos)
public class Aluno {

    @Id // Marca como Chave Primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremento no banco
    private Long matricula;

    private String nome;

    private Boolean matriculaAtiva;

    private LocalDate dataInicio;
}