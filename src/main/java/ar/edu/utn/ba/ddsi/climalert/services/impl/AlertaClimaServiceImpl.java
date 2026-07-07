package ar.edu.utn.ba.ddsi.climalert.services.impl;

import ar.edu.utn.ba.ddsi.climalert.models.RegistroClima;
import ar.edu.utn.ba.ddsi.climalert.repositories.RegistroClimaRepository;
// Importá acá tu servicio de notificaciones
import ar.edu.utn.ba.ddsi.climalert.services.NotificacionesCorreoService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlertaClimaServiceImpl {

  private final RegistroClimaRepository repositorioClima;
  private final NotificacionesCorreoService servicioNotificacion; // Ajustá este nombre al que estés usando

  private LocalDateTime fechaHoraUltimaAlertaEnviada;

  @Scheduled(cron = "0 */1 * * * *")
  public void analizarCondicionesMeteorologicas() {
    Optional<RegistroClima> ultimoRegistroAnalizable = repositorioClima.findLast();

    ultimoRegistroAnalizable.ifPresent(registro -> {
      boolean temperaturaCritica = registro.getTemperatura() > 35.0;
      boolean humedadCritica = registro.getHumedad() > 60;

      if (temperaturaCritica && humedadCritica) {
        if (fechaHoraUltimaAlertaEnviada == null || !fechaHoraUltimaAlertaEnviada.equals(registro.getFechaHora())) {

          System.out.println("¡Condiciones críticas detectadas! Procesando alerta...");
          servicioNotificacion.enviarCorreoDeAlertaCritica(registro);

          fechaHoraUltimaAlertaEnviada = registro.getFechaHora();

        } else {
          System.out.println("Condiciones críticas persistentes, pero la alerta de esta lectura ya fue enviada.");
        }
      } else {
        System.out.println("Clima analizado - Parámetros normales.");
      }
    });
  }
}