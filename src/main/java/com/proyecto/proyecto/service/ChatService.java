package com.proyecto.proyecto.service;

import com.proyecto.proyecto.dto.ChatRequest;
import com.proyecto.proyecto.model.Candidato;
import com.proyecto.proyecto.model.MensajeChat;
import com.proyecto.proyecto.model.Usuario;
import com.proyecto.proyecto.model.Voto;
import com.proyecto.proyecto.repository.MensajeChatRepository;
import com.proyecto.proyecto.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MensajeChatRepository mensajeChatRepository;
    private final UsuarioRepository usuarioRepository;
    private final VotacionService votacionService;

    public MensajeChat enviarMensaje(ChatRequest request, String dni) {
        Usuario usuario = usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String respuesta = generarRespuestaInteligente(request.getMensaje(), dni, usuario.getNombres());

        MensajeChat mensaje = new MensajeChat();
        mensaje.setUsuario(usuario);
        mensaje.setMensaje(request.getMensaje());
        mensaje.setRespuesta(respuesta);

        return mensajeChatRepository.save(mensaje);
    }

    public List<MensajeChat> obtenerHistorial(String dni) {
        Usuario usuario = usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return mensajeChatRepository.findByUsuarioIdOrderByFechaCreacionDesc(usuario.getId());
    }

    public List<String> obtenerSugerencias(String dni) {
        if (votacionService.yaVoto(dni)) {
            return List.of(
                "¿Cómo presento una reclamación?",
                "¿Cuál es el horario de atención?",
                "Ver resultados de votación",
                "¿Cómo cambio mi foto de perfil?"
            );
        }

        Optional<Voto> votoEnProgreso = votacionService.obtenerVotoEnProgreso(dni);
        if (votoEnProgreso.isPresent() && votoEnProgreso.get().getCandidatoPresidente() != null) {
            return List.of(
                "Continuar votación",
                "Ver candidatos a alcalde",
                "Cancelar votación",
                "¿Cómo funciona el sistema de votación?"
            );
        }

        return List.of(
            "¿Cómo puedo votar?",
            "¿Dónde puedo ver los candidatos?",
            "¿Cómo funciona el sistema de votación?",
            "¿Cómo gestiono los usuarios del sistema?"
        );
    }

    private String generarRespuestaInteligente(String mensaje, String dni, String nombreUsuario) {
        String mensajeLower = mensaje.toLowerCase().trim();
        
        if (mensajeLower.contains("votar") || mensajeLower.contains("votacion") || mensajeLower.contains("elecciones") || 
            mensajeLower.contains("candidato") || mensajeLower.contains("elegir")) {
            return manejarVotacion(dni);
        }
        
        if (mensajeLower.contains("registrar candidato") || mensajeLower.contains("nuevo candidato")) {
            return "📝 Para registrar un nuevo candidato:\n\n1. Debes tener permisos de ADMINISTRADOR o SUPERADMINISTRADOR\n2. Ve a la sección 'Candidatos' en el panel de administración\n3. Completa los datos del candidato\n4. Selecciona el tipo: PRESIDENTE o ALCALDE\n\n¿Necesitas ayuda con algo más?";
        }
        
        if (mensajeLower.contains("resultado") || mensajeLower.contains("ganador") || mensajeLower.contains("quien va ganando")) {
            return "📊 Para ver los resultados de la votación:\n\nLos resultados se mostrarán una vez finalizado el proceso electoral.\nSolo los administradores pueden ver resultados parciales.\n\n¿Deseas saber algo más?";
        }
        
        if (mensajeLower.contains("gestionar usuario") || mensajeLower.contains("cambiar rol") || mensajeLower.contains("administrador")) {
            return "👥 Gestión de Usuarios:\n\nComo SUPERADMINISTRADOR puedes:\n• Cambiar roles de usuarios\n• Ver lista de todos los usuarios\n• Activar/desactivar cuentas\n\nRoles disponibles:\n- USUARIO (por defecto)\n- ADMINISTRADOR\n- SUPERADMINISTRADOR\n\n¿Qué necesitas hacer?";
        }
        
        if (mensajeLower.matches("\\d+")) {
            return procesarVoto(dni, mensajeLower);
        }
        
        if (mensajeLower.contains("hola") || mensajeLower.contains("buenos") || mensajeLower.contains("buenas")) {
            String saludo = obtenerSaludo();
            return String.format("¡%s %s! 👋\n\nSoy tu asistente virtual. Puedo ayudarte con:\n\n🗳️ Votaciones\n📋 Reclamaciones\n👤 Gestión de perfil\nℹ️ Información general\n\n¿En qué puedo ayudarte hoy?", saludo, nombreUsuario);
        }
        
        if (mensajeLower.contains("ayuda") || mensajeLower.contains("que puedes hacer") || mensajeLower.contains("opciones")) {
            return "Puedo ayudarte con:\n\n🗳️ VOTACIONES:\n- Di 'quiero votar' para participar en las elecciones\n- Verifica si ya votaste\n\n📝 RECLAMACIONES:\n- Presentar quejas, reclamos o sugerencias\n\nℹ️ INFORMACIÓN:\n- Horarios de atención\n- Servicios disponibles\n\n¿Qué necesitas?";
        }
        
        if (mensajeLower.contains("horario") || mensajeLower.contains("atencion") || mensajeLower.contains("abierto")) {
            return "🕐 Nuestro horario de atención es:\nLunes a Viernes: 8:00 AM - 6:00 PM\nSábados: 9:00 AM - 1:00 PM\nDomingos: Cerrado";
        }
        
        if (mensajeLower.contains("reclamacion") || mensajeLower.contains("queja") || mensajeLower.contains("reclamo")) {
            return "📋 Para presentar una reclamación:\n\n1. Ve a la sección 'Libro de Reclamaciones'\n2. Completa el formulario con tu caso\n3. Puedes adjuntar evidencia (opcional)\n4. Recibirás una respuesta en 48 horas\n\nTipos: QUEJA, RECLAMO, SUGERENCIA";
        }
        
        if (mensajeLower.contains("perfil") || mensajeLower.contains("datos") || mensajeLower.contains("informacion personal")) {
            return "👤 Para ver tu perfil:\nVe a la sección 'Mi Perfil' donde podrás:\n- Ver tus datos personales\n- Cambiar tu foto de perfil\n- Ver tu historial de reclamaciones";
        }
        
        if (mensajeLower.contains("gracias") || mensajeLower.contains("thank")) {
            return "¡De nada! 😊 Estoy aquí para ayudarte cuando lo necesites. ¿Hay algo más en lo que pueda asistirte?";
        }
        
        if (mensajeLower.contains("adios") || mensajeLower.contains("chau") || mensajeLower.contains("hasta luego")) {
            return "¡Hasta luego! 👋 Que tengas un excelente día. Recuerda que estoy aquí cuando me necesites.";
        }
        
        return "Entiendo tu consulta. Puedo ayudarte con:\n• Votaciones (di 'quiero votar')\n• Reclamaciones\n• Información de servicios\n• Horarios\n\n¿Podrías ser más específico sobre lo que necesitas?";
    }

    private String manejarVotacion(String dni) {
        if (votacionService.yaVoto(dni)) {
            return "✅ Ya has ejercido tu derecho al voto anteriormente.\n\nTu voto fue registrado exitosamente. Gracias por participar en el proceso democrático. 🗳️";
        }

        Optional<Voto> votoEnProgreso = votacionService.obtenerVotoEnProgreso(dni);
        if (votoEnProgreso.isPresent() && votoEnProgreso.get().getCandidatoPresidente() != null) {
            return mostrarCandidatosAlcalde();
        }

        return mostrarCandidatosPresidente();
    }

    private String mostrarCandidatosPresidente() {
        List<Candidato> presidentes = votacionService.obtenerCandidatos(Candidato.TipoCandidato.PRESIDENTE);
        
        if (presidentes.isEmpty()) {
            return "Lo siento, no hay candidatos disponibles en este momento.";
        }

        StringBuilder respuesta = new StringBuilder();
        respuesta.append("🗳️ ELECCIONES PRESIDENCIALES\n\nCandidatos disponibles:\n\n");
        
        for (Candidato c : presidentes) {
            respuesta.append(String.format("%d. %s - %s\n", c.getId(), c.getNombre(), c.getPartido()));
            if (c.getPropuestas() != null) {
                respuesta.append(String.format("   Propuesta: %s\n", c.getPropuestas()));
            }
            respuesta.append("\n");
        }
        
        respuesta.append("Para votar, escribe el número del candidato de tu preferencia.");
        return respuesta.toString();
    }

    private String mostrarCandidatosAlcalde() {
        List<Candidato> alcaldes = votacionService.obtenerCandidatos(Candidato.TipoCandidato.ALCALDE);
        
        if (alcaldes.isEmpty()) {
            return "Lo siento, no hay candidatos a alcalde disponibles en este momento.";
        }

        StringBuilder respuesta = new StringBuilder();
        respuesta.append("🏛️ ELECCIONES MUNICIPALES\n\nCandidatos a Alcalde:\n\n");
        
        for (Candidato c : alcaldes) {
            respuesta.append(String.format("%d. %s - %s\n", c.getId(), c.getNombre(), c.getPartido()));
            if (c.getPropuestas() != null) {
                respuesta.append(String.format("   Propuesta: %s\n", c.getPropuestas()));
            }
            respuesta.append("\n");
        }
        
        respuesta.append("Para votar, escribe el número del candidato de tu preferencia.");
        return respuesta.toString();
    }

    private String procesarVoto(String dni, String numeroStr) {
        try {
            Long candidatoId = Long.parseLong(numeroStr);
            
            if (votacionService.yaVoto(dni)) {
                return "Ya has votado anteriormente. No puedes votar de nuevo.";
            }

            Optional<Voto> votoEnProgreso = votacionService.obtenerVotoEnProgreso(dni);
            
            if (votoEnProgreso.isEmpty() || votoEnProgreso.get().getCandidatoPresidente() == null) {
                String resultado = votacionService.votarPresidente(dni, candidatoId);
                return resultado + "\n\n" + mostrarCandidatosAlcalde();
            } else {
                return votacionService.votarAlcalde(dni, candidatoId);
            }
            
        } catch (NumberFormatException e) {
            return "Por favor, ingresa un número válido.";
        } catch (RuntimeException e) {
            return "Error: " + e.getMessage();
        }
    }

    private String obtenerSaludo() {
        int hora = java.time.LocalTime.now().getHour();
        if (hora >= 6 && hora < 12) {
            return "Buenos días";
        } else if (hora >= 12 && hora < 19) {
            return "Buenas tardes";
        } else {
            return "Buenas noches";
        }
    }
}
