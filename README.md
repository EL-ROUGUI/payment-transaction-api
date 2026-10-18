# Payment Transaction API

API REST de gestion de transactions de paiement (projet démo).  
Développé avec Java 17 et Spring Boot 3.

## Architecture

- **Controller (`com.mehdi.payment.controller`)** : Expose les endpoints REST, valide les requêtes HTTP entrantes et retourne les réponses formatées avec les codes HTTP appropriés.
- **Service (`com.mehdi.payment.service`)** : Contient la logique métier, effectue les contrôles de validité et gère les transactions applicatives.
- **Repository (`com.mehdi.payment.repository`)** : Assure l'accès aux données et les opérations CRUD en s'appuyant sur Spring Data JPA.
- **Entity (`com.mehdi.payment.entity`)** : Représente le modèle de données persistant mappé sur la table de base de données.

## Prérequis

- Java 17 (JDK)
- Maven 3.9+
- Docker (optionnel)

## Lancement local

Pour démarrer l'application en local :

```bash
mvn spring-boot:run
```

L'API est accessible sur `http://localhost:8080`.

### Exemples de requêtes

#### 1. Créer une transaction (`POST /api/transactions`)

```bash
curl -X POST http://localhost:8080/api/transactions \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 250.75,
    "currency": "MAD",
    "merchant": "Marjane Market"
  }'
```

#### 2. Récupérer toutes les transactions (`GET /api/transactions`)

```bash
curl -X GET http://localhost:8080/api/transactions
```

#### 3. Récupérer une transaction par ID (`GET /api/transactions/{id}`)

```bash
curl -X GET http://localhost:8080/api/transactions/1
```

## Lancement avec Docker

Construction de l'image Docker multi-stage :

```bash
docker build -t payment-transaction-api .
```

Démarrage du conteneur :

```bash
docker run -p 8080:8080 payment-transaction-api
```

## Tests

Pour exécuter la suite complète de tests (7 tests unitaires, MockMvc et intégration) :

```bash
mvn test
```

## Intégration Continue (CI)

Le workflow GitHub Actions (`.github/workflows/ci.yml`) automatise la compilation et l'exécution des tests (`mvn -B package`) à chaque `push` et `pull_request` sur la branche `master`.
