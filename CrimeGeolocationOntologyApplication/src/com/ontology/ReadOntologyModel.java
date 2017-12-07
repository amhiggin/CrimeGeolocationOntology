package com.ontology;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;

public class ReadOntologyModel {

	public static String sparqlQueryFilename = "queries.txt";
	public static List<String> queriesAsStrings = new ArrayList<String>();

	// Heavily based off sample code provided in the Jena Tutorials, CS7IS1
	// TODO remove unnecessary printlns after debugging
	public static OntModel loadAllClassesOnt(String localSource) throws FileNotFoundException {
		OntModel model = com.hp.hpl.jena.rdf.model.ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		model.read(new FileInputStream(localSource), null); // , "RDF/XML");

		Iterator<?> RootClasses = model.listHierarchyRootClasses();

		while (RootClasses.hasNext()) {
			String RootclassSTR = RootClasses.next().toString();
			if (RootclassSTR.indexOf("semanticweb") == -1)
				break;
			print("ROOTCLASS: " + RootclassSTR);
			OntClass query = model.getOntClass(RootclassSTR);

			for (Iterator<?> i = query.listSubClasses(); i.hasNext();) {
				OntClass c = (OntClass) i.next();
				print("                SubClass: " + c);
			}
		}
		if (model != null) {
			print("Successfully loaded ontology model");
		}
		return model;
	}

	public static List<String> populateListOfStringQueriesFromFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(sparqlQueryFilename));
			String line;
			while ((line = br.readLine()) != null) {
				queriesAsStrings.add(line);
			}
		} catch (Exception e) {
			print(OntologyConstants.ERROR_READING_FILE);
		}
		return queriesAsStrings;
	}

	public static Query convertStringToQuery(String queryString, OntModel ontologyModel) {
		Query query = QueryFactory.create(queryString);
		return query;
	}

	public static List<String> populateListOfQuestionsToDisplay() {
		List<String> questions = new ArrayList<>();
		questions.add(OntologyConstants.FIRST_QUESTION);
		questions.add(OntologyConstants.SECOND_QUESTION);
		questions.add(OntologyConstants.THIRD_QUESTION);
		questions.add(OntologyConstants.FOURTH_QUESTION);
		questions.add(OntologyConstants.FIFTH_QUESTION);
		questions.add(OntologyConstants.SIXTH_QUESTION);
		return questions;
	}

	public static void print(String message) {
		System.out.println(OntologyConstants.BLACK_BACKGROUND + OntologyConstants.GREEN_BOLD + message);
	}
}
