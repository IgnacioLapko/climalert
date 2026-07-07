package ar.edu.utn.ba.ddsi.climalert.services.impl.AlertaClimaServiceImplTest;

import ar.edu.utn.ba.ddsi.climalert.models.RegistroClima;
import ar.edu.utn.ba.ddsi.climalert.repositories.RegistroClimaRepository;
import ar.edu.utn.ba.ddsi.climalert.services.NotificacionesCorreoService;
import ar.edu.utn.ba.ddsi.climalert.services.impl.AlertaClimaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlertaClimaServiceImplTest {

  @Mock
  private RegistroClimaRepository repositorioClima;

  @Mock
  private NotificacionesCorreoService servicioNotificacion;

  @InjectMocks
  private AlertaClimaServiceImpl alertaClimaService;

  @Test
  void analizarCondicionesMeteorologicas_sinDatos_noHaceNada() {
    when(repositorioClima.findLast()).thenReturn(Optional.empty());

    alertaClimaService.analizarCondicionesMeteorologicas();

    verify(servicioNotificacion, never()).enviarCorreoDeAlertaCritica(any());
  }

  @Test
  void analizarCondicionesMeteorologicas_climaNormal_noEnviaCorreo() {
    // Clima normal (25°C, 50% humedad)
    RegistroClima registroNormal = new RegistroClima("CABA", 25.0, 50);
    when(repositorioClima.findLast()).thenReturn(Optional.of(registroNormal));

    alertaClimaService.analizarCondicionesMeteorologicas();

    verify(servicioNotificacion, never()).enviarCorreoDeAlertaCritica(any());
  }

  @Test
  void analizarCondicionesMeteorologicas_climaCritico_enviaCorreo() {
    // Clima crítico (38°C, 65% humedad)
    RegistroClima registroCritico = new RegistroClima("CABA", 38.0, 65);
    when(repositorioClima.findLast()).thenReturn(Optional.of(registroCritico));

    alertaClimaService.analizarCondicionesMeteorologicas();

    verify(servicioNotificacion, times(1)).enviarCorreoDeAlertaCritica(registroCritico);
  }

  @Test
  void analizarCondicionesMeteorologicas_climaCriticoRepetido_noEnviaSpam() {
    // Clima crítico
    RegistroClima registroCritico = new RegistroClima("CABA", 38.0, 65);
    when(repositorioClima.findLast()).thenReturn(Optional.of(registroCritico));

    // Simulamos que el cron pasa 3 veces (3 minutos distintos) con el MISMO registro
    alertaClimaService.analizarCondicionesMeteorologicas(); // Minuto 1
    alertaClimaService.analizarCondicionesMeteorologicas(); // Minuto 2
    alertaClimaService.analizarCondicionesMeteorologicas(); // Minuto 3

    // Aunque pasó 3 veces, el correo se envió 1 sola vez
    verify(servicioNotificacion, times(1)).enviarCorreoDeAlertaCritica(registroCritico);
  }

  @Test
  void analizarCondicionesMeteorologicas_climaCriticoNuevo_enviaCorreoNuevo() {
    // Primer clima crítico
    RegistroClima registroCritico1 = new RegistroClima("CABA", 38.0, 65);
    // Forzamos una fecha de hace 5 minutos para simular un registro viejo
    registroCritico1.setFechaHora(LocalDateTime.now().minusMinutes(5));

    // Segundo clima crítico (nueva lectura de la API)
    RegistroClima registroCritico2 = new RegistroClima("CABA", 39.0, 70);

    when(repositorioClima.findLast())
        .thenReturn(Optional.of(registroCritico1))
        .thenReturn(Optional.of(registroCritico2));

    alertaClimaService.analizarCondicionesMeteorologicas();
    alertaClimaService.analizarCondicionesMeteorologicas();

    verify(servicioNotificacion, times(1)).enviarCorreoDeAlertaCritica(registroCritico1);
    verify(servicioNotificacion, times(1)).enviarCorreoDeAlertaCritica(registroCritico2);
  }
}