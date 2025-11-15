# 🎟️ Coupon API — DDD + Arquitetura Hexagonal

Microserviço responsável pela criação, consulta, atualização, publicação e resgate de cupons promocionais.  
Desenvolvida com **Java 21**, **Spring Boot**, **DDD**, **SOLID** e princípios de **Arquitetura Hexagonal**.

---

## 🧱 Arquitetura

A aplicação segue uma estrutura em camadas, com foco em isolamento e clareza entre responsabilidades:

```
domain/ → Regra de negócio pura (Entities, Aggregates, VOs, Validators)

application/ → Use Cases, Persistência, Controllers, DTOs, Mappers

infrastructure/ → Configurações, Testcontainers

tests/ → Testes de Domínio, Use Cases e E2E
```


---

## 🚀 Funcionalidades

- Criar cupom  
- Atualizar cupom  
- Buscar cupom por ID  
- Buscar cupom por código  
- Publicar cupom  
- Resgatar cupom 

---

## 🔗 Endpoints

| Método | Endpoint                  | Descrição              |
|--------|---------------------------|------------------------|
| POST   | `/coupon`                 | Criar cupom           |
| GET    | `/coupon/{id}`            | Buscar por ID         |
| GET    | `/coupon/code/{code}`     | Buscar por código     |
| PUT    | `/coupon/{id}`            | Atualizar cupom       |
| POST   | `/coupon/{id}/publish`    | Publicar cupom        |
| POST   | `/coupon/{id}/redeem`     | Resgatar cupom        |

---

## 🧩 Modelo de Domínio

A entidade `Coupon` concentra todas as regras de negócio:

- Cupom nasce **não publicado** e **não resgatado**
- Não pode ser publicado se:
  - estiver expirado  
  - possuir desconto inferior a **0.5**
- Não pode ser resgatado se:
  - não estiver publicado  
  - estiver expirado 
---

## 🧠 Casos de Uso

- `CreateCoupon`
- `GetCouponByIdUseCase`
- `GetCouponByCodeUseCase`
- `UpdateCouponUsecase`
- `PublishCouponUseCase`
- `RedeemCouponUseCase`

Cada use case:
- aplica regra de aplicação  
- chama repositório  
- retorna DTO via OutputMapper  

---

## 🧪 Testes

A aplicação inclui testes completos divididos em três camadas:

### 1️⃣ Testes de Domínio
Garantem que a entidade `Coupon` funciona corretamente:

- Criação  
- Publicação  
- Resgate  
- Expiração  
- Rebuild a partir dos dados persistidos  

### 2️⃣ Testes de Use Case
Executam cenários reais usando **Testcontainers + PostgreSQL**.

### 3️⃣ Testes E2E (Controller)
Validação completa via **RestAssured**, incluindo:

- Fluxo de criação  
- Fluxo de atualização  
- Publicar  
- Resgatar  
- Buscar por ID e código  
- Erros de validação e regras de negócio  

---

## 🛠️ Tecnologias Utilizadas

- Java 21  
- Spring Boot  
- JPA / Hibernate  
- PostgreSQL  
- Testcontainers  
- RestAssured  
- JUnit
- FluentValidator  
- Lombok  
- Docker  
- Swagger/OpenAPI  

---

## ▶️ Como Rodar

### Detalhes da aplicação:
Executar Aplicação:
```bash
mvn spring-boot:run
```

Executar testes:
```bash
mvn clean test
```
(O Testcontainers irá subir um PostgreSQL automaticamente)

📄 Exemplo de Cupom (JSON)
```bash
{
  "id": "uuid",
  "code": "CUPOM10",
  "description": "cupom de desconto",
  "discountValue": 10.0,
  "expirationDate": "2025-01-10",
  "published": false,
  "redeemed": false
}
```
