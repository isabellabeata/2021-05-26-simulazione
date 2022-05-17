package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {

	private YelpDao dao;
	private Graph<Business, DefaultWeightedEdge> grafo;
	private List<Business> businesses;
	private Map<String, Business> idMap;
	private List<Business> bestPercorso;

	public Model() {
		dao= new YelpDao();
		businesses= new LinkedList<Business>(this.dao.getAllBusiness());
		idMap= new HashMap<String, Business>();
	}
	
	public List<Business> getAllBusiness() {
		return this.dao.getAllBusiness();
	}



	public void creaGrafo(String city, int year) {
		
		
		this.grafo= new SimpleDirectedWeightedGraph<Business, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		businesses=dao.getAllBusinessCity(city, year, idMap);

		Graphs.addAllVertices(this.grafo, businesses);
		
		


		for(Business b1 : businesses) {
			for(Business b2: businesses) {
				
				double diffAvg= b1.getMedia()-b2.getMedia();
				if(diffAvg>0 && !(this.grafo.containsEdge(b2, b1))) {	
					
				Graphs.addEdgeWithVertices(this.grafo, b2, b1, diffAvg);

				}
				else if(diffAvg<0 && !(this.grafo.containsEdge(b1, b2))) {
					
					Graphs.addEdgeWithVertices(this.grafo, b1, b2, -(diffAvg));
				}

			}
		}
	}

	public Business bestLocale() {
		
		Business b = null;		
		double pesoE;
		double peso=0.0;

		for(Business bi: this.businesses) {
				pesoE=0;
			
			for(DefaultWeightedEdge e: this.grafo.incomingEdgesOf(bi)) {
				pesoE+=this.grafo.getEdgeWeight(e);
				
			}
			for(DefaultWeightedEdge ei: this.grafo.outgoingEdgesOf(bi)) {
				
				pesoE-=this.grafo.getEdgeWeight(ei);
			}
			
			if(pesoE>peso) {
				peso=pesoE;
				b=bi;
			}

		}

		return b;
	}
	
	public String nVertici() {
		return "Grafo creato!"+"\n"+"#verici: "+ this.grafo.vertexSet().size()+"\n";
	}
	
	public String nArchi() {
		return "#archi: "+ this.grafo.edgeSet().size()+"\n";
	}
	
	public List<Business> getBusinessCity(String city){
		return this.dao.getBusinessCity(city);
	}
	
	public List<Business> percorsoBest(Business b, double x) {
		
		List<Business> parziale= new ArrayList<>();
		this.bestPercorso= new ArrayList<Business>();
		parziale.add(b);
		bestPercorso.add(b);
		cerca_ricorsiva(parziale,b,x);
		
		return bestPercorso;
 
	}

	private void cerca_ricorsiva(List<Business> parziale, Business b, double x) {
		int lunghezzaP=1;
		if(parziale.get(parziale.size()-1).equals(this.bestLocale())){
			if(parziale.size()<lunghezzaP) {
				lunghezzaP=parziale.size();
				bestPercorso=new ArrayList<Business>(parziale);
		}
	}
		//Graphs.successorListOf(this.grafo, b)
		
			for(DefaultWeightedEdge e: this.grafo.outgoingEdgesOf(b)) {
				
				if(this.grafo.getEdgeWeight(e)>x) {
					Business bi= Graphs.getOppositeVertex(this.grafo, e, b);
				parziale.add(bi);
				cerca_ricorsiva(parziale, bi, x);
				parziale.remove(parziale.size()-1);
			}
			
			
		}
		
		
	}

}
