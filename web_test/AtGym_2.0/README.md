#Dokumentation





##Mockups
##Use-Case-Diagramm
##Funktionale Anforderungen
##Nicht-funktionale Anforderungen
##Architektur
###Klassendiagramm
###Observer
##Datenbank
##Technologien
###HTML 5
HTML 5 wird verwendet, um die Grundstruktur der Website aufzubauen. Das bei HTML 5 neu eingeführte Tag <section> gibt der Website eine Struktur. 
Außerhalb der <section> befindet sich das Hintergrundbild und die Navigationsleiste, sowie der Footer. Der eigentliche Inhalt der einzelnen HTML-Seiten wird innerhalb der <section> 
abgebildet. Die Navigation befindet sich in dem dafür vorgesehenen Tag <nav> und der Footer in <footer>.
Für die Validierung von Inputfelder wird der Typ 'number' verwendet, um sicherzustellen, dass in bestimmten <input> Tags, wie z.B. in "myRoutine.html" nur Zahlen eingegeben werden.
In den HTML-Seiten für die Übungen, wird mithilfe von <select> und <option> eine 'drop-down list' erzeugt, um beim Hinzufügen von Übungen ein Wochentag auswählen zu können.

###CSS3
CSS3 stellt neben Bootstrap das Design der HTML-Seiten dar. Für Elemente, die sich wiederholen, wurden Klassen verwendet. Für einzelne Tags eine ID. Das Design für die Klassen und ID's
wird in der Datei "style.css" beschrieben.
Alle Größenangaben in CSS werden relativ (in Prozent) angegeben und das Attribut 'float' verwendet, um ein Responsive Web Design zu ermöglichen.
Media Query?
###Bootstrap
Bootstrap bietet eine Auswahl an Designelementen. Verwendet werden diese für die Navigationsleiste, Buttons, sowie Icons, wie z.B. der Stift-Icon in "aboutMe.html", um Daten zu bearbeiten.
Auch für die ausklappbaren Panels, in denen die Übungen aufgelistet werden, stellt Bootstrap eine Design Klasse zur Verfügung.
Um Bootstrap zu integrieren, muss zunächst die "bootstrap.js" Datei eingebunden werden. In dieser werden alle Bootstrap Komponente mit Javascript realisiert. 
Dadurch genügt es in den HTML-Seiten nur die benötigte Klasse anzugeben.
###JavaScript
###JQuery
###Ajax
###WebSockets
###JSON
##Weitere Ideen