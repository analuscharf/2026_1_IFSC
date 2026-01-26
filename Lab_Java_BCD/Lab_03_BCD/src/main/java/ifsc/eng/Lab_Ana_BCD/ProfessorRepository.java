package ifsc.eng.Lab_Ana_BCD;

import org.springframework.data.repository.CrudRepository;

// Professor tem Long como chave primária (id)
public interface ProfessorRepository extends CrudRepository<Professor, Long> {

}