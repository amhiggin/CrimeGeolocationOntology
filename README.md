# CrimeGeolocationOntology
An OWL ontology application for querying crime geolocation information in Ireland between 2004-2016. Built using Apache Jena, OWL API and Protégé.
* Ontology can be loaded from file into the framework.
* SPARQL queries can also be loaded from file into the framework, each separated by newlines.
* A simple CLI allows users to execute any of the loaded SPARQL queries.

## Composition
* Ordnance Survey Ireland (OSI) Geohive Dataset: http://data.geohive.ie
* Irish 'Crimes at Garda Stations' Dataset: https://data.gov.ie/dataset/crimes-at-garda-stations-level-2010-2016

## Dependencies
* JRE 1.8.0_101
* Apache Jena 2.5.4
* ANSI Eclipse plugin: https://mihai-nita.net/2013/06/03/eclipse-plugin-ansi-in-console/

#County_and_ElectoralDivision_Application
* A simple Java application used to extract and organise coordinate data from both datasets.
* It is not needed for the operation of the CrimeGeolocationOntology. It is only included for illustrative purposes.
