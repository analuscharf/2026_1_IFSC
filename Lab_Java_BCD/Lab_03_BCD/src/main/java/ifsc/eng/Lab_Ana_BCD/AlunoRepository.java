package ifsc.eng.Lab_Ana_BCD;

import ifsc.eng.Lab_Ana_BCD.Aluno;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// CrudRepository<TIPO_DA_ENTIDADE, TIPO_DO_ID>
@Repository
public interface AlunoRepository extends CrudRepository<Aluno, Long> {

    // O Spring Data JPA já implementou para você:
    // save(), findById(), findAll(), delete(), count(), etc.

}