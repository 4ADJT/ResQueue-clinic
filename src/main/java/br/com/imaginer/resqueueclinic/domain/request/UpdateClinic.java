package br.com.imaginer.resqueueclinic.domain.request;

public record UpdateClinic(
    String name,

    String address,

    String phone
) {
}
