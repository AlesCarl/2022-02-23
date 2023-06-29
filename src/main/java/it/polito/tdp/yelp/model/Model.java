package it.polito.tdp.yelp.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	YelpDao dao; 
    private List<Review> allReviews ; 
    private SimpleDirectedWeightedGraph<Review, DefaultWeightedEdge> graph;  // SEMPLICE, PESATO, NON ORIENTATO


	
	
 public Model() {
		
		this.dao= new YelpDao(); 
    	this.graph= new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
    	this.allReviews= new ArrayList<>();
    	//listGradoSimilarita= new ArrayList<>(); 
		
	}
 
 private void loadAllNodes(Business b) {
	 
  this.allReviews= dao.getAllReviewsBusiness(b);
	 
 }
	
 
 
 public void creaGrafo( Business b) {
	 
	 
	 loadAllNodes(b); 
 		//System.out.println("size: " +this.allNACode.size());

		 /** VERTICI */
    	Graphs.addAllVertices(this.graph, allReviews);
 		System.out.println("NUMERO vertici GRAFO: " +this.graph.vertexSet().size());
 		System.out.println("vertici GRAFO: " +this.graph.vertexSet());
 		
 	/*
   collega con le recensioni che hanno una
   data di pubblicazione (colonna review_date, tabella Reviews) più recente*:
   
   
   •gli archi devono essere orientati dalla recensione r verso le recensioni più recenti;
  
   •il peso di ogni arco (sempre positivo) è dato dalla DIFFERENZA, in termini di giorni,
    tra la data di pubblicazione di r e la data di pubblicazione della recensione più recente; 
   
    se tale differenza è 0, l’arco non deve essere inserito.
 		
 	*/
 		
 		
 	
 		
 		/** ARCHI */
 		
 		for(Review r1: this.allReviews) {
	 			for(Review r2: this.allReviews) {
	 				
	 				if(r1.getReviewId().compareTo(r2.getReviewId())!=0) { 
	 					
	 					if(r1.getDate().isBefore(r2.getDate())) {
	 					
	 					double peso=ChronoUnit.DAYS.between(r2.getDate(),  r1.getDate());  
	 					 //double peso=(r2 -r1); 
	 			 				if( peso!=0) {
	 			 					
	 		 						Graphs.addEdgeWithVertices(this.graph, r2, r1, peso);
	 		 						
	 		        }
	 			}
	 		  }
	 		}
	 }
 		System.out.println("NUMERO ARCHI GRAFO: " +this.graph.edgeSet().size());

 
 } 
 
 
 List<Review> listVertexMax ; 
 Set<DefaultWeightedEdge> listArchiUscentiMax;
 
 public Risultato getMaxArchiUscenti() {  //return num archi uscenti max
	
	 int max=0; 
	 listVertexMax = new ArrayList<>();	
	 
	 
	 
	 for(Review rr: this.graph.vertexSet() ) {
		 
		 if( this.graph.outgoingEdgesOf(rr).size() == max) {
			 //ris.getListResult().add(rr);
			 listVertexMax.add(rr); 
		  }
		 
		if( this.graph.outgoingEdgesOf(rr).size() > max) {
			
			 listVertexMax.clear();
			 max= this.graph.outgoingEdgesOf(rr).size(); 
			 listVertexMax.add(rr);
			//listArchiUscentiMax = this.graph.outgoingEdgesOf(rr); 
		 }
	 }
	 
	 Risultato ris= new Risultato(max, listVertexMax); 
	 
	// ris.setListResult(listVertexMax );
	// ris.setTotArchiUscenti(max);
	 
	 /** NON VA BENE COSI **/ 
	 
	 return ris; 
 }

 /**
   
  trovare e stampare la più LUNGA sequenza di recensioni consecutive per cui il 
  punteggio di ogni recensione (colonna stars, tabella Reviews) sia sempre MAGGIORE 
  o UGUALE di quello della recensione precedente. 
  
  
  + STAMPA altro
  */
 
    private List<Review> parziale; 
	private List<Review> percorsoOttimale; 
 
	public RisulatiRicorsione sequezaRecensioniLunga() {
		
	 parziale= new ArrayList<>() ; // qui inseriro' tutti i vari vertici scelti

	 Review rMin=getStarMin();
	 parziale.add(rMin); 
	 
	 
	 percorsoOttimale= new ArrayList<>(); 
	 //int giorniInter=0; 

	 ricorsione(parziale); 
	 
	 Review rEnd= percorsoOttimale.get(percorsoOttimale.size()-1); 
	 long giorniInter = ChronoUnit.DAYS.between( rEnd.getDate(),  rMin.getDate()); 
	 
	 RisulatiRicorsione rRR= new RisulatiRicorsione(percorsoOttimale,giorniInter) ;
	
	 
	 return rRR; 
	}
	
 


private void ricorsione(List<Review> parziale) {
	
	Review current = parziale.get(parziale.size()-1);

	
	/** qui trovo la SOLUZIONE MIGLIORE **/ 
	
	if(parziale.size()>percorsoOttimale.size()) {
		
		this.percorsoOttimale = new ArrayList<>(parziale); 
	}
	

	List<Review> successori= Graphs.successorListOf(graph, current);
	List<Review> newSuccessori= new ArrayList<>();  

	
	for(Review rr: successori) {
	    if(!parziale.contains(rr)) {
	    	newSuccessori.add(rr);  // QUI METTO SOLO I VERTICI CHE NON SONO GIA' STATI USATI
	    }
  }
	
	/** ***  condizione di uscita  ***/
	if(newSuccessori.size()==0) {
		return; 
	}
	
	
	 /** qui trovo le recensioni crescenti  **/ 
	for(Review bb: newSuccessori) {
		
		if(current.getStars()<=bb.getStars()) {
			parziale.add(bb);
			
			ricorsione(parziale ); 
			parziale.remove(bb);
		}
		
	 }
		
	}


private Review getStarMin() {
	Review rMin= null; 
	double starMin=100; 
	
	for(Review rr: this.allReviews) {
		if(rr.getStars()<starMin) {
			starMin= rr.getStars();
			rMin= rr;
		}
	}
	
	return rMin;
}


public List<String> getCitta(){
	 return dao.getAllCities(); 
 }


public List<Business> getLocaliCitta(String citta) {
	return dao.getLocaliCitta(citta); 
	
}


public int getVertici() {
	return this.graph.vertexSet().size();
}

public int getNumEdges() {
	return this.graph.edgeSet().size();
}
	
}
