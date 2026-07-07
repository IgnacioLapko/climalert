package ar.edu.utn.ba.ddsi.climalert.services.impl;

import ar.edu.utn.ba.ddsi.climalert.models.RegistroClima;
import ar.edu.utn.ba.ddsi.climalert.services.NotificacionesCorreoService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificacionesCorreoServiceImpl implements NotificacionesCorreoService {

  private final List<String> listaDeDestinatarios = List.of(
      "admin@clima.com",
      "emergencias@clima.com",
      "meteorologia@clima.com"
  );

  public void enviarCorreoDeAlertaCritica(RegistroClima registroConPeligro) {
    System.out.println("\n================ ALERTA CLIMÁTICA ================");
    System.out.println("Enviando correos a: " + String.join(", ", listaDeDestinatarios));
    System.out.println("Asunto: ALERTA DE PELIGRO METEOROLÓGICO");
    System.out.println("Cuerpo del mensaje:");
    System.out.println("Se han detectado condiciones climáticas inusuales/peligrosas.");
    System.out.println("Detalle completo del clima:");
    System.out.println(registroConPeligro.toString());
    System.out.println("==================================================\n");
  }
}