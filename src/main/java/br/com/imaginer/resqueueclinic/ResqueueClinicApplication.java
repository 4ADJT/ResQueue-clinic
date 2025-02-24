package br.com.imaginer.resqueueclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ResqueueClinicApplication {

  public static void main(String[] args) {
    SpringApplication.run(ResqueueClinicApplication.class, args);
  }

}
