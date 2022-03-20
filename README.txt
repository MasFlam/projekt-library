Projekt używa Gradle do kompilacji oraz wymaga co najmniej Javy 11.
W pliku createdb.sql znajduje się SQL do wygenerowania tabeli w bazie danych.
Strona internetowa otwiera się na porcie 8080.

Kompilacja i uruchomienie:
    gradle build
(albo gradlew.bat build jeżeli nie ma Pan Gradle'a)

    java -Ddb.user=USER -Ddb.pass=PASSWORD -Ddb.host=HOST -Ddb.port=PORT -Ddb.database=DATABASE -jar build/quarkus-app/quarkus-run.jar
(oczywiście USER itd. zastąpić faktycznymi danymi)
