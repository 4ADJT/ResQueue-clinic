# 🏥 Resqueue Clinic Service

## 📖 Sobre o Projeto
O **Resqueue Clinic Service** é um **microserviço** que gerencia dados de clínicas no sistema **Resqueue**. Ele utiliza **Spring Boot 3**, **Spring Security**, **OAuth 2.0**, **Eureka Client** e **PostgreSQL** como banco de dados.

Este serviço faz parte da arquitetura de **microserviços**, sendo **descoberto dinamicamente** via **Eureka Server** e integrado ao ecossistema **Resqueue**.

---

## 🚀 **Tecnologias Utilizadas**
- **Java 21 (Corretto)**
- **Spring Boot 3 (Web, Security, Eureka Client)**
- **Spring Cloud (OpenFeign, Netflix Eureka)**
- **Spring Security (OAuth 2.0, JWT Resource Server)**
- **Hibernate (JPA)**
- **Flyway (Migrations)**
- **PostgreSQL**
- **Maven**

---

## ⚙️ **Configuração do Ambiente**
### 🔧 **Variáveis de Ambiente**
Antes de rodar o serviço, configure as seguintes variáveis no **`application.yml`** ou no ambiente:

```yaml
server:
  port: 0  # Definido dinamicamente pelo Eureka

spring:
  application:
    name: resqueue-clinic

  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: ${KC_BASE_ISSUER_URL:http://localhost:9000}/realms/resqueue

  datasource:
    url: ${DATABASE_URL:jdbc:postgresql://localhost:5432/resqueue_clinic}
    username: ${DATABASE_USERNAME:admin}
    password: ${DATABASE_PASSWORD:admin}
    driver-class-name: org.postgresql.Driver

  jpa:
    database: postgresql
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect

  flyway:
    baseline-on-migrate: true
    enabled: true

springdoc:
  api-docs:
    enabled: true
    path: /clinic/v3/api-docs
  swagger-ui:
    enabled: true
    path: /docs
    config-url: /clinic/v3/api-docs/swagger-config
    urls:
      - name: clinic-service
        url: /clinic/v3/api-docs

eureka:
  client:
    fetch-registry: true
    register-with-eureka: true
    service-url:
      defaultZone: ${EUREKA_URL:http://localhost:8761/eureka/}
```

### 🔑 **Variáveis Explicadas**
| Variável                | Descrição |
|-------------------------|-----------|
| `KC_BASE_ISSUER_URL`    | URL base do **Keycloak** para autenticação JWT |
| `EUREKA_URL`            | URL do **Eureka Server** para registro do serviço |
| `DATABASE_URL`          | URL de conexão do **banco de dados PostgreSQL** |
| `DATABASE_USERNAME`     | Usuário do banco de dados |
| `DATABASE_PASSWORD`     | Senha do banco de dados |

---

## 🚀 **Executando o Projeto**
### **Rodando com Docker**
Uma imagem Docker já está disponível no **Docker Hub**:

```sh
docker pull rodrigobrocchi/resqueue-clinic:latest
docker run -p 8083:8083 \
  -e KC_BASE_ISSUER_URL=http://localhost:9000 \
  -e EUREKA_URL=http://localhost:8761/eureka \
  -e DATABASE_URL=jdbc:postgresql://localhost:5432/resqueue_clinic \
  -e DATABASE_USERNAME=admin \
  -e DATABASE_PASSWORD=admin \
  rodrigobrocchi/resqueue-clinic:latest
```

---

## 📄 **Documentação da API**
A documentação da API está disponível através do **Gateway do Resqueue**, que pode ser acessado no repositório:

🔗 **[ResQueue Gateway - GitHub](https://github.com/4ADJT/ResQueue-gateway)**
