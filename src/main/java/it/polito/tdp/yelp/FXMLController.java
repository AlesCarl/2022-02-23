/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.yelp;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.yelp.model.Business;
import it.polito.tdp.yelp.model.Model;
import it.polito.tdp.yelp.model.Review;
import it.polito.tdp.yelp.model.RisulatiRicorsione;
import it.polito.tdp.yelp.model.Risultato;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnMiglioramento"
    private Button btnMiglioramento; // Value injected by FXMLLoader

    @FXML // fx:id="cmbCitta"
    private ComboBox<String> cmbCitta; // Value injected by FXMLLoader

    @FXML // fx:id="cmbLocale"
    private ComboBox<Business> cmbLocale; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    
    @FXML
    void doRiempiLocali(ActionEvent event) {
    	
    	this.cmbLocale.getItems().clear();
    	String citta = this.cmbCitta.getValue();
    	if(citta != null) {

    	cmbLocale.getItems().addAll(model.getLocaliCitta(citta) ); 
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	Business bb= this.cmbLocale.getValue();
    	
    	model.creaGrafo(bb);
    	this.txtResult.appendText("VERTICI E ARCHI: "+model.getVertici()+" -- "+model.getNumEdges());
    	
    	Risultato riss= model.getMaxArchiUscenti(); 
    	this.txtResult.appendText("\nIdReview: "+riss.getListResult());
    	
    	this.txtResult.appendText("\nCon un totale di archi pari a: "+riss.getTotArchiUscenti());


    	
    }

    @FXML
    void doTrovaMiglioramento(ActionEvent event) {
    	
    	RisulatiRicorsione rrC= model.sequezaRecensioniLunga();
    	
    	this.txtResult.appendText("\nSequenza review più grande: "); 

    	for(Review rr: rrC.getPercorsoOttimale()) {
    	this.txtResult.appendText("\n"+rr+" -- "+rr.getStars());
    	}
    	
    	this.txtResult.appendText("\nLunga un totale di giorni "); 
    	this.txtResult.appendText(""+rrC.getGiorniInter());

    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnMiglioramento != null : "fx:id=\"btnMiglioramento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCitta != null : "fx:id=\"cmbCitta\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbLocale != null : "fx:id=\"cmbLocale\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.setCombox();
    }
    
    public void setCombox() {
    	cmbCitta.getItems().addAll(model.getCitta());
    }
}
