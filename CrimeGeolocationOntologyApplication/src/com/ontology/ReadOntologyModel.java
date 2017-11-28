package com.ontology;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

public class ReadOntologyModel {

	public static List<String> queriesAsStrings = new ArrayList<String>();

	public static OntModel loadAllClassesOnt(String localSource) throws FileNotFoundException {
		OntModel model = com.hp.hpl.jena.rdf.model.ModelFactory.createOntologyModel(OntModelSpec.OWL_LITE_MEM, null);
		model.read(new FileInputStream(localSource), null);

		Iterator<?> RootClasses = model.listHierarchyRootClasses();

		while (RootClasses.hasNext()) {
			String RootclassSTR = RootClasses.next().toString();
			System.out.println("ROOTCLASS: " + RootclassSTR);
			OntClass query = model.getOntClass(RootclassSTR);

			for (Iterator<?> i = query.listSubClasses(); i.hasNext();) {
				OntClass c = (OntClass) i.next();
				System.out.println("                SubClass: " + c);
			}

		}
		return model;
	}

	public static List<Query> loadAllQueries(OntModel ontologyModel) {
		for (String queryString : queriesAsStrings) {
			Query query = QueryFactory.create(queryString);
			try (QueryExecution qexec = QueryExecutionFactory.create(query, ontologyModel)) {
				ResultSet results = qexec.execSelect();
				for (; results.hasNext();) {
					QuerySolution soln = results.nextSolution();
					RDFNode x = soln.get("varName"); // Get a result variable by
					// name.
					Resource r = soln.getResource("VarR"); // Get a result
					// variable - must
					// be a resource
					Literal l = soln.getLiteral("VarL"); // Get a result
					// variable - must
					// be a literal
				}
			}
		}
	}

	public static List<String> populateListOfQuestions() {
		List<String> questions = new ArrayList<>();
		questions.add(OntologyConstants.FIRST_QUESTION);
		questions.add(OntologyConstants.SECOND_QUESTION);
		questions.add(OntologyConstants.THIRD_QUESTION);
		questions.add(OntologyConstants.FOURTH_QUESTION);
		questions.add(OntologyConstants.FIFTH_QUESTION);
		questions.add(OntologyConstants.SIXTH_QUESTION);

		return questions;
	}

}
