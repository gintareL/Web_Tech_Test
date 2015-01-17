#Dokumentation





##Die ersten Ideen und ihre Realisierung
Um das Design der Webapplication zu erstellen, wurden am Anfang vom Projekt Mockups entwickelt. Einige sind in der folgenden Tabelle zu sehen. 
Während der Entwicklung mussten ein Paar Änderungen vorgenommen werden. Das Ergebnis weicht aber von der ersten Idee nur ganz wenig ab. 
#
Mockups | Realisierung
------------ | -------------
![GitHub login](mockups/logIn.jpg) | ![GitHub loginUmsetzung](mockups/loginUmsetzung.jpg)
![GitHub home](mockups/home.jpg) | ![GitHub homeUmsetzung](mockups/homeUmsetzung.jpg)
![GitHub ourgym](mockups/ourgym.jpg) | ![GitHub ourgymUmsetzung](mockups/ourgymUmsetzung.jpg)
![GitHub mygym](mockups/mygym.jpg) | ![GitHub mygymUmsetzung](mockups/mygymUmsetzung.jpg)
![GitHub uebungen](mockups/uebungen.jpg) | ![GitHub uebungenUmsetzung](mockups/uebungenUmsetzung.jpg)
##Use-Case-Diagramm
##Funktionale Anforderungen
##Nicht-funktionale Anforderungen
##Architektur
###Klassendiagramm
![GitHub Klassendiagramm](Klassendiagramm.jpg)
###Observer
##Datenbank
Damit die Datenbank leicht zu pflegen und zu erweitern ist, wurde sie in der 3. Normalform entwicklt. 
Zugriff auf die Datenbank gelingt über JDBC. Um die Sicherheit der Webapplikation zu erhöhen, wurden die PreparedStatements benutzt.
Passwörter von User sind ebenfalls geschützt und werden verschlüßelt in die Datenbank gespeichert. Für die Verschlüsserung wurde Secure Hash Algorithm benutzt.
![GitHub atGymDB](atGymDB.jpg)
##Technologien
###HTML 5
###CSS3
###Bootstrap
###JavaScript
###JQuery
###Ajax
###WebSockets
###JSON
##Weitere Ideen