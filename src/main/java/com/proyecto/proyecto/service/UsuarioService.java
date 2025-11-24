package com.proyecto.proyecto.service;

import com.proyecto.proyecto.dto.*;
import com.proyecto.proyecto.model.Usuario;
import com.proyecto.proyecto.repository.UsuarioRepository;
import com.proyecto.proyecto.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;
    private final ReniecService reniecService;

    public String iniciarRegistro(RegistroRequest request) {
        if (usuarioRepository.existsByDni(request.getDni())) {
            throw new RuntimeException("El DNI ya está registrado");
        }
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setDni(request.getDni());
        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setCorreo(request.getCorreo());
        usuario.setDepartamento(request.getDepartamento());
        usuario.setDistrito(request.getDistrito());
        usuario.setPin(passwordEncoder.encode(request.getPin()));
        usuario.setVerificado(false);

        String codigo = generarCodigoVerificacion();
        usuario.setCodigoVerificacion(codigo);
        usuario.setCodigoExpiracion(LocalDateTime.now().plusMinutes(15));

        usuarioRepository.save(usuario);
        emailService.enviarCodigoVerificacion(usuario.getCorreo(), codigo);

        return "Registro iniciado. Revisa tu correo para el código de verificación.";
    }

    public LoginResponse verificarCodigo(VerificarCodigoRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getVerificado()) {
            throw new RuntimeException("El usuario ya está verificado");
        }

        if (usuario.getCodigoExpiracion().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El código ha expirado");
        }

        if (!usuario.getCodigoVerificacion().equals(request.getCodigo())) {
            throw new RuntimeException("Código incorrecto");
        }

        usuario.setVerificado(true);
        usuario.setCodigoVerificacion(null);
        usuario.setCodigoExpiracion(null);
        usuarioRepository.save(usuario);

        String token = jwtUtil.generateToken(usuario.getDni(), usuario.getRole().name());
        return new LoginResponse(token, UsuarioResponse.fromUsuario(usuario));
    }

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByDni(request.getDni())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!usuario.getVerificado()) {
            throw new RuntimeException("Usuario no verificado");
        }

        if (!passwordEncoder.matches(request.getPin(), usuario.getPin())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String token = jwtUtil.generateToken(usuario.getDni(), usuario.getRole().name());
        return new LoginResponse(token, UsuarioResponse.fromUsuario(usuario));
    }

    public UsuarioResponse obtenerPerfil(String dni) {
        Usuario usuario = usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return UsuarioResponse.fromUsuario(usuario);
    }

    public String cambiarFotoPerfil(String dni, MultipartFile archivo) throws IOException {
        Usuario usuario = usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename();
        Path rutaArchivo = Paths.get("uploads", nombreArchivo);
        Files.createDirectories(rutaArchivo.getParent());
        Files.write(rutaArchivo, archivo.getBytes());

        usuario.setFotoPerfil(nombreArchivo);
        usuarioRepository.save(usuario);

        return "Foto de perfil actualizada";
    }

    public String cambiarRole(CambiarRoleRequest request, String dniAdmin) {
        Usuario admin = usuarioRepository.findByDni(dniAdmin)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));

        if (admin.getRole() != Usuario.Role.SUPERADMINISTRADOR) {
            throw new RuntimeException("No tienes permisos para cambiar roles");
        }

        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        try {
            Usuario.Role nuevoRole = Usuario.Role.valueOf(request.getNuevoRole());
            usuario.setRole(nuevoRole);
            usuarioRepository.save(usuario);
            return "Role actualizado correctamente";
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Role inválido");
        }
    }

    private String generarCodigoVerificacion() {
        return String.format("%06d", new Random().nextInt(999999));
    }
}
