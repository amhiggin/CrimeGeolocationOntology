package com.ontology;

import java.util.ArrayList;
import java.util.List;

public class OntologyConstants {
	public static String WELCOME_MESSAGE = "Welcome to the Crime Geolocation Ontology for Ireland";
	public static String INFO_ABOUT_ONTOLOGY = "This is an ontology spanning the Geohive Geolocation Data Ontology, "
			+ "and the 'Crimes at Garda Stations' dataset, both Irish datasets available on the web.";
	public static String PRESS_X_TO_EXIT = "Otherwise enter 'x' to exit the application.";
	public static String ERROR_READING_FILE = "An error occurred when trying to load the required file.";
	public static String THANK_YOU = "Thank you for using the Crime Geolocation Ontology for Ireland!";

	// This should be used with String.format(PRESENT_USER_OPTIONS, args)
	public static String PRESENT_USER_OPTIONS = "What would you like to ask? Select the number of the question to "
			+ "ask.\n1) %s\n2) %s\n3) %s\n4) %s\n5) %s\n6) %s";
	public static String SECTION_BREAK = " - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ";

	// TODO @Amber populate these when we know what the final questions are
	public static final String FIRST_QUESTION = "How many stations are there in County Donegal?";
	public static final String SECOND_QUESTION = "What is the most common type of crime in Kerry between 2004-2016: damage to the person, or damage to property?";
	public static final String THIRD_QUESTION = "On average, how many crimes are committed in Dublin per year?";
	public static final String FOURTH_QUESTION = "In which year did County Roscommon have its highest crime rate?";
	public static final String FIFTH_QUESTION = "Which county saw the biggest decline in burglaries over the period 2004-2016?";
	public static final String SIXTH_QUESTION = "Which county had the lowest number of frauds in 2008?";

	public static List<String> ALL_CRIMES = new ArrayList<String>() {{
		add("Attempts or Threats to Murder, Assaults, Harassments and Related Offences");
		add("Dangerous or Negligent Acts");
		add("Kidnapping and Related Offences");
		add("Robbery, Extortion and Hijacking Offences");
		add("Burglary and Related Offences");
		add("Theft and Related Offences");
		add("Fraud, Deception and Related Offences");
		add("Controlled Drug Offences");
		add("Weapons and Explosives Offences");
		add("Damage to Property and to the Environment");
		add("Public Order and other Social Code Offences");
		add("Offences against Government, Justice Procedures and Organisation of Crime");
	}};

	public static List<String> ALL_COUNTIES = new ArrayList<String>() {{
		add("Carlow");
		add("Cavan");
		add("Clare");
		add("Cork");
		add("Donegal");
		add("Dublin");
		add("Galway");
		add("Kerry");
		add("Kildare");
		add("Kilkenny");
		add("Laois");
		add("Leitrim");
		add("Limerick");
		add("Longford");
		add("Louth");
		add("Mayo");
		add("Meath");
		add("Monaghan");
		add("Offaly");
		add("Roscommon");
		add("Sligo");
		add("Tipperary");
		add("Waterford");
		add("Westmeath");
		add("Wexford");
		add("Wicklow");
	}};

	public static List<String> ALL_DIVISIONS = new ArrayList<String>() {{
		add("Limerick Division");
		add("Cork City Division");
		add("Cork West Division");
		add("Cork North Division");
		add("Kerry Division");
		add("Donegal Division");
		add("Sligo/Leitrim Division");
		add("Cavan/Monaghan Division");
		add("Louth Division");
		add("Galway Division");
		add("Mayo Division");
		add("Clare Division");
		add("Roscommon/Longford Division");
		add("Laois/Offaly Division");
		add("Wicklow Division");
		add("Meath Division");
		add("Westmeath Division");
		add("Kildare Division");
		add("D.M.R. Northern Division");
		add("D.M.R. Western Division");
		add("D.M.R. Southern Division");
		add("D.M.R. South Central Division");
		add("D.M.R. North Central Division");
		add("D.M.R. Eastern Division");
		add("Waterford Division");
		add("Wexford Division");
		add("Tipperary Division");
		add("Kilkenny/Carlow Division");
	}};

	/*
	 * I thought since we aren't doing a GUI, that it might be nice to have some
	 * colours in our UI! I can chat with everyone about what colours the text
	 * should be in our next meeting :) NOTE: use RESET at the end of the string
	 * after giving it the colour.
	 */

	// Reset
	public static final String RESET = "\033[0m"; // Text Reset

	// Regular Colors
	public static final String BLACK = "\033[0;30m"; // BLACK
	public static final String RED = "\033[0;31m"; // RED
	public static final String GREEN = "\033[0;32m"; // GREEN
	public static final String YELLOW = "\033[0;33m"; // YELLOW
	public static final String BLUE = "\033[0;34m"; // BLUE
	public static final String PURPLE = "\033[0;35m"; // PURPLE
	public static final String CYAN = "\033[0;36m"; // CYAN
	public static final String WHITE = "\033[0;37m"; // WHITE

	// Bold
	public static final String BLACK_BOLD = "\033[1;30m"; // BLACK
	public static final String RED_BOLD = "\033[1;31m"; // RED
	public static final String GREEN_BOLD = "\033[1;32m"; // GREEN
	public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
	public static final String BLUE_BOLD = "\033[1;34m"; // BLUE
	public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
	public static final String CYAN_BOLD = "\033[1;36m"; // CYAN
	public static final String WHITE_BOLD = "\033[1;37m"; // WHITE

	// Underline
	public static final String BLACK_UNDERLINED = "\033[4;30m"; // BLACK
	public static final String RED_UNDERLINED = "\033[4;31m"; // RED
	public static final String GREEN_UNDERLINED = "\033[4;32m"; // GREEN
	public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
	public static final String BLUE_UNDERLINED = "\033[4;34m"; // BLUE
	public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
	public static final String CYAN_UNDERLINED = "\033[4;36m"; // CYAN
	public static final String WHITE_UNDERLINED = "\033[4;37m"; // WHITE

	// Background
	public static final String BLACK_BACKGROUND = "\033[40m"; // BLACK
	public static final String RED_BACKGROUND = "\033[41m"; // RED
	public static final String GREEN_BACKGROUND = "\033[42m"; // GREEN
	public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
	public static final String BLUE_BACKGROUND = "\033[44m"; // BLUE
	public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
	public static final String CYAN_BACKGROUND = "\033[46m"; // CYAN
	public static final String WHITE_BACKGROUND = "\033[47m"; // WHITE

	// High Intensity
	public static final String BLACK_BRIGHT = "\033[0;90m"; // BLACK
	public static final String RED_BRIGHT = "\033[0;91m"; // RED
	public static final String GREEN_BRIGHT = "\033[0;92m"; // GREEN
	public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
	public static final String BLUE_BRIGHT = "\033[0;94m"; // BLUE
	public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
	public static final String CYAN_BRIGHT = "\033[0;96m"; // CYAN
	public static final String WHITE_BRIGHT = "\033[0;97m"; // WHITE

	// Bold High Intensity
	public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
	public static final String RED_BOLD_BRIGHT = "\033[1;91m"; // RED
	public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
	public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
	public static final String BLUE_BOLD_BRIGHT = "\033[1;94m"; // BLUE
	public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
	public static final String CYAN_BOLD_BRIGHT = "\033[1;96m"; // CYAN
	public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

	// High Intensity backgrounds
	public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
	public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
	public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
	public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
	public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
	public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
	public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m"; // CYAN
	public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m"; // WHITE

}
