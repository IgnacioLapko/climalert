package ar.edu.utn.ba.ddsi.climalert.services;

import ar.edu.utn.ba.ddsi.climalert.models.RegistroClima;

public interface NotificacionesCorreoService {
  void enviarCorreoDeAlertaCritica(RegistroClima registroConPeligro);
}
