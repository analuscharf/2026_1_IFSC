package ifsc.eng.Lab_Ana_BCD;

import  ifsc.eng.Lab_Ana_BCD.Orientador;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrientadorRepository extends CrudRepository<Orientador, String> {
    // Tipo do ID é String, conforme definido na entidade Orientador.
}