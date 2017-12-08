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

	public static String ontologyName = "mapping_new.owl"; // Crime-in-Ireland-2004-2016.owl";
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
				printRedText("Exception Message: " + e.getMessage());
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
				printRedText("Exception occurred: " + e.getMessage());
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
			printGreenText(OntologyConstants.OPTIONS_TO_DISPLAY_LISTS);
			printGreenText(OntologyConstants.PRESS_X_TO_EXIT);
			String input = inputScanner.nextLine();
			switch (input) {
			case "electoral divisions":
				OntologyConstants.printAllElectoralDivisions();
				break;
			case "garda divisions":
				OntologyConstants.printAllGardaDivisions();
				break;
			case "counties":
				OntologyConstants.printAllCounties();
				break;
			case "crimes":
				OntologyConstants.printAllCrimes();
				break;
			case "x":
				printGreenText("'x' entered. Exiting application.");
				running = false;
				return;
			default:
				ResultSet results = executeQueryBasedOnUserInput(input, inputScanner);
				if (results != null) {
					System.out.print(OntologyConstants.PURPLE);
					outputResultsToConsole(results);
				} else {
					if (running == true) {
						printRedText("Couldn't process the results of the query.");
					} else {
						break;
					}
				}
			}
		}
		inputScanner.close();
	}

	private static ResultSet executeQueryBasedOnUserInput(String selectedQuery, Scanner inputScanner) {
		String queryString = null;
		String county = null, specificCrime = null, timePeriod = null, electoralDivision = null, gardaDivision = null;
		switch (selectedQuery) {
		case "1":
			// "What stations are there in a specified county?";
			queryString = queriesAsStrings.get(0);
			System.out.println("Enter the county: ");
			county = inputScanner.nextLine();
			if (OntologyConstants.ALL_COUNTIES.contains(county)) {
				queryString = String.format(queryString, county);
			} else {
				queryString = null;
				printRedText(String.format("Invalid data entered: %s", county));
			}
			break;
		case "2":
			// "What is the most common type of crime in a specified county in a specified year?"
			queryString = queriesAsStrings.get(1);
			System.out.println("Enter the county: ");
			county = inputScanner.nextLine();
			System.out.println("Enter the year of interest: ");
			timePeriod = inputScanner.nextLine();
			if ((checkValidYearEntered(timePeriod)) && OntologyConstants.ALL_COUNTIES.contains(county)) {
				queryString = String.format(queryString, county, timePeriod);
			} else {
				queryString = null;
				printRedText(String.format("Invalid data entered: %s, %s", county, timePeriod));
			}
			break;
		case "3":
			// "What electoral divisions are in a specified garda division?"
			queryString = queriesAsStrings.get(2);
			System.out.println("Enter the garda division: ");
			gardaDivision = inputScanner.nextLine();
			if (OntologyConstants.ALL_DIVISIONS.contains(gardaDivision)) {
				queryString = String.format(queryString, gardaDivision);
			} else {
				queryString = null;
				printRedText(String.format("Invalid data entered: %s", gardaDivision));
			}
			break;
		case "4":
			// "In which year did a specified Electoral Division have its
			// highest crime rate?"
			queryString = queriesAsStrings.get(3);
			System.out.println("Enter the Electoral Division: ");
			electoralDivision = inputScanner.nextLine();
			if (OntologyConstants.ALL_ELECTORAL_DIVISIONS.contains(electoralDivision)) {
				queryString = String.format(queryString, electoralDivision);
			} else {
				queryString = null;
				printRedText(String.format("Invalid data entered: %s", electoralDivision));
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
				printRedText(String.format("Invalid data entered: %s", specificCrime));
			}
			break;
		case "6":
			// "Which Electoral Division had the lowest number of a specific
			// crime in a given year?";
			queryString = queriesAsStrings.get(5);
			System.out.println("Enter the crime (e.g. burglary, fraud): ");
			specificCrime = inputScanner.nextLine();
			System.out.println("Enter the year of interest: ");
			timePeriod = inputScanner.nextLine();
			if ((checkValidYearEntered(timePeriod) && OntologyConstants.ALL_CRIMES.contains(specificCrime))) {
				queryString = String.format(queryString, specificCrime, timePeriod);
			} else {
				queryString = null;
				printRedText(String.format("Invalid data entered: %s, %s", specificCrime, timePeriod));
			}
			break;
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
		printGreenText(String.format("Query to execute is: %s", queryString));
		ResultSet results = null;
		try {
			Query executableQuery = ReadOntologyModel.convertStringToQuery(queryString, ontologyModel);
			QueryExecution qexec = QueryExecutionFactory.create(executableQuery, ontologyModel);
			results = qexec.execSelect();
		} catch (Exception e) {
			printRedText(String.format("Unable to execute the query: %s", queryString));
			printRedText("Exception Message: " + e.getMessage());
		}
		return results;
	}

	public static boolean checkValidYearEntered(String timePeriod) {
		int year = Integer.parseInt(timePeriod);
		if (year >= 2004 && year <= 2016) {
			return true;
		}
		printRedText("Invalid year entered: " + Integer.toString(year));
		return false;
	}

	public static void outputResultsToConsole(ResultSet results) {
		ResultSetFormatter.out(System.out, results);
		System.out.print(OntologyConstants.RESET);
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
