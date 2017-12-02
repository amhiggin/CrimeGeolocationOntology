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
	private static List<String> queriesAsStrings = new ArrayList<String>();

	public static void main(String args[]) {
		// Setup UI
		printGreenText(OntologyConstants.WELCOME_MESSAGE);
		printGreenText(OntologyConstants.INFO_ABOUT_ONTOLOGY);
		printGreenText(OntologyConstants.SECTION_BREAK);

		try {
			printGreenText("Reading ontology model into the application...");
			ontologyModel = ReadOntologyModel.loadAllClassesOnt(ontologyName);
		} catch (Exception e) {
			if (e instanceof FileNotFoundException) {
				printRedText(OntologyConstants.ERROR_READING_FILE);
			} else {
				printRedText("Couldn't read ontology model - exiting...");
			}
			return;
		}

		// successfully read the ontology model
		running = true;
		queriesAsStrings = ReadOntologyModel.populateListOfStringQueriesFromFile();
		questionsToAskOntology = ReadOntologyModel.populateListOfQuestionsToDisplay();

		while (running) {
			try {
				presentOptionsToUser();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		printGreenText(OntologyConstants.THANK_YOU);

	}

	public static void presentOptionsToUser() {
		Scanner inputScanner = new Scanner(System.in);
		while (running) {
			printGreenText(OntologyConstants.SECTION_BREAK);
			printNeutralText(String.format(OntologyConstants.PRESENT_USER_OPTIONS, questionsToAskOntology.get(0),
					questionsToAskOntology.get(1), questionsToAskOntology.get(2), questionsToAskOntology.get(3),
					questionsToAskOntology.get(4), questionsToAskOntology.get(5)));
			OntologyConstants.printAllCrimes();
			printGreenText(OntologyConstants.PRESS_X_TO_EXIT);
			String input = inputScanner.nextLine();
			ResultSet results = executeQueryBasedOnUserInput(input, inputScanner);
			if (results != null) {
				outputResultsToConsole(results);
			} else {
				if (running == true) {
					printRedText("Couldn't process the results of the query.");
				} else {
					break;
				}
			}
		}
		inputScanner.close();
	}

	// FIXME: when the model is completed, review the order in which the
	// String.formatting is happening in the queries
	private static ResultSet executeQueryBasedOnUserInput(String selectedQuery, Scanner inputScanner) {
		String queryString = null;
		String county = null, specificCrime = null, timePeriod = null;
		switch (selectedQuery) {
		case "1":
			// "How many stations are there in a specified county?";
			queryString = queriesAsStrings.get(0);
			System.out.println("Enter the county: ");
			county = inputScanner.nextLine();
			if (OntologyConstants.ALL_COUNTIES.contains(county)) {
				queryString = String.format(queryString, county);
			} else {
				queryString = null;
			}
			break;
		case "2":
			// "What is the most common type of crime in a specified county in a
			// specified year: damage to the person, or damage to property?";
			queryString = queriesAsStrings.get(1);
			System.out.println("Enter the county: ");
			county = inputScanner.nextLine();
			System.out.println("Enter the year of interest: ");
			timePeriod = inputScanner.nextLine();
			if (OntologyConstants.ALL_COUNTIES.contains(county) && (checkValidYearEntered(timePeriod))) {
				queryString = String.format(queryString, county, timePeriod);
			} else {
				queryString = null;
			}
			break;
		case "3":
			// "On average, how many crimes are committed in a specified county
			// per year?";
			queryString = queriesAsStrings.get(2);
			System.out.println("Enter the county: ");
			county = inputScanner.nextLine();
			if (OntologyConstants.ALL_COUNTIES.contains(county)) {
				queryString = String.format(queryString, county);
			} else {
				queryString = null;
			}
			break;
		case "4":
			// "In which year did a specified county have its highest crime
			// rate?";
			queryString = queriesAsStrings.get(3);
			System.out.println("Enter the county: ");
			county = inputScanner.nextLine();
			if (OntologyConstants.ALL_COUNTIES.contains(county)) {
				queryString = String.format(queryString, county);
			} else {
				queryString = null;
			}
			break;
		case "5":
			// "Which county saw the biggest rise in a specific crime type
			// over the period 2004-2016?";
			queryString = queriesAsStrings.get(4);
			System.out.println("Enter the crime (e.g. burglary, fraud): ");
			specificCrime = inputScanner.nextLine();
			if (OntologyConstants.ALL_CRIMES.contains(specificCrime)) {
				queryString = String.format(queryString, specificCrime);
			} else {
				queryString = null;
			}
			break;
		case "6":
			// "Which county had the lowest number of a specific crime in a
			// given year?";
			queryString = queriesAsStrings.get(5);
			System.out.println("Enter the crime (e.g. burglary, fraud): ");
			specificCrime = inputScanner.nextLine();
			System.out.println("Enter the year of interest: ");
			timePeriod = inputScanner.nextLine();
			if (OntologyConstants.ALL_CRIMES.contains(specificCrime) && (checkValidYearEntered(timePeriod))) {
				queryString = String.format(queryString, specificCrime, timePeriod);
			} else {
				queryString = null;
			}
			break;
		case "x":
			printGreenText("'x' entered. Exiting application.");
			running = false;
			return null;
		default:
			printRedText("Invalid input: " + selectedQuery);
			return null;
		}
		return executeSparqlQuery(queryString);
	}

	public static ResultSet executeSparqlQuery(String queryString) {
		if (queryString == null) {
			printRedText("Query is null: cannot execute.");
			return null;
		}
		ResultSet results = null;
		try {
			Query executableQuery = ReadOntologyModel.convertStringToQuery(queryString, ontologyModel);
			QueryExecution qexec = QueryExecutionFactory.create(executableQuery, ontologyModel);
			results = qexec.execSelect();
			qexec.close();
		} catch (Exception e) {
			printRedText(String.format("Unable to execute the query: %s", queryString));
		}
		return results;
	}

	public static boolean checkValidYearEntered(String timePeriod) {
		int year = Integer.parseInt(timePeriod);
		if (year >= 2004 && year <= 2016) {
			return true;
		}
		return false;
	}

	public static void outputResultsToConsole(ResultSet results) {
		ResultSetFormatter.out(System.out, results);
	}

	public static void printGreenText(String message) {
		System.out.println(OntologyConstants.GREEN_BOLD + message + OntologyConstants.RESET);
	}

	public static void printRedText(String message) {
		System.out.println(OntologyConstants.RED + message + OntologyConstants.RESET);
	}

	public static void printNeutralText(String message) {
		System.out.println(message);
	}

}
