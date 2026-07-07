package ar.edu.utn.ba.ddsi.climalert.services.impl;

import ar.edu.utn.ba.ddsi.climalert.models.RegistroClima;
import ar.edu.utn.ba.ddsi.climalert.repositories.RegistroClimaRepository;
import ar.edu.utn.ba.ddsi.climalert.services.RegistroClimaService;
import ar.edu.utn.ba.ddsi.climalert.services.dto.RespuestaAPIClimaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RegistroClimaServiceImpl implements RegistroClimaService {

  private final RestTemplate clienteRest;
  private final RegistroClimaRepository repositorioClima;

  @Value("${weatherapi.key}")
  private String claveAccesoApi;

  @Value("${weatherapi.location}")
  private String ubicacionObjetivo;

  // cron que ejecuta cada 5 minutos
  @Scheduled(cron = "0 */5 * * * *")
  public void obtenerYGuardarDatosClimaticos() {
    try {
      String urlDeConsulta = String.format("http://api.weatherapi.com/v1/current.json?key=%s&q=%s", claveAccesoApi, ubicacionObjetivo);
      RespuestaAPIClimaDTO respuestaClima = clienteRest.getForObject(urlDeConsulta, RespuestaAPIClimaDTO.class);

      if (respuestaClima != null && respuestaClima.getClimaActual() != null) {
        RegistroClima nuevoRegistro = new RegistroClima(
            ubicacionObjetivo,
            respuestaClima.getClimaActual().getTemperaturaGradosCelsius(),
            respuestaClima.getClimaActual().getPorcentajeHumedad()
        );
        repositorioClima.save(nuevoRegistro);
        System.out.println("Datos climáticos almacenados: " + nuevoRegistro);
      }
    } catch (Exception excepcion) {
      System.err.println("Error al obtener datos de WeatherAPI: " + excepcion.getMessage());
    }
  }
}
