package ifsc.eng.Lab_Ana_BCD;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
// REMOVA O IMPORT DE @Transactional DAQUI

@SpringBootApplication
public class LabAnaBcdApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabAnaBcdApplication.class, args);
    }

    @Bean
    // Injetamos o componente de lógica (TesteComponent) e o chamamos.
    // Este arquivo NÃO TEM @Transactional.
    public CommandLineRunner brincarComDados(TesteComponent testeComponent) {
        return args -> {
            testeComponent.rodarTestes(); // Chama o método transacional no outro arquivo
        };
    }
}