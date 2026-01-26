package ifsc.eng.Lab_Ana_BCD;

import org.springframework.data.repository.CrudRepository;

// Disciplina tem Long como chave primária (id)
public interface DisciplinaRepository extends CrudRepository<Disciplina, Long> {

}