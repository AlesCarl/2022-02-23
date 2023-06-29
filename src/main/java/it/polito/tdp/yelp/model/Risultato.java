package it.polito.tdp.yelp.model;

import java.util.List;

public class Risultato {
	
	
	int totArchiUscenti;
	List<Review> listResult;
	
	public Risultato(int totArchiUscenti, List<Review> listResult) {
		super();
		this.totArchiUscenti = totArchiUscenti;
		this.listResult = listResult;
	}

	public int getTotArchiUscenti() {
		return totArchiUscenti;
	}

	public void setTotArchiUscenti(int totArchiUscenti) {
		this.totArchiUscenti = totArchiUscenti;
	}

	public List<Review> getListResult() {
		return listResult;
	}

	public void setListResult(List<Review> listResult) {
		this.listResult = listResult;
	} 
	
	

}
