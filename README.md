[![Maven Central](https://img.shields.io/maven-central/v/nl.praegus/toolchain-appium-fixtures.svg?maxAge=21600)](https://mvnrepository.com/artifact/nl.praegus/toolchain-appium-fixtures)

# Praegus Fitnesse Appium

This project extends the Java fixtures provided by [hsac-fitnesse-fixtures](https://github.com/fhoeben/hsac-fitnesse-fixtures).
It adds the ability to test mobile (iOS, Android) and Windows applications using [appium](http://appium.io).

To run tests you need Appium and an appropriate driver.

## Usage:
Add the dependency below to your project's pom.xml and add `nl.praegus.fitnesse.slim.fixtures` to your imports in FitNesse

```
<dependency>
    <groupId>nl.praegus</groupId>
    <artifactId>toolchain-appium-fixtures</artifactId>
    <version>0.0.22</version>
</dependency>
```

## Update 0.0.22
- Minor improvements for testing Android apps

## Update 0.0.12
- Terug op Java 8.

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
