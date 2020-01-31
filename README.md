This is Racing-API service E2E tests project

**Built using Technologies** 

Java 11  
Cucumber-JVM  
Maven  
JsonPath  
GSON  
Rest Assured 

**How to run tests?**

`mvn test` - runs e2e tests 

`mvn test verify` - runs e2e tests and produces cucumber html report in the root folder *target/cucumber-html-reports* folder named *overview-features.html* file 

Maven surefire plugin runs the main test file - RunCucumberTest.java which takes cucumber options to find the tests that needs to run   


**Cucumber options**

Below cucumber options are specified 

Tags - @e2e - run all test scenarios marked with tag @e2e




