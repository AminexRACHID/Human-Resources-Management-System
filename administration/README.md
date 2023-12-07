# Stagaire Service


### générer et envoyer une attestation de stage 

POST : http://localhost:8090/administration-service/api/administration/generateAttestation
```xml
{
    "nomPrenom" : "TOUZOUZ Adnane",
    "cin" : "m519",
    "poste" : "etudiant",
    "dateOccupation" : "2024-04-23",
    "nomEtablissement" : "fsm"
}