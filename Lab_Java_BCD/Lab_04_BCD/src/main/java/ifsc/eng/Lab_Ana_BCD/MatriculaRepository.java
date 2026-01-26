package ifsc.eng.Lab_Ana_BCD;

import org.springframework.data.repository.CrudRepository;

// Matricula tem Long como chave primária (id)
public interface MatriculaRepository extends CrudRepository<Matricula, MatriculaId> {
}