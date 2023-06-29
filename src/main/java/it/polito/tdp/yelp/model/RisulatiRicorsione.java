package it.polito.tdp.yelp.model;

import java.util.List;

public class RisulatiRicorsione {

	
	List<Review> percorsoOttimale;
	long giorniInter;
	
	
	public RisulatiRicorsione(List<Review> percorsoOttimale, long giorniInter) {
		
		this.percorsoOttimale = percorsoOttimale;
		this.giorniInter = giorniInter;
	}


	public List<Review> getPercorsoOttimale() {
		return percorsoOttimale;
	}


	public void setPercorsoOttimale(List<Review> percorsoOttimale) {
		this.percorsoOttimale = percorsoOttimale;
	}


	public long getGiorniInter() {
		return giorniInter;
	}


	public void setGiorniInter(long giorniInter) {
		this.giorniInter = giorniInter;
	}
	
	
	
	
}
