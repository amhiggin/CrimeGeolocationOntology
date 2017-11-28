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
		try {
			ontology = ReadOntologyModel.loadAllClassesOnt(ontologyName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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

	}

	public static void presentOptionsToUser() {
		print(OntologyConstants.WELCOME_MESSAGE);
		print(OntologyConstants.INFO_ABOUT_ONTOLOGY);
		Scanner inputScanner = null;
		while (running) {
			print(String.format(OntologyConstants.PRESENT_USER_OPTIONS, questionsToAskOntology.get(0),
					questionsToAskOntology.get(1), questionsToAskOntology.get(2), questionsToAskOntology.get(3),
					questionsToAskOntology.get(4), questionsToAskOntology.get(5)));
			inputScanner = new Scanner(System.in);
			String input = inputScanner.nextLine();
			executeQueryBasedOnInput(input);
		}
		inputScanner.close();
	}

	private static void executeQueryBasedOnInput(String input) {
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
		default:
			print("Invalid input: " + input);
			return;
		}
		executeSparqlQuery(query);
	}

	public static void executeSparqlQuery(Query query) {
		if (query == null) {
			print("Query is null: cannot execute");
			return;
		}
	}

	public static void print(String message) {
		System.out.println(message);
	}

}
