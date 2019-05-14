# Praegus Fitnesse Appium

This project extends the Java fixtures provided by [hsac-fitnesse-fixtures](https://github.com/fhoeben/hsac-fitnesse-fixtures).
It adds the ability to test mobile (iOS, Android) and Windows applications using [appium](http://appium.io).

## Update 0.0.9

### Nieuwe features
- Scrollen gefixt in in ieder geval Android:
  - scroll to "place" werkt nu. Is wel traag. 
  - Nieuwe functies: scroll up; scroll down. Is wat sneller.
- Upgrade naar laatste fitnesse en hsac versies. 
- is element on screen eruit gehaald. Is functionaliteit die appium niet ondersteunt. 
 
### Known issues
- Scrollen en klikken samen in 1 operatie werkt niet. We lijken alleen xml te krijgen van appium van wat zichtbaar is. Moet nog geimplementeerd owrden.  