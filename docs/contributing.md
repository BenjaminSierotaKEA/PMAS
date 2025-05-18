# Contributing to PMAS

Tak fordi du overvejer at bidrage til **Project Manager for Alpha Solutions (PMAS)**.  
Dette dokument guider dig igennem hvordan man kommer i gang som ny udvikler, og hvordan man bedst muligt arbejder sammen med resten af teamet. 

Velkommen til Projektet!

---

## Indholdsfortegnelse
- [Code of Conduct](#code-of-conduct)
- [Hvordan du kan bidrage](#hvordan-du-kan-bidrage)
  - [Spørgsmål](#spørgsmål)
  - [Opret en Issue](#opret-en-issue)
  - [Pull Requests](#pull-requests)
  - [Beskyttet Main Branch](#beskyttet-main-branch)
- [Branches og Konventioner](#branches-og-konventioner)
- [Test og CI/CD](#test-og-cicd)
- [Pull Request Tjekliste](#pull-request-tjekliste)
- [Licens](#licens)

---

## Code of Conduct

> Vi opfører os pænt over for hinanden :) .

---

## Hvordan du kan bidrage

### Spørgsmål
Hvis du er i tvivl om noget, så spørg! Det er bedre at få afklaring end at gætte.

### Opret en Issue
Har du fundet en fejl, en forbedringsidé, eller noget andet?  
Opret en **Issue** med en kort titel og en beskrivelse. Tag det gerne med labels som `bug`, `enhancement` eller `question`.

### Pull Requests
Hvis du har lavet en forbedring eller rettelse, så lav en pull request. Beskriv tydeligt hvad du har ændret og hvorfor.

### Beskyttet Main Branch
Vi har Github Rules slået til, der gør at man ikke kan merge in pull request uden at få mindst et review.
Hold øje med pull request og kig koden igennem før du merger.

---

## Branches og Konventioner

- Brug feature branches: `feature/mit-feature-navn`
- Følg eksisterende pakkestruktur (`controller`, `model`, `repository`, `service`, `util`)
- Brug camelCase til variabler og metoder
- 4-space indryk

---

## Test og CI/CD

- Vi skriver tests til det vi laver – både unit og integration hvor det giver mening.
- Brug `@Transactional` i tests hvor det kræves.
- Når du laver en pull request, vil GitHub Actions automatisk tjekke at byg og tests virker.
- Qodana bruges til statisk analyse af kodekvalitet. Den fortæller dig hvis noget går galt eller skal forbedres.

---

## Pull Request Tjekliste

Før du laver en PR, så dobbelttjek gerne:

- [ ] Har du skrevet tests?
- [ ] Har du skrevet kommentarer?
- [ ] Har du ikke ødelagt noget der virkede før?
- [ ] Har du beskrevet ændringerne?
- [ ] Har du brugt en fornuftig branch?

---

## Licens

Dette projekt er udviklet som en del af et eksamensprojekt på KEA og må ikke bruges kommercielt uden tilladelse.

---

## No Brown Eminems
Hvis du læser dette, så godt gået! og tak fordi du faktisk læste vores dokumentation :)