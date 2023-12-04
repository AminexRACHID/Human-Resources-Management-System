# Offre Stage Service

### Add Intership

POST : http://localhost:8090/offre-stage/api/stage
```xml
{
    "title" : "Data Science",
    "type" : "stage preembauche",
    "duration" : 12,
    "startDate" : "2023-04-23",
    "remuneration" : true,
    "diploma" : "BAC+5",
    "description" : "blablabla"
}
```

### Apply Intership

POST : http://localhost:8090/offre-stage/api/stage/applyIntership
```xml
{
    "id" : 1,
    "stageId" : 2,
    "city" : "Meknes",
    "levelStudies" : "BAC+5",
    "linkedin": "http://teeeest.com",
    "email" : "amine.rcd.ar@gmail.com",
    "phone" : "06836719"
}
```

### Accept or Interview or Reject an Intership Apply

PUT : http://localhost:8090/offre-stage/api/stage/accept/1
PUT : http://localhost:8090/offre-stage/api/stage/interview/1
PUT : http://localhost:8090/offre-stage/api/stage/reject/1
