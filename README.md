# Obilet UI Test Automation


## Project Features
- **Test Framework**: Selenium WebDriver + Cucumber BDD
- **Build Tool**: Maven
- **Java Version**: 11
- **Browser**: Chrome

## Requirements
- Java 11
- Maven 3.6
- Chrome Browser

## Install Dependencies:
```bash
mvn clean install
```
## Running Tests
### Run all tests:
```bash
mvn clean test
```
### Run specific tags:
```bash
mvn clean test -Dcucumber.filter.tags="@bus"
```
