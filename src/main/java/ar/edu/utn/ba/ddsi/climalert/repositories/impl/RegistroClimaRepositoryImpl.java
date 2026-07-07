package ar.edu.utn.ba.ddsi.climalert.repositories.impl;

import ar.edu.utn.ba.ddsi.climalert.models.RegistroClima;
import ar.edu.utn.ba.ddsi.climalert.repositories.RegistroClimaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class RegistroClimaRepositoryImpl implements RegistroClimaRepository {

  private final List<RegistroClima> almacenamientoEnMemoria = new CopyOnWriteArrayList<>();

  public void save(RegistroClima registro) {
    almacenamientoEnMemoria.add(registro);
  }

  public Optional<RegistroClima> findLast() {
    if (almacenamientoEnMemoria.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(almacenamientoEnMemoria.get(almacenamientoEnMemoria.size() - 1));
  }

  public List<RegistroClima> findAll() {
    return almacenamientoEnMemoria;
  }
}