# Project Manager for Alpha Solutions (PMAS)

---

## Indholdsfortegnelse
- [Oversigt](#oversigt)
- [Funktionalitet](#funtionalitet)
- [Teknologier](#teknologier)
- [Kom I Gang](#kom-i-gang)
- [Kontakt](#kontakt)

---

## Oversigt
PMAS er udviklet af fire studerende fra Københavns Erhvervsakademi (KEA) som et eksamensprojekt i 2. semester.

Projektet er blevet formidlet til de studerende som et eksternt udviklingsteam der er blevet hyret til at lave et internt 
projektstyringsprogram for Alpha Solutions, som i denne kontekst er klienten.
Alpha Solutions har efterspurgt at værktøjet skal understøtte funktionalitet for agile arbejdsmetoder. 
Programmet skulle yderligere kunne bruges til at danne overblik over projekter ved at kategorisere dem ned i "Subprojekts" og "Tasks", og videre 
beregne arbejdsbyrder og regne op med tidsbudgeter.
Programmet er yderligere blevet sat det krav at det skal have en intuitiv og brugervenlig brugerflade.

---

## Funtionalitet
For at kunne udnytte programmets funktionalitet, skal en Admin bruger tilføje dig til databasen. 
Dette kan de gøre gennem deres lukkede side, hvor de yderligere kan ændre bugerinformation og slette forældede brugere.

Når du er blevet oprettet i systemet kan du nu tilgå webappens log-in side.
Ved at indtaste de korrekte brugeroplysninger vil du blive taget til et endpoint, alt efter hvilke rettigheder din bruger har.
som almen bruger, vil du blive taget til din personlige brugerside, hvor du har en oversigt over arbejdsopgaver, også kaldt "tasks", der er blevet tildelt dig.
Hver af disse tasks er klikbare, og vil tage dig til en side med detaljeret information om opgaverne.
Opgaverne er yderligere blevet sorteret så de mest hastende opgaver ligger øverst i rækken af opgaver, en bruger kan begynde at arbejde på.
Navigation til diverse sider mulig gennem webappen navigationsbar, og gør det muligt at se en avanceret oversigt over subprojekter og tasks som en bruger er blevet tildelt.
En bruger kan afslutte deres session ved at trykke "Log Out" gennem navigationsbaren.

En bruger kan også komme i form af at "Project Manager", med udvidede rettigheder til at oprette, ændre og slætte elementer på systemet.
Brugere med Project Manager rollen har derfor også flere muligheder for at se oversigter over projekter, subprojekter og tasks.

Nedstsående er et link til den deployede version af programmet:
[Link](https://pmas5-dfcxeqekc2awech2.francecentral-01.azurewebsites.net/)

---

## Teknologier
#### Programmet er blevet udviklet med følgende teknologier:

* **Java (JDK 21)** - Det primære programmeringssprog.
* **IntelliJ IDEA 2024.2.5** (Ultimate Edition) - Den anvendte IDE.
* **Spring Boot Framework 3.4.5** - Framework anvendt til backend og applikationsarkitektur
* **Spring DevTools 3.4.5** - Bruges til live reload.
* **Apache Maven Wrapper 3.3.2** -  Til håndtering af afhængigheder og build-processen.
* **MySQL 9.2 + MySQL Workbench 8.0 CE** - Databaseløsning til produktion og udvikling.
* **MySQL Connector** - JDBC-driver til MySQL-integration.
* **h2Database 2.3.232** - In-memory database til integrationstest.
* **Thymeleaf Template Engine 3.1.3** - Til dynamisk HTML rendering.
* **Mockito 5.17.0** - Til test af applikationen.
* **Qodana Cloud** - Til statisk kodeanalyse.
* **Microsoft Azure** - Anvendt til deloyment af applikationen.
* **GitHub Actions** - CI/CD

---

## Kom I Gang
For at få programmet til at køre på jeres makskine, er det vigtigt at gennemgå den nedstående information:

### Softwaremæssige Forudsætninger
Programmet anvender de følgende teknologier og platforme, og er essentielle for at programmet fungerer:

* **Java JDK 21 eller nyere**
* **Maven**
* **Microsoft Azure** 
* **Qodana**
* **Github**



### Installering 
1. Klon dette [GitHub Repository](https://github.com/BenjaminSierotaKEA/PMAS)
2. Opret database ved at køre DDL fra: src/main/resources/database 
Kør eventuelt DML for at oprette en af hver type bruger.
3. Opret Environment Variables, for både Development og Production. Disse Environment variabler bruger følgende navnekonvention:

Lokalt, på jeres IDE sættes environment variables for development Spring Profile:
<code>
<br>DEV_DATABASE_URL = jdbc:mysql://localhost:3306/pmasdatabase 
<br>DEV_USERNAME = [Database workspace username]
<br>DEV_PASSWORD = [Database workspace password]
</br>
</code>
På Github Secrets Sættes environment variables for production Spring Profile:
<code>
   <br>PROD_DATABASE_URL = [Genereret URL fra Azure Server]
   <br>PROD_USERNAME = [Azure Server Username]
   <br>PROD_PASSWORD = [Azure Server password]
</code>

4. Programmet kan nu køres lokalt of fungere med CD Continues Deployment.

---

## Kontakt

Yderligere information og spørgsmål kan findes ved at kontakte os på:
[Besi0002@stud.kea.dk](mailto:Besi0002@stud.kea.dk),
[Jajo0004@stud.kea.dk](mailto:Jajo0004@stud.kea.dk),
[Macl0001@stud.kea.dk](mailto:Macl0001@stud.kea.dk) eller
[Joha0005@stud.kea.dk](mailto:Jajo0004@stud.kea.dk).


