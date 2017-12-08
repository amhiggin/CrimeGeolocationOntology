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
					System.out.print(OntologyConstants.CYAN_BOLD_BRIGHT);
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
		String county = null, specificCrime = null, timePeriod = null, electoralDivision = null, gardaDivision = null, threshold = null;
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
			// "For a specified county, what crimes had a number of instances over a specified threshold?"
			queryString = queriesAsStrings.get(3);
			System.out.println("Enter the county: ");
			county = inputScanner.nextLine();
			System.out.println("Enter the threshold (an integer): ");
			threshold = inputScanner.nextLine();
			if ((checkValidThresholdEntered(threshold)) && OntologyConstants.ALL_COUNTIES.contains(county)) {
				queryString = String.format(queryString, county, threshold);
			} else {
				queryString = null;
				printRedText(String.format("Invalid data entered: %s, %s", county, threshold));
			}
			break;
		case "5":
			// "In a specified county, which station and in what year saw the highest recorded number of occurrences of a specified crime type?"
			
			System.out.println("Enter the crime: ");
			specificCrime = inputScanner.nextLine();
			System.out.println("Enter the county: ");
			county = inputScanner.nextLine();
			
			
			String crimeString = getCrimeStringFormatted(specificCrime);
			queryString = queriesAsStrings.get(4);			
			
			if (OntologyConstants.ALL_COUNTIES.contains(county) && OntologyConstants.ALL_CRIMES.contains(specificCrime)) {
				queryString = String.format(queryString, crimeString, county);
			} else {
				queryString = null;
				printRedText(String.format("Invalid data entered: %s, %s", specificCrime, county));
			}
			break;
		case "6":
			// "Which stations (and for what crimes) in a specified county, in a specified year, had fewer than a specified number of crimes?"
			queryString = queriesAsStrings.get(5);
			System.out.println("Enter the county: ");
			county = inputScanner.nextLine();
			System.out.println("Enter the year of interest: ");
			timePeriod = inputScanner.nextLine();
			System.out.println("Enter the threshold number of crimes:" );
			threshold = inputScanner.nextLine();
			if ((checkValidYearEntered(timePeriod) && OntologyConstants.ALL_COUNTIES.contains(county)) && (checkValidThresholdEntered(threshold))) {
				queryString = String.format(queryString, county, timePeriod, threshold);
			} else {
				queryString = null;
				printRedText(String.format("Invalid data entered: %s, %s", county, timePeriod, threshold));
			}
			break;
		default:
			printRedText("Invalid input: " + selectedQuery);
			return null;
		}
		return executeSparqlQuery(queryString);
	}

	private static String getCrimeStringFormatted(String specificCrime) {
		String crimeString = "";
		
		switch (specificCrime) {
		case "Attempts or Threats to Murder, Assaults, Harassments and Related Offences":
			 crimeString = "AttemptsOrThreatsToMurderAssaultsHarassmentsAndRelatedOffences";
			break;
		case "Burglary and Related Offences":
			crimeString = "BurglaryAndRelatedOffences";
			break;
		case "Controlled Drug Offences":
			crimeString = "ControlledDrugOffences";
			break;
		case "Damage to Property and to the Environment":
			crimeString = "DamageToPropertyAndToTheEnvironment";
			break;
		case "Dangerous or Negligent Acts":
			crimeString = "DangerousOrNegligentActs";
			break;
		case "Fraud, Deception and Related Offences":
			crimeString = "FraudDeceptionAndRelatedOffences";
			break;
		case "Kidnapping and Related Offences":
			crimeString = "KidnappingAndRelatedOffences";
			break;
		case "Offences against Government, Justice Procedures and Organisation of Crime":
			crimeString = "OffencesAgainstGovernmentJusticeProceduresAndOrganisationOfCrime";
			break;
		case "Public Order and other Social Code Offences":
			crimeString = "PublicOrderAndOtherSocialCodeOffences";
			break;
		case "Robbery, Extortion and Hijacking Offences":
			crimeString = "RobberyExtortionAndHijackingOffences";
			break;
		case "Theft and Related Offences":
			crimeString = "TheftAndRelatedOffences";
			break;
		case "Weapons and Explosives Offences":
			crimeString = "WeaponsAndExplosivesOffences";
			break;
		}
		return crimeString;
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
	
	public static boolean checkValidThresholdEntered(String threshold){
		try {
			int thresholdInt = Integer.parseInt(threshold);
			return true;
		} catch (Exception e) {
			printRedText("Invalid threshold entered: " + threshold);
			return false;
		}
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
