package ar.edu.utn.ba.ddsi.climalert.services.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RespuestaAPIClimaDTO(
    @JsonProperty("location") UbicacionDto ubicacion,
    @JsonProperty("current") ClimaActualDto climaActual
) {}

record UbicacionDto(
    @JsonProperty("name") String nombreDeUbicacion,
    @JsonProperty("country") String pais
) {}

record ClimaActualDto(
    @JsonProperty("temp_c") double temperaturaGradosCelsius,
    @JsonProperty("humidity") int porcentajeHumedad
) {}