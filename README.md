# Books
## Inleiding
De 'Books' applicatie is een eenvoudige toepassing die is ontwikkeld om boeken op te zoeken in een uitgebreide API. Met deze applicatie kan de gebruiker ook lijsten maken om boeken aan toe te voegen.

## Pagina's
### Home
Dit is de startpagina waar gebruikers via de tekstbox boeken kunnen zoeken op basis van hun titels. Wanneer er op een boek wordt geklikt, navigeren we naar de 'Book Details'-pagina.
### Book Details
Op deze pagina kunnen gebruikers meer informatie krijgen over het geselecteerde boek. Ze kunnen hier ook het boek toevoegen aan een specifieke lijst.
### Book Lists
Hier krijgen gebruikers een overzicht van hun boeklijsten. Om een boeklijst aan te passen, kunnen ze op het optie-icoontje drukken aan de rechterzijde van de lijst. Hier hebben ze de mogelijkheid om de lijstnaam aan te passen of de lijst te verwijderen. Als er op een boeklijst wordt geklikt, navigeren ze naar de 'Book List Details' pagina.
### Book List Details
Op deze pagina zien gebruikers de naam van de lijst en de verschillende boeken in die lijst. Als er op een boek wordt geklikt, navigeren ze weer naar de 'Book Details' pagina van dat specifieke boek.

## API
***OpenLibrary***: https://openlibrary.org/developers/api

## Documentatie
De documentatie van deze applicatie kan worden gegenereerd met het Gradle-commando **`dokkaHtml`** aan de hand van Dokka.