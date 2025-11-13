# Coupon API (CRUD completo em Spring Boot + DDD + Arquitetura Hexagonal)

API responsável pela criação, consulta, atualização, publicação e resgate de cupons promocionais.  
Desenvolvida utilizando Java 21, Spring Boot 3, DDD, SOLID e princípios de Arquitetura Hexagonal.

---

## Arquitetura

A aplicação segue uma organização em camadas com isolamento claro entre domínio, aplicação e infraestrutura:
---

## Principais Funcionalidades

- Criar cupom
- Atualizar cupom
- Buscar cupom por ID
- Buscar cupom por código
- Publicar cupom
- Resgatar cupom
- Validações completas do domínio

---

## Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST   | `/coupon` | Cria um cupom |
| GET    | `/coupon/{id}` | Busca por ID |
| GET    | `/coupon/code/{code}` | Busca por código |
| PUT    | `/coupon/{id}` | Atualiza |
| POST   | `/coupon/{id}/publish` | Publica o cupom |
| POST   | `/coupon/{id}/redeem`  | Resgata o cupom |

---

## Modelo de Domínio

A entidade `Coupon` contém todas as regras:

- Cupom inicia como **não publicado** e **não resgatado**  
- Não pode ser publicado se:
  - estiver expirado
  - tiver desconto inferior a 0.5  
- Não pode ser resgatado se:
  - não estiver publicado
  - estiver expirado  
- Todas as operações chamam automaticamente `selfValidate()`

Value Objects:

- `CouponCode` — normaliza e valida códigos
- `CouponDescription` — normaliza textos
- `CouponData` — agrega dados essenciais

---

## Casos de Uso

- `CreateCoupon`
- `GetCouponByIdUseCase`
- `GetCouponByCodeUseCase`
- `UpdateCouponUsecase`
- `PublishCouponUseCase`
- `RedeemCouponUseCase`

Cada caso de uso contém:
- regra de aplicação
- chamadas ao repositório
- mapeamento para DTO

---

## Testes

A aplicação possui testes em três níveis:

### 1. Testes de Domínio  
Validam comportamento da entidade `Coupon`:

- criação
- publicação
- expiração
- resgate
- rebuild

### 2. Testes de Use Case  
Executam cenários reais usando banco via Testcontainers.

### 3. Testes de Controller (E2E)  
Utilizam RestAssured para validar endpoints reais.

---

## Tecnologias

- Java 21  
- Spring Boot 3  
- JPA / Hibernate  
- PostgreSQL  
- Testcontainers  
- RestAssured  
- JUnit 5  
- FluentValidator  
- Lombok  

---

## Como Rodar

### Iniciar aplicação:
```bash
mvn spring-boot:run

Rodar testes:
mvn clean test


Testcontainers irá subir o PostgreSQL automaticamente.

Estrutura esperada do cupom
{
  "id": "uuid",
  "code": "CUPOM10",
  "description": "cupom de desconto",
  "discountValue": 10.0,
  "expirationDate": "2025-01-10",
  "published": false,
  "redeemed": false
}
