RestTemplate: used for simplicity and stability when calling external HTTP APIs (FastBank and SolidBank). No need for reactive stack in this context.
MapStruct; for clean and fast mapping between DTOs and internal models, reducing boilerplate code.
Lombok: to reduce repetitive code (getters, setters, constructors).

The external bank APIs are called asynchronously 
to avoid slow service from delaying the full response -> return offer as soon as it available;

BE port 8080
curl to test

curl -X POST localhost:8080/application \
  -H "Content-Type: application/json" \
  -d '{
    "phoneNumber": "+37126000000",
    "email": "john.doe@klix.app",
    "monthlyIncome": 150.0,
    "monthlyCreditLiabilities": 300,
    "monthlyExpenses": 10.0,
    "dependents": 0,
    "agreeToDataSharing": true,
    "amount": 150.0,
    "status": "MARRIED",
    "agreeToBeScored": true
  }'
