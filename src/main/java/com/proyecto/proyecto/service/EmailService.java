package com.proyecto.proyecto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarCodigoVerificacion(String destinatario, String codigo) {
        try {
            System.out.println("📧 Intentando enviar email a: " + destinatario);
            System.out.println("📧 Código: " + codigo);
            
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setFrom("ochoareyesjosue@gmail.com");
            mensaje.setTo(destinatario);
            mensaje.setSubject("Código de Verificación - Sistema de Registro");
            mensaje.setText(
                "¡Hola!\n\n" +
                "Tu código de verificación es: " + codigo + "\n\n" +
                "Este código expira en 15 minutos.\n\n" +
                "Si no solicitaste este código, ignora este mensaje.\n\n" +
                "Saludos,\n" +
                "Sistema de Registro"
            );
            
            System.out.println("📧 Enviando email...");
            mailSender.send(mensaje);
            System.out.println("✅ Email enviado exitosamente a: " + destinatario);
            
        } catch (Exception e) {
            System.err.println("❌ Error al enviar email: " + e.getMessage());
            System.err.println("❌ Tipo de error: " + e.getClass().getName());
            e.printStackTrace();
            System.out.println("📧 Código de verificación (para pruebas): " + codigo);
            throw new RuntimeException("No se pudo enviar el email. Verifica la configuración de correo.");
        }
    }
}
