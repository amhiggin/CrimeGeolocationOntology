package com.ontology;

import java.util.ArrayList;
import java.util.List;

public class OntologyConstants {
	/*
	 * I thought since we aren't doing a GUI, that it might be nice to have some
	 * colours in the UI! NOTE: use RESET at the end of the string after giving
	 * it the colour.
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

	public static String WELCOME_MESSAGE = "Welcome to the Crime Geolocation Ontology for Ireland";
	public static String INFO_ABOUT_ONTOLOGY = "This is an ontology spanning the Geohive Geolocation Data Ontology, "
			+ "and the 'Crimes at Garda Stations' dataset, both Irish datasets available on the web.";
	public static String PRESS_X_TO_EXIT = "Otherwise enter 'x' to exit the application.";
	public static String ERROR_READING_FILE = RED + "An error occurred when trying to load the required file." + RESET;
	public static String THANK_YOU = "Thank you for using the Crime Geolocation Ontology for Ireland!";

	// This should be used with String.format(PRESENT_USER_OPTIONS, args).
	// PRINTS IN PURPLE
	public static String PRESENT_USER_OPTIONS = "What would you like to ask? Select the number of the question to "
			+ "ask. \n\t1) %s\n\t2) %s\n\t3) %s\n\t4) %s\n\t5) %s\n\t6) %s";
	public static String OPTIONS_TO_DISPLAY_LISTS = "Enter:\n\t- 'legal towns' to display a list of all legal towns and cities\n\t- 'crimes' to display a list of crimes for which records exist\n\t- 'counties' to display a list of all counties";
	public static String SECTION_BREAK = " - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ";

	// TODO @Amber populate these when we know what the final questions are
	public static final String FIRST_QUESTION = CYAN + "How many stations are there in a specified county?" + RESET;
	public static final String SECOND_QUESTION = CYAN
			+ "What is the most common type of crime in a specified county in a specified year: damage to the person, or damage to property?"
			+ RESET;
	public static final String THIRD_QUESTION = CYAN
			+ "On average, how many crimes are committed in a specified county per year?" + RESET;
	public static final String FOURTH_QUESTION = CYAN
			+ "In which year did a specified Legal Town/City have its highest crime rate?" + RESET;
	// FIXME: figure out how to do the time ranges
	public static final String FIFTH_QUESTION = CYAN
			+ "Which county saw the biggest rise in a specific crime over the period 2004-2016?" + RESET;
	public static final String SIXTH_QUESTION = CYAN
			+ "Which Legal Town/City had the lowest number of a specific crime in a given year?" + RESET;

	public static List<String> ALL_CRIMES = new ArrayList<String>() {
		{
			add("Attempts or Threats to Murder, Assaults, Harassments and Related Offences");
			add("Burglary and Related Offences");
			add("Controlled Drug Offences");
			add("Damage to Property and to the Environment");
			add("Dangerous or Negligent Acts");
			add("Fraud, Deception and Related Offences");
			add("Kidnapping and Related Offences");
			add("Offences against Government, Justice Procedures and Organisation of Crime");
			add("Public Order and other Social Code Offences");
			add("Robbery, Extortion and Hijacking Offences");
			add("Theft and Related Offences");
			add("Weapons and Explosives Offences");
		}
	};

	public static void printAllCrimes() {
		System.out.println(
				GREEN_BOLD + "The crimes you can select for questions 5 and 6 (easiest to just copy+paste): " + RESET);
		for (String crime : ALL_CRIMES) {
			System.out.println("\t" + PURPLE + crime + RESET);
		}
	}

	public static List<String> ALL_COUNTIES = new ArrayList<String>() {
		{
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
		}
	};

	public static void printAllCounties() {
		System.out.println(GREEN_BOLD
				+ "The counties you can select for questions 1, 2 and 3 (easiest to just copy+paste): " + RESET);
		for (String county : ALL_COUNTIES) {
			System.out.println("\t" + PURPLE + county + RESET);
		}
	}

	public static List<String> ALL_DIVISIONS = new ArrayList<String>() {
		{
			add("Cavan/Monaghan Division");
			add("Clare Division");
			add("Cork City Division");
			add("Cork North Division");
			add("Cork West Division");
			add("D.M.R. Eastern Division");
			add("D.M.R. North Central Division");
			add("D.M.R. Northern Division");
			add("D.M.R. South Central Division");
			add("D.M.R. Southern Division");
			add("D.M.R. Western Division");
			add("Donegal Division");
			add("Galway Division");
			add("Kerry Division");
			add("Kildare Division");
			add("Kilkenny/Carlow Division");
			add("Laois/Offaly Division");
			add("Limerick Division");
			add("Louth Division");
			add("Mayo Division");
			add("Meath Division");
			add("Roscommon/Longford Division");
			add("Sligo/Leitrim Division");
			add("Tipperary Division");
			add("Waterford Division");
			add("Westmeath Division");
			add("Wexford Division");
			add("Wicklow Division");
		}
	};

	public static List<String> ALL_LEGAL_TOWNS_AND_CITIES = new ArrayList<String>() {
		{
			add("Ardee Legal Town");
			add("Arklow Legal Town");
			add("Athlone Legal Town");
			add("Athy Legal Town");
			add("Balbriggan Legal Town");
			add("Ballina Legal Town");
			add("Ballinasloe Legal Town");
			add("Ballybay Legal Town");
			add("Ballyshannon Legal Town");
			add("Bandon Legal Town");
			add("Bantry Legal Town");
			add("Belturbet Legal Town");
			add("Birr Legal Town");
			add("Boyle Legal Town");
			add("Bray Legal Town");
			add("Buncrana Legal Town");
			add("Bundoran Legal Town");
			add("Carlow Legal Town");
			add("Carrick-On-Suir Legal Town");
			add("Carrickmacross Legal Town");
			add("Cashel Legal Town");
			add("Castlebar Legal Town");
			add("Castleblayney Legal Town");
			add("Cavan Legal Town");
			add("Clonakilty Legal Town");
			add("Clones Legal Town");
			add("Clonmel Legal Town");
			add("Cobh Legal Town");
			add("Cootehill Legal Town");
			add("Cork City");
			add("Drogheda Legal Town");
			add("Droichead Nua Legal Town");
			add("Dublin City");
			add("Dundalk Legal Town");
			add("Dungarvan Legal Town");
			add("Edenderry Legal Town");
			add("Ennis Legal Town");
			add("Enniscorthy Legal Town");
			add("Fermoy Legal Town");
			add("Galway City");
			add("Gorey Legal Town");
			add("Granard Legal Town");
			add("Greystones Legal Town");
			add("Kilkee Legal Town");
			add("Kilkenny Legal Town");
			add("Killarney Legal Town");
			add("Kilrush Legal Town");
			add("Kinsale Legal Town");
			add("Leixlip Legal Town");
			add("Letterkenny Legal Town");
			add("Limerick City");
			add("Lismore Legal Town");
			add("Listowel Legal Town");
			add("Longford Legal Town");
			add("Loughrea Legal Town");
			add("Macroom Legal Town");
			add("Mallow Legal Town");
			add("Midleton Legal Town");
			add("Monaghan Legal Town");
			add("Mountmellick Legal Town");
			add("Muinebeag Legal Town");
			add("Mullingar Legal Town");
			add("Naas Legal Town");
			add("Navan (An Uaimh) Legal Town");
			add("Nenagh Legal Town");
			add("New Ross Legal Town");
			add("Passage West Legal Town");
			add("Portlaoighise Legal Town");
			add("Shannon Legal Town");
			add("Skibbereen Legal Town");
			add("Sligo Legal Town");
			add("Templemore Legal Town");
			add("Thurles Legal Town");
			add("Tipperary Legal Town");
			add("Tralee Legal Town");
			add("Tramore Legal Town");
			add("Trim Legal Town");
			add("Tuam Legal Town");
			add("Tullamore Legal Town");
			add("Waterford City");
			add("Westport Legal Town");
			add("Wexford Legal Town");
			add("Wicklow Legal Town");
			add("Youghal Legal Town");
		}
	};

	public static void printAllLegalTownsAndCities() {
		System.out.println(GREEN_BOLD
				+ "The legal towns/cities you can select for question 4 (easiest to just copy+paste): " + RESET);
		for (String townOrCity : ALL_LEGAL_TOWNS_AND_CITIES) {
			System.out.println("\t" + PURPLE + townOrCity + RESET);
		}
	}

}
