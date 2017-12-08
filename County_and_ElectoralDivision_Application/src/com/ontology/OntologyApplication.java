package com.ontology;

import java.awt.geom.Point2D;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;



public class OntologyApplication {
	
	public static boolean running;
	private static List<GardaStation> gardaStations = new ArrayList<GardaStation>();
	private static List<County> counties = new ArrayList<County>();
	private static List<ElectoralDivision> electoralDivisions = new ArrayList<ElectoralDivision>();
	private static List<String> electoralDivision_geometries = new ArrayList<String>();

	public static void main(String args[]) {
		
		printGreenText("Preparing datasets. Please wait...");
		running = true;
		
		// Populate the list of electoralDivisions
     	String queryText_electoralDivisionNames = String.format("PREFIX my: <http://example.org/ApplicationSchema#>\n" +
						                "PREFIX geo: <http://www.opengis.net/ont/geosparql#>\n" +
						                "PREFIX geof: <http://www.opengis.net/def/function/geosparql/>\n" +
						                
										"SELECT ?electoralDivisionURI ?electoralDivisionName WHERE { " +
											"?electoralDivisionURI <http://www.w3.org/2000/01/rdf-schema#label> ?electoralDivisionName . " +
											"} ORDER BY ASC(?electoralDivisionName)");//, county);
        
		Model model_electoralDivisionNames = ModelFactory.createDefaultModel();
		model_electoralDivisionNames.read("file:electoral_divisions_names.ttl", "TTL");	// Electoral divisions
		
		Query query_electoralDivisionNames = QueryFactory.create(queryText_electoralDivisionNames);
        QueryExecution qe_electoralDivisionNames = QueryExecutionFactory.create(queryText_electoralDivisionNames, model_electoralDivisionNames);
        ResultSet results_electoralDivisionNames = qe_electoralDivisionNames.execSelect();
        
        ByteArrayOutputStream myConsoleStream_electoralDivisionNames;
        myConsoleStream_electoralDivisionNames = new ByteArrayOutputStream();
        
		
		ResultSetFormatter.out(myConsoleStream_electoralDivisionNames, results_electoralDivisionNames, query_electoralDivisionNames);
        String electoralDivision_names_text = new String( myConsoleStream_electoralDivisionNames.toByteArray() );
        
        String[] electoralDivision_names = electoralDivision_names_text.split(System.getProperty("line.separator"));
        
        for (String electoralDivision_text: electoralDivision_names) {
        	
        	if (electoralDivision_text.indexOf("\"") > -1 && electoralDivision_text.indexOf("@") == -1)
        	{
        		String uri = electoralDivision_text.substring(electoralDivision_text.indexOf("electoralDivisions/") + 6);
        		uri = uri.substring(0, uri.indexOf(">"));
            	
            	String name = electoralDivision_text.substring(electoralDivision_text.indexOf("\"") + 1);
            	name = name.substring(0, name.indexOf("\""));
        		
            	ElectoralDivision newElectoralDivision = new ElectoralDivision(name, uri);
                electoralDivisions.add(newElectoralDivision);
        	}
        }
        
     // Populate the list of electoralDivision geometries
     	String queryText_electoralDivisionGeometries = String.format("PREFIX my: <http://example.org/ApplicationSchema#>\n" +
						                "PREFIX geo: <http://www.opengis.net/ont/geosparql#>\n" +
						                "PREFIX geof: <http://www.opengis.net/def/function/geosparql/>\n" +
						                
										"SELECT ?electoralDivisionURI ?geometry WHERE { " +
											"?electoralDivisionURI geo:hasGeometry ?blankNode . " + 
											"?blankNode geo:asWKT ?geometry . " +
											"} ");
        
		Model model_electoralDivisionGeometries = ModelFactory.createDefaultModel();
		model_electoralDivisionGeometries.read("file:electoral_divisions_geometry.ttl", "TTL");	// Electoral divisions
		
		Query query_electoralDivisionGeometries = QueryFactory.create(queryText_electoralDivisionGeometries);
        QueryExecution qe_electoralDivisionGeometries = QueryExecutionFactory.create(queryText_electoralDivisionGeometries, model_electoralDivisionGeometries);
        ResultSet results_electoralDivisionGeometries = qe_electoralDivisionGeometries.execSelect();
        
        ByteArrayOutputStream myConsoleStream_electoralDivisionGeometries;
        myConsoleStream_electoralDivisionGeometries = new ByteArrayOutputStream();
		
		ResultSetFormatter.out(myConsoleStream_electoralDivisionGeometries, results_electoralDivisionGeometries, query_electoralDivisionGeometries);
        String electoralDivision_geometries_text = new String( myConsoleStream_electoralDivisionGeometries.toByteArray() );
        
        String[] electoralDivision_geometries = electoralDivision_geometries_text.split(System.getProperty("line.separator"));
        
        for (String geometry_text: electoralDivision_geometries) 
        {
        	if (geometry_text.indexOf("\"") > -1)
        	{
        		String uri = geometry_text.substring(geometry_text.indexOf("electoralDivisions/") + 6);
        		uri = uri.substring(0, uri.indexOf(">"));
            	
            	String name_and_coordinates_portion = geometry_text.substring(geometry_text.indexOf("\"") + 1);
            	String[] polygonGroups;
                
                if (name_and_coordinates_portion.indexOf("MULTIPOLYGON") > -1)	// MULTIPOLYGON
                {	
                	name_and_coordinates_portion = name_and_coordinates_portion.substring(name_and_coordinates_portion.indexOf("(((") + 3);
                	name_and_coordinates_portion = name_and_coordinates_portion.substring(0, name_and_coordinates_portion.indexOf(")))"));   

                    polygonGroups = name_and_coordinates_portion.split("\\)\\)");
                    
                    int counter = 0;
                    for (String polygonGroup : polygonGroups) {
                    	
                    	polygonGroups[counter] = polygonGroup.substring(polygonGroup.indexOf("((") + 2);
                    	
                    	counter++;
                    }
                }
                else	// POLYGON
                {
                	name_and_coordinates_portion = name_and_coordinates_portion.substring(name_and_coordinates_portion.indexOf("((") + 2);
                	name_and_coordinates_portion = name_and_coordinates_portion.substring(0, name_and_coordinates_portion.indexOf("))"));   
                    
                    String[] groupArray = {name_and_coordinates_portion};
                    polygonGroups = groupArray;
                }
                
        		
        		for (ElectoralDivision electoralDivision : electoralDivisions) {
        			if (electoralDivision.uri.equals(uri))
        			{
        				electoralDivision.polygonGroups = polygonGroups;
        				
        			}
        				
        		}
        	}
        }
		
		// Populate the list of Garda stations
		String queryText_gardaStations =
				"SELECT ?station_uri ?position " +
						"WHERE { " +
							"?station_uri <http://example.org/data/garda_stations_CoordinatesOnly.csv#Station> ?station . " +
							"?station_uri <http://example.org/data/garda_stations_CoordinatesOnly.csv#Position> ?position . " +
							"} ORDER BY ASC(?station_uri)";
		
		Model model_gardaStations = ModelFactory.createDefaultModel();
		model_gardaStations.read("file:garda_stations_CoordinatesOnly.owl", "RDF/XML");
		
		Query query_gardaStations = QueryFactory.create(queryText_gardaStations);
        QueryExecution qe_gardaStations = QueryExecutionFactory.create(query_gardaStations, model_gardaStations);
        ResultSet results_gardaStations = qe_gardaStations.execSelect();

    	ByteArrayOutputStream myConsoleStream_gardaStations;
    	myConsoleStream_gardaStations = new ByteArrayOutputStream();
		
		ResultSetFormatter.out(myConsoleStream_gardaStations, results_gardaStations, query_gardaStations);
		

        String station_positions_text = new String( myConsoleStream_gardaStations.toByteArray() );

        station_positions_text = station_positions_text.substring(station_positions_text.indexOf("<") + 1);
        
        String[] station_positions = station_positions_text.split(System.getProperty("line.separator"));
        
        for (String next_station: station_positions) {
        	
        	if (next_station.indexOf("#") > -1)
        	{
        		String name = next_station.substring(next_station.indexOf("#") + 1);
            	name = name.substring(0, name.indexOf("/"));
            	
            	String coordinates = next_station.substring(next_station.indexOf("\"") + 1);
            	coordinates = coordinates.substring(0, coordinates.indexOf("\""));
            	String[] separate_coordinates = coordinates.split(" ");
        		double station_latitude = Double.parseDouble(separate_coordinates[0]);
        		double station_longitude = Double.parseDouble(separate_coordinates[1]);
        		
        		GardaStation station = new GardaStation(name, station_latitude, station_longitude);
            	
            	gardaStations.add(station);
        	}
        	
        }
        
        // Populate the list of counties
     	String queryText_counties = String.format("PREFIX my: <http://example.org/ApplicationSchema#>\n" +
						                "PREFIX geo: <http://www.opengis.net/ont/geosparql#>\n" +
						                "PREFIX geof: <http://www.opengis.net/def/function/geosparql/>\n" +
						                
										"SELECT ?countyName ?geometry WHERE { " +
											"?countyURI geo:hasGeometry ?blankNode . " +
											"?blankNode geo:asWKT ?geometry . "  + 
											"?countyURI <http://www.w3.org/2000/01/rdf-schema#label> ?countyName" +
											"} ");
        
		Model model_counties = ModelFactory.createDefaultModel();
		model_counties.read("file:counties_cutNames.ttl", "TTL");
		
		Query query_counties = QueryFactory.create(queryText_counties);
        QueryExecution qe_counties = QueryExecutionFactory.create(query_counties, model_counties);
        ResultSet results_counties = qe_counties.execSelect();
        
        ByteArrayOutputStream myConsoleStream;
    	myConsoleStream = new ByteArrayOutputStream();
		
		ResultSetFormatter.out(myConsoleStream, results_counties, query_counties);
        String county_polygons_text = new String( myConsoleStream.toByteArray() );
        
        String[] county_polygons = county_polygons_text.split(System.getProperty("line.separator"));
        
        for (String next_county: county_polygons) {
        	
        	if (next_county.indexOf("\"") > -1)
        	{
        		String name_and_coordinates_portion = next_county.substring(next_county.indexOf("\"") + 1);
            	String name = name_and_coordinates_portion.substring(0, name_and_coordinates_portion.indexOf("\""));
            	
            	String[] polygonGroups;
                
                if (name_and_coordinates_portion.indexOf("MULTIPOLYGON") > -1)	// MULTIPOLYGON
                {	
                	name_and_coordinates_portion = name_and_coordinates_portion.substring(name_and_coordinates_portion.indexOf("(((") + 3);
                	name_and_coordinates_portion = name_and_coordinates_portion.substring(0, name_and_coordinates_portion.indexOf(")))"));   

                    polygonGroups = name_and_coordinates_portion.split("\\)\\)");
                    
                    int counter = 0;
                    for (String polygonGroup : polygonGroups) {
                    	
                    	polygonGroups[counter] = polygonGroup.substring(polygonGroup.indexOf("((") + 2);
                    	
                    	counter++;
                    }
                }
                else	// POLYGON
                {
                	name_and_coordinates_portion = name_and_coordinates_portion.substring(name_and_coordinates_portion.indexOf("((") + 2);
                	name_and_coordinates_portion = name_and_coordinates_portion.substring(0, name_and_coordinates_portion.indexOf("))"));   
                    
                    String[] groupArray = {name_and_coordinates_portion};
                    polygonGroups = groupArray;
                }
                
                County newCounty = new County(name, polygonGroups);
                counties.add(newCounty);
        	}
        	
        }
		

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
			printNeutralText("Enter '1' to print out the list of Garda stations and the counties they are located in.\n" +
			"Enter '2' to print out the list of Garda stations and the electoral divisions they are located in.");
			printGreenText(OntologyConstants.PRESS_X_TO_EXIT);
			String input = inputScanner.nextLine();
			executeQueryBasedOnUserInput(input, inputScanner);
		}
		inputScanner.close();
	}

	private static void executeQueryBasedOnUserInput(String selectedQuery, Scanner inputScanner) {
		String queryString = null;
		String county = null, specificCrime = null, timePeriod = null;
		
		switch (selectedQuery) {
		case "1":
			printGreenText("Printing list of Garda stations and counties...\n");
			// List each station and its corresponding county
			for (GardaStation station : gardaStations)
			{
				ArrayList<String> counties = listStationCounties(station);
				System.out.print(station.name + " ");
				for (String string_county : counties)
				{
					if (string_county.length() > 0)
					{
						string_county = string_county.substring(0,1) + string_county.substring(1).toLowerCase();
					}
					
						
					System.out.print(string_county);
				}
				System.out.println();
			}
			printGreenText("\nEnd of list.\n");
			break;
		case "2":
			printGreenText("Printing list of Garda stations and electoral divisions...\n");
			// List each station and its corresponding electoralDivisions
			for (GardaStation station : gardaStations)
			{
				ArrayList<String> electoralDivisions = listStationElectoralDivisions(station);
				System.out.print(station.name + " ");
				
				for (String string_electoralDivision : electoralDivisions)
				{
					if (string_electoralDivision.length() > 0)
					{
						string_electoralDivision = string_electoralDivision.substring(0,1) + string_electoralDivision.substring(1).toLowerCase();
					}
					
						
					System.out.print(string_electoralDivision);
				}
				System.out.println();
			}
			printGreenText("\nEnd of list.\n");
			break;
		case "x":
			printGreenText("'x' entered. Exiting application.");
			running = false;
			break;
		default:
			printRedText("Invalid input: " + selectedQuery);
		}
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
	
	public static ArrayList<String> listStationElectoralDivisions(GardaStation station)
	{
		ArrayList<String> electoralDivisionsThisStationIsIn = new ArrayList<String>();
		for (ElectoralDivision electoralDivision : electoralDivisions)	// Go through list of electoralDivisions
		{
			boolean isInElectoralDivision = false;
			
			for (String groupOfCoordinates : electoralDivision.polygonGroups)
			{
				ArrayList<Point2D.Double> polygonPoints = new ArrayList<Point2D.Double>();
	        	
	        	String[] coordinatePairs = groupOfCoordinates.split(", ");
	        	
	        	for (String pair : coordinatePairs) {
	        		
	    			String[] coordinates = pair.split(" ");
	    			coordinates[0] = coordinates[0].replace("(", "");
	    			coordinates[1] = coordinates[1].replace(")", "");
	    			
	    			double latitude = Double.parseDouble(coordinates[0]);
	    			double longitude = Double.parseDouble(coordinates[1]);
	    		    
	    		    Point2D.Double newPoint = new Point2D.Double(longitude,latitude);
	    		    
	    		    polygonPoints.add(newPoint);
	    		}
	        	
	        	Point2D.Double stationPoint = new Point2D.Double(station.latitude, station.longitude);
				if (IsPointWithinPolygon(stationPoint, polygonPoints)){
					isInElectoralDivision = true;
					break;
				}
			}
			
			if (isInElectoralDivision)
			{
				electoralDivisionsThisStationIsIn.add(electoralDivision.name);
			}
		}
		return electoralDivisionsThisStationIsIn;
	}
	
	public static ArrayList<String> listStationCounties(GardaStation station)
	{
		ArrayList<String> countiesThisStationIsIn = new ArrayList<String>();
		for (County county : counties)	// Go through list of counties
		{
			boolean isInCounty = false;
			
			for (String groupOfCoordinates : county.polygonGroups)
			{
				ArrayList<Point2D.Double> polygonPoints = new ArrayList<Point2D.Double>();
	        	
	        	String[] coordinatePairs = groupOfCoordinates.split(", ");
	        	
	        	for (String pair : coordinatePairs) {
	        		
	    			String[] coordinates = pair.split(" ");
	    			coordinates[0] = coordinates[0].replace("(", "");
	    			coordinates[1] = coordinates[1].replace(")", "");
	    			
	    			double latitude = Double.parseDouble(coordinates[0]);
	    			double longitude = Double.parseDouble(coordinates[1]);
	    		    
	    		    Point2D.Double newPoint = new Point2D.Double(longitude,latitude);
	    		    
	    		    polygonPoints.add(newPoint);
	    		}
	        	
	        	Point2D.Double stationPoint = new Point2D.Double(station.latitude, station.longitude);
				if (IsPointWithinPolygon(stationPoint, polygonPoints)){
					isInCounty = true;
				}
			}
			
			if (isInCounty)
			{
				countiesThisStationIsIn.add(county.name);
			}
		}
		return countiesThisStationIsIn;
	}

	public static ArrayList<GardaStation> findStationsInCounty(String county) {
		
		county = county.toUpperCase();

		// Find the geometry of the county in the query
     	String queryText = String.format("PREFIX my: <http://example.org/ApplicationSchema#>\n" +
						                "PREFIX geo: <http://www.opengis.net/ont/geosparql#>\n" +
						                "PREFIX geof: <http://www.opengis.net/def/function/geosparql/>\n" +
						                
										"SELECT ?geometry WHERE { " +
											"?countyURI geo:hasGeometry ?blankNode . " +
											"?countyURI ?predicate \"%s\"@en . " +
											"?blankNode geo:asWKT ?geometry . "  + 
											"} ", county);
     	
     	
     	Model model = ModelFactory.createDefaultModel();
        model.read("file:counties.ttl", "TTL");
   
        Query query = QueryFactory.create(queryText);
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

    	ByteArrayOutputStream myConsoleStream;
    	myConsoleStream = new ByteArrayOutputStream();
		
		ResultSetFormatter.out(myConsoleStream, results, query);
        
        String text = new String( myConsoleStream.toByteArray() );
        

	    ArrayList<GardaStation> stationsInCounty = new ArrayList<GardaStation>();
	    
	    String[] polygonGroups;
        
        if (text.indexOf("MULTIPOLYGON") > -1)	// MULTIPOLYGON
        {	
        	text = text.substring(text.indexOf("(((") + 3);
            text = text.substring(0, text.indexOf(")))"));   

            polygonGroups = text.split("\\)\\)");
            
            int counter = 0;
            for (String polygonGroup : polygonGroups) {
            	
            	polygonGroups[counter] = polygonGroup.substring(polygonGroup.indexOf("((") + 2);
            	
            	counter++;
            }
        }
        else	// POLYGON
        {
        	text = text.substring(text.indexOf("((") + 2);
            text = text.substring(0, text.indexOf("))"));   
            
            String[] groupArray = {text};
            polygonGroups = groupArray;
        }
        
        
        for (String groupOfCoordinates : polygonGroups) {
        	
        	ArrayList<Point2D.Double> polygonPoints = new ArrayList<Point2D.Double>();
        	
        	String[] coordinatePairs = groupOfCoordinates.split(", ");
        	
        	for (String pair : coordinatePairs) {
        		
    			String[] coordinates = pair.split(" ");
    			coordinates[0] = coordinates[0].replace("(", "");
    			coordinates[1] = coordinates[1].replace(")", "");
    			
    			double latitude = Double.parseDouble(coordinates[0]);
    			double longitude = Double.parseDouble(coordinates[1]);
    		    
    		    Point2D.Double newPoint = new Point2D.Double(longitude,latitude);
    		    
    		    polygonPoints.add(newPoint);
    		}
        	
        	for (GardaStation next_station: gardaStations) {
	        	
				Point2D.Double stationPoint = new Point2D.Double(next_station.latitude, next_station.longitude);
				if (IsPointWithinPolygon(stationPoint, polygonPoints)){
					stationsInCounty.add(next_station);
				}
	        }
    		
        }
		
		return stationsInCounty;
	}
	
	/*
	 * NB) The code for IsPointWithinPolygon and its helpers (doIntersect, orientation, onSegment, max and min) are adapted from:
	 * 
	 * 		http://www.geeksforgeeks.org/how-to-check-if-a-given-point-lies-inside-a-polygon/
	 * 
	 * The code for these methods is avaiable under the Creative Commons Legal Code.
	 * 
	 * 		http://www.geeksforgeeks.org/how-to-check-if-a-given-point-lies-inside-a-polygon/
	 * 
	 */

	
	public static boolean IsPointWithinPolygon(Point2D.Double p, ArrayList<Point2D.Double> polygon)
    {
    	// Create extreme point
	    Point2D.Double extreme = new Point2D.Double(1000, p.y);
	    
	    int n = polygon.size();
	    
	    // Check if line segment from point to extreme intersects line segment from polygon[i] to polygon[next]
	    int count = 0, i = 0;
	    do
	    {
	        int next = (i+1)%n;
	 
	        // Check if the line segment from 'p' to 'extreme' intersects
	        // with the line segment from 'polygon[i]' to 'polygon[next]'
	        if (doIntersect(polygon.get(i), polygon.get(next), p, extreme))
	        {
	            // If the point 'p' is colinear with line segment 'i-next',
	            // then check if it lies on segment. If it lies, return true,
	            // otherwise false
	            if (orientation(polygon.get(i), p, polygon.get(next)) == 0)
	               return onSegment(polygon.get(i), p, polygon.get(next));
	 
	            count++;
	        }
	        i = next;
	    } while (i != 0);
	 
	    
	 // Return true if count is odd, false otherwise
	    return (count%2 == 1);  // Same as (count%2 == 1)
    }

	 // The function that returns true if line segment 'p1q1'
	 // and 'p2q2' intersect.
	 public static boolean doIntersect(Point2D.Double p1, Point2D.Double q1, Point2D.Double p2, Point2D.Double q2)
	 {
	     // Find the four orientations needed for general and
	     // special cases
	     int o1 = orientation(p1, q1, p2);
	     int o2 = orientation(p1, q1, q2);
	     int o3 = orientation(p2, q2, p1);
	     int o4 = orientation(p2, q2, q1);
	  
	     // General case
	     if (o1 != o2 && o3 != o4)
	         return true;
	  
	     // Special Cases
	     // p1, q1 and p2 are colinear and p2 lies on segment p1q1
	     if (o1 == 0 && onSegment(p1, p2, q1)) return true;
	  
	     // p1, q1 and p2 are colinear and q2 lies on segment p1q1
	     if (o2 == 0 && onSegment(p1, q2, q1)) return true;
	  
	     // p2, q2 and p1 are colinear and p1 lies on segment p2q2
	     if (o3 == 0 && onSegment(p2, p1, q2)) return true;
	  
	      // p2, q2 and q1 are colinear and q1 lies on segment p2q2
	     if (o4 == 0 && onSegment(p2, q1, q2)) return true;
	  
	     return false; // Doesn't fall in any of the above cases
	 }
	 
	 public static int orientation(Point2D.Double p, Point2D.Double q, Point2D.Double r)
	 {
		 double val = (q.y - p.y) * (r.x - q.x) -
		              (q.x - p.x) * (r.y - q.y);
		 
		 if (val == 0) return 0;  // colinear
		 return (val > 0)? 1: 2; // clock or counterclock wise
	}
	 
	 public static boolean onSegment(Point2D.Double p, Point2D.Double q, Point2D.Double r)
	 {
		    if (q.x <= max(p.x, r.x) && q.x >= min(p.x, r.x) &&
		            q.y <= max(p.y, r.y) && q.y >= min(p.y, r.y))
		        return true;
		    return false;
		}

	public static double max(double x, double y) {
		if (x > y) 
			return x;
		else
			return y;
	}
	
	public static double min(double x, double y) {
		if (x < y) 
			return x;
		else
			return y;
	}
	
	/*
	 * NB) The code for IsPointWithinPolygon and its helpers (doIntersect, orientation, onSegment, max and min) are adapted from:
	 * 
	 * 		http://www.geeksforgeeks.org/how-to-check-if-a-given-point-lies-inside-a-polygon/
	 * 
	 * The code for these methods is avaiable under the Creative Commons Legal Code.
	 * 
	 * 		http://www.geeksforgeeks.org/how-to-check-if-a-given-point-lies-inside-a-polygon/
	 * 
	 */

}
