package com.proyecto.proyecto.config;

import com.proyecto.proyecto.model.Usuario;
import com.proyecto.proyecto.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    private final com.proyecto.proyecto.repository.CandidatoRepository candidatoRepository;

    @Override
    public void run(String... args) {
        // Crear SuperAdmin
        if (usuarioRepository.count() == 0) {
            Usuario superAdmin = new Usuario();
            superAdmin.setNombres("Super");
            superAdmin.setApellidos("Administrador");
            superAdmin.setDni("99999999");
            superAdmin.setCorreo("admin@sistema.com");
            superAdmin.setDistrito("Lima");
            superAdmin.setDepartamento("Lima");
            superAdmin.setPin(passwordEncoder.encode("999999"));
            superAdmin.setRole(Usuario.Role.SUPERADMINISTRADOR);
            superAdmin.setVerificado(true);
            
            usuarioRepository.save(superAdmin);
            
            System.out.println("===========================================");
            System.out.println("SUPERADMINISTRADOR CREADO:");
            System.out.println("DNI: 99999999");
            System.out.println("PIN: 999999");
            System.out.println("===========================================");
        }

        // Crear Candidatos de ejemplo
        if (candidatoRepository.count() == 0) {
            // Candidatos a Presidente
            com.proyecto.proyecto.model.Candidato p1 = new com.proyecto.proyecto.model.Candidato();
            p1.setNombre("María Elena Torres");
            p1.setPartido("Partido Progresista");
            p1.setTipo(com.proyecto.proyecto.model.Candidato.TipoCandidato.PRESIDENTE);
            p1.setPropuestas("Educación gratuita y salud universal");
            candidatoRepository.save(p1);

            com.proyecto.proyecto.model.Candidato p2 = new com.proyecto.proyecto.model.Candidato();
            p2.setNombre("Carlos Mendoza");
            p2.setPartido("Alianza Nacional");
            p2.setTipo(com.proyecto.proyecto.model.Candidato.TipoCandidato.PRESIDENTE);
            p2.setPropuestas("Desarrollo económico y empleo");
            candidatoRepository.save(p2);

            com.proyecto.proyecto.model.Candidato p3 = new com.proyecto.proyecto.model.Candidato();
            p3.setNombre("Ana Lucía Vargas");
            p3.setPartido("Frente Democrático");
            p3.setTipo(com.proyecto.proyecto.model.Candidato.TipoCandidato.PRESIDENTE);
            p3.setPropuestas("Seguridad ciudadana y justicia");
            candidatoRepository.save(p3);

            // Candidatos a Alcalde
            com.proyecto.proyecto.model.Candidato a1 = new com.proyecto.proyecto.model.Candidato();
            a1.setNombre("Jorge Ramírez");
            a1.setPartido("Movimiento Municipal");
            a1.setTipo(com.proyecto.proyecto.model.Candidato.TipoCandidato.ALCALDE);
            a1.setPropuestas("Mejora de parques y áreas verdes");
            candidatoRepository.save(a1);

            com.proyecto.proyecto.model.Candidato a2 = new com.proyecto.proyecto.model.Candidato();
            a2.setNombre("Patricia Flores");
            a2.setPartido("Lima para Todos");
            a2.setTipo(com.proyecto.proyecto.model.Candidato.TipoCandidato.ALCALDE);
            a2.setPropuestas("Transporte público eficiente");
            candidatoRepository.save(a2);

            com.proyecto.proyecto.model.Candidato a3 = new com.proyecto.proyecto.model.Candidato();
            a3.setNombre("Roberto Silva");
            a3.setPartido("Independiente");
            a3.setTipo(com.proyecto.proyecto.model.Candidato.TipoCandidato.ALCALDE);
            a3.setPropuestas("Seguridad y limpieza pública");
            candidatoRepository.save(a3);

            System.out.println("===========================================");
            System.out.println("CANDIDATOS CREADOS:");
            System.out.println("3 Presidentes y 3 Alcaldes");
            System.out.println("===========================================");
        }
    }
}
