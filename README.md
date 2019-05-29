# Praegus Fitnesse Appium

This project extends the Java fixtures provided by [hsac-fitnesse-fixtures](https://github.com/fhoeben/hsac-fitnesse-fixtures).
It adds the ability to test mobile (iOS, Android) and Windows applications using [appium](http://appium.io).

## Update 0.0.11
- Build wordt vanaf nu gedaan met OpenJDK 11. 

## Update 0.0.10
- Scroll to er toch uit gehaald; werkte te instabiel.
- Nieuwe functies toegevoegd: scroll down to en scroll up to. Simpelere implementatie die in principe hetzelfde doet als scroll to.
- Dingen die te maken hebben met window size specifiek gemaakt voor Windows.
- Dingen die te maken hebben met rechts klikken, control klikken en shift klikken specifiek gemaakt voor Windows.

## Update 0.0.9

### Nieuwe features
- Scrollen gefixt in in ieder geval Android:
  - scroll to "place" werkt nu. Is wel traag. 
  - Nieuwe functies: scroll up; scroll down. Is wat sneller.
- Upgrade naar laatste fitnesse en hsac versies. 
- is element on screen eruit gehaald. Is functionaliteit die appium niet ondersteunt. 
 
## Known issues

### Android
- Scrollen en klikken samen in 1 operatie werkt niet. We lijken alleen xml te krijgen van appium van wat zichtbaar is. Moet nog geimplementeerd worden.

### Windows
- normalized value of in
- number of times is visible
- scroll to
- value of in
- wait / wait long
