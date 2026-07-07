package ar.edu.utn.ba.ddsi.climalert.services.impl;

import ar.edu.utn.ba.ddsi.climalert.models.RegistroClima;
import ar.edu.utn.ba.ddsi.climalert.repositories.RegistroClimaRepository;
import ar.edu.utn.ba.ddsi.climalert.services.AlertaClimaService;
import ar.edu.utn.ba.ddsi.climalert.services.NotificacionesCorreoService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlertaClimaServiceImpl implements AlertaClimaService {

  private final RegistroClimaRepository repositorioClima;
  private final NotificacionesCorreoService servicioNotificacion;

  // Ejecuta cada 1 minuto
  @Scheduled(cron = "0 */1 * * * *")
  public void analizarCondicionesMeteorologicas() {
    Optional<RegistroClima> ultimoRegistroAnalizable = repositorioClima.findLast();

    ultimoRegistroAnalizable.ifPresent(registro -> {
      boolean temperaturaCritica = registro.getTemperatura() > 35.0;
      boolean humedadCritica = registro.getHumedad() > 60;

      if (temperaturaCritica && humedadCritica) {
        System.out.println("¡Condiciones críticas detectadas! Procesando alerta...");
        servicioNotificacion.enviarCorreoDeAlertaCritica(registro);
      } else {
        System.out.println("Clima analizado - Parámetros normales.");
      }
    });
  }
}