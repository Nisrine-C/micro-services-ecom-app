# Micro Services Bank App (Synchronous)

Microservices banking demo built with Spring Boot and Spring Cloud using synchronous REST communication through OpenFeign. Services discover each other via Eureka and share configuration from a Spring Cloud Config Server backed by the local `config-repo` folder.

## Services

- **config-service** (`:9999`): Spring Cloud Config Server pointing to `file:///C:/Users/HP/IdeaProjects/ebank-app-ms/config-repo`.
- **discovery-service** (`:8761`): Eureka server (no self-registration).
- **gateway-service** (`:8888`): Spring Cloud Gateway using Eureka discovery locator (dynamic routes, lower-case service ids). Config client disabled here.
- **customer-service** (`:8081`): Spring Data REST exposes customers at `/api/customers/**`; seeds sample customers; exposes config test endpoints `/testConfig1` and `/testConfig2`.
- **inventory-service** (`:8082`): Spring Data REST exposes products at `/api/products/**`; seeds sample products.
- **billing-service** (`:8083`): Uses Feign to call customer-service and inventory-service; aggregates bills and exposes `GET /bills/{id}` with enriched customer and product details.

## Tech Stack

- Spring Boot, Spring Data REST (H2 in-memory databases per service)
- Spring Cloud: Config Server/Client, Eureka Discovery, Gateway, OpenFeign
- Build: Maven (`mvnw`/`mvnw.cmd`)

## Synchronous Flow

- Gateway receives requests and forwards via service discovery (no static routes).
- Billing service uses OpenFeign clients `CustomerRestClient` and `ProductRestClient` to fetch customer/product data synchronously before returning a bill.

## Configuration Highlights

- Shared config in `config-repo/application.properties`:
  - `global.params.p1=555`, `global.params.p2=777`
  - `spring.h2.console.enabled=true`
  - Eureka client defaults (`http://localhost:8761/eureka`), management endpoints exposed (`management.endpoints.web.exposure.include=*`).
- Service-specific config (see `config-repo/*`):
  - **customer-service**: H2 `jdbc:h2:mem:customers-db`, `spring.data.rest.base-path=/api`, sample params `customer.params.x/y`.
  - **inventory-service**: H2 `jdbc:h2:mem:products-db`, base-path `/api`.
  - **billing-service**: H2 `jdbc:h2:mem:bills-db`, base-path `/api`.
- Each client service imports config with `spring.config.import=optional:configserver:http://localhost:9999` and registers with Eureka.

## Run Order (local)

Prereqs: JDK 17+, Maven wrapper, ports 9999/8761/8888/8081/8082/8083 free.

1. **Config Server**
   - `cd config-service`
   - `mvnw.cmd spring-boot:run`
2. **Discovery Server**
   - `cd discovery-service`
   - `mvnw.cmd spring-boot:run`
3. **Domain Services** (parallel ok after Eureka is up)
   - `cd customer-service && mvnw.cmd spring-boot:run`
   - `cd inventory-service && mvnw.cmd spring-boot:run`
   - `cd billing-service && mvnw.cmd spring-boot:run`
4. **Gateway**
   - `cd gateway-service`
   - `mvnw.cmd spring-boot:run`

Profiles: additional `*-dev.properties` / `*-prod.properties` exist in `config-repo` if you add profile activation (e.g., `--spring.profiles.active=dev`).

## Key Endpoints

- Customers (Spring Data REST): `GET http://localhost:8081/api/customers`, `GET http://localhost:8081/api/customers/{id}`
- Products (Spring Data REST): `GET http://localhost:8082/api/products`, `GET http://localhost:8082/api/products/{id}`
- Bill aggregation: `GET http://localhost:8083/bills/{id}` (fills customer and product details via Feign)
- Config smoke tests: `GET http://localhost:8081/testConfig1`, `GET http://localhost:8081/testConfig2`
- Gateway (discovery-based): e.g., `GET http://localhost:8888/CUSTOMER-SERVICE/api/customers` or lowercase id thanks to locator.

## Data Seeding

- `customer-service` seeds three customers on startup.
- `inventory-service` seeds five products on startup.
- `billing-service` seeds bills and line items by fetching all customers/products via Feign when both upstream services are running.

## Useful URLs

- Eureka dashboard: `http://localhost:8761`
- H2 consoles (enable in config): `http://localhost:<service-port>/h2-console`

## Troubleshooting

- If a service cannot fetch config, ensure config-service is running at `:9999` and the repo path matches your machine location.
- If billing-service fails at startup, verify customer-service and inventory-service are registered in Eureka and reachable.
- Clear/override ports if already in use, or adjust `server.port` in the service `application.properties`.
