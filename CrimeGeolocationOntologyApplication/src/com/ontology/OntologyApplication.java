package com.ontology;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;

public class OntologyApplication {
	// TODO @Amber update with final ontology name
	public static String ontologyName = "test.owl";
	public static com.hp.hpl.jena.ontology.OntModel ontologyModel = null;
	public static List<String> questionsToAskOntology = new ArrayList<String>();

	public static boolean running;
	private static List<Query> queries = new ArrayList<Query>();

	public static void main(String args[]) {
		// Setup UI
		print(OntologyConstants.WELCOME_MESSAGE);
		print(OntologyConstants.INFO_ABOUT_ONTOLOGY);
		print(OntologyConstants.SECTION_BREAK);

		try {
			print("Reading ontology model into the application...");
			ontologyModel = ReadOntologyModel.loadAllClassesOnt(ontologyName);
		} catch (Exception e) {
			if(e instanceof FileNotFoundException){
				print(OntologyConstants.ERROR_READING_FILE);
			} else {
				print("Couldn't read ontology model - exiting...");
			}
			return;
		}

		// successfully read the ontology model
		running = true;
		ReadOntologyModel.populateListOfStringQueriesFromFile();
		queries = ReadOntologyModel.loadAllQueriesFromStringList(ontologyModel);
		questionsToAskOntology = ReadOntologyModel.populateListOfQuestionsToDisplay();

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
			print(OntologyConstants.SECTION_BREAK);
			print(String.format(OntologyConstants.PRESENT_USER_OPTIONS, questionsToAskOntology.get(0),
					questionsToAskOntology.get(1), questionsToAskOntology.get(2), questionsToAskOntology.get(3),
					questionsToAskOntology.get(4), questionsToAskOntology.get(5)));
			print(OntologyConstants.PRESS_X_TO_EXIT);
			String input = inputScanner.nextLine();
			ResultSet results = executeQueryBasedOnUserInput(input);
			if (results != null) {
				outputResultsToConsole(results);
			} else {
				if(running == true){
					print("Couldn't process the results of the query.");
				} else {
					break;
				}
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
			print("Query is null: cannot execute.");
			return null;
		}
		QueryExecution qexec = QueryExecutionFactory.create(query, ontologyModel);
		ResultSet results = qexec.execSelect();
		qexec.close();
		return results;
	}

	public static void outputResultsToConsole(ResultSet results) {
		ResultSetFormatter.out(System.out, results);
	}

	public static void print(String message) {
		System.out.println(OntologyConstants.BLACK_BACKGROUND + OntologyConstants.GREEN_BOLD + message);
	}

}
