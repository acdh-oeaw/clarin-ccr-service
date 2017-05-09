## clarin-ccr-service

clarin-ccr-service is java library used to cache concepts from [CLARIN Concept Registry](https://www.clarin.eu/ccr) localy. The motivation was to make a function that translates concept URI into name (prefLabel) using [OpenSKOS API](http://openskos.org/api) when processing [CMDI](https://www.clarin.eu/content/component-metadata) records and profiles. Since there are many records and concepts are repeating caching was necessary and the simple mapping functionality has grown into a library. 

## Prerequisites

* Java 8
* Maven

## Getting started

Clone or download and build the library with maven using command:
```
mvn install
```

Create dependency in your pom file or add the jar file to the classpath

```
	<groupId>ac.at.acdh.cmdi</groupId>
			<artifactId>cmdi-ccr-service</artifactId>
			<version>{CURRENT VERSION}</version>
	</dependency>	
```

## Example

To create CCRservice object use following code:
```
ICCRService ccr = CCRServiceFactory.getCCRService();
```
Factory returns singleton object that is initialized with all concepts from the CCR. Also, once per hour factory "refreshes" the object in order to provide long running services with up-to-date values. Data is parsed with [gson library](https://github.com/google/gson). 

ICCRService interface has two methods:
 * CCRConcept getConcept(String uri)
 * Collection<CCRConcept> getAll()
 
CCRConcept is a POJO class with following properties:
 * concept URI
 * prefLabel and
 * status (approved, candidate, expired) 
 
To get concept name (prefLabel) from the URI use:
```
...
//an example for "licence URL" concept
String uri = "https://openskos.meertens.knaw.nl/ccr/api/find-concepts/CCR_C-6586_2c79d86a-5a75-0890-d407-7d9cb86b9beb"
ccr.getConcept(uri).getPrefLabel();
...
```

## License

This project is licensed under the Apache License, Version 2.0 - see the [LICENSE](LICENSE) file for details

## Acknowledgments

Many thanks to CLARIN core development team for support

