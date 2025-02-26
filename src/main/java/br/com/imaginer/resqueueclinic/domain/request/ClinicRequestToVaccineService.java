package br.com.imaginer.resqueueclinic.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record ClinicRequestToVaccineService(

    @JsonProperty("clinic_id") UUID clinicId,

    @JsonProperty("user_id") UUID userId,

    boolean active
) {
}
