package ar.edu.utn.ba.ddsi.climalert.models;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RegistroClima {
  private LocalDateTime fechaHora;
  private String ubicacion;
  private double temperatura;
  private int humedad;

  public RegistroClima(String ubicacion, double temperatura, int humedad) {
    this.fechaHora = LocalDateTime.now();
    this.ubicacion = ubicacion;
    this.temperatura = temperatura;
    this.humedad = humedad;
  }
}