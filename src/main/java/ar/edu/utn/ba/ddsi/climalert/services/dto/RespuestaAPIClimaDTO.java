package ar.edu.utn.ba.ddsi.climalert.services.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RespuestaAPIClimaDTO {
  @JsonProperty("location")
  private UbicacionDTO ubicacion;

  @JsonProperty("current")
  private ClimaActualDTO climaActual;

  @Data
  public static class UbicacionDTO {
    @JsonProperty("name")
    private String nombreDeUbicacion;

    @JsonProperty("country")
    private String pais;
  }

  @Data
  public static class ClimaActualDTO {
    @JsonProperty("temp_c")
    private double temperaturaGradosCelsius;

    @JsonProperty("humidity")
    private int porcentajeHumedad;
  }
}