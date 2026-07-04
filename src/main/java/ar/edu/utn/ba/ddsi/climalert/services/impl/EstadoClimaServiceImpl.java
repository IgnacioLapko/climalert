package ar.edu.utn.ba.ddsi.climalert.services.impl;

import ar.edu.utn.ba.ddsi.climalert.models.EstadoClima;
import ar.edu.utn.ba.ddsi.climalert.services.EstadoClimaService;
import org.springframework.stereotype.Service;

@Service
public class EstadoClimaServiceImpl implements EstadoClimaService {
  @Override
  public EstadoClima obtenerCondicionesMetereologicas() {
    return null;
  }
}
//http://api.weatherapi.com/v1/current.json?q=Buenos Aires&key=64f8024b77584c73b3e213626260407