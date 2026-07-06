package ar.edu.utn.ba.ddsi.climalert.repositories;

import ar.edu.utn.ba.ddsi.climalert.models.RegistroClima;
import java.util.List;
import java.util.Optional;

public interface RegistroClimaticoRepository {
  void save(RegistroClima registro);
  Optional<RegistroClima> findLast();
  List<RegistroClima> findAll();
}
