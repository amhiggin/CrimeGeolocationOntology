package com.ontology;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.hp.hpl.jena.query.Query;

public class OntologyApplication {
	// TODO @Amber update with final ontology name
	public static String ontologyName = "crime_geolocation_ontology.owl";
	public static com.hp.hpl.jena.ontology.OntModel ontology = null;
	public static List<String> questionsToAskOntology = new ArrayList<String>();

	public static boolean running;
	private static List<Query> queries = new ArrayList<Query>();

	public static void main(String args[]) {
		print(OntologyConstants.WELCOME_MESSAGE);
		print(OntologyConstants.INFO_ABOUT_ONTOLOGY);

		try {
			print("Reading ontology model into the application...");
			ontology = ReadOntologyModel.loadAllClassesOnt(ontologyName);
		} catch (FileNotFoundException e) {
			print(OntologyConstants.ERROR_READING_FILE);
			return;
		}

		// successfully read the ontology model
		running = true;
		queries = ReadOntologyModel.loadAllQueries(ontology);
		questionsToAskOntology = ReadOntologyModel.populateListOfQuestions();

		while (running) {
			try {
				presentOptionsToUser();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		print(OntologyConstants.THANK_YOU);

	}

	public static void presentOptionsToUser() {
		Scanner inputScanner = new Scanner(System.in);
		while (running) {
			print(String.format(OntologyConstants.PRESENT_USER_OPTIONS, questionsToAskOntology.get(0),
					questionsToAskOntology.get(1), questionsToAskOntology.get(2), questionsToAskOntology.get(3),
					questionsToAskOntology.get(4), questionsToAskOntology.get(5)));
			print(OntologyConstants.PRESS_X_TO_EXIT);
			String input = inputScanner.nextLine();
			ResultSet results = executeQueryBasedOnUserInput(input);
			if (results != null) {
				outputResultsToConsole(results);
			} else {
				print("Couldn't process the results of the query.");
			}
		}
		inputScanner.close();
	}

	private static ResultSet executeQueryBasedOnUserInput(String input) {
		Query query = null;
		switch (input) {
		case "1":
			query = queries.get(0);
			break;
		case "2":
			query = queries.get(1);
			break;
		case "3":
			query = queries.get(2);
			break;
		case "4":
			query = queries.get(3);
			break;
		case "5":
			query = queries.get(4);
			break;
		case "6":
			query = queries.get(5);
			break;
		case "x":
			print("'x' entered. Exiting application.");
			running = false;
			return null;
		default:
			print("Invalid input: " + input);
			return null;
		}
		return executeSparqlQuery(query);
	}

	public static ResultSet executeSparqlQuery(Query query) {
		if (query == null) {
			print("Query is null: cannot execute");
			return null;
		}
		// else execute the query
		// FIXME @Amber
	}

	public static void outputResultsToConsole(Results results) {
		// TODO @Amber implement
	}

	public static void print(String message) {
		System.out.println(message);
	}

}
