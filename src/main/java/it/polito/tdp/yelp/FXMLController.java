/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.yelp;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.yelp.model.Business;
import it.polito.tdp.yelp.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnLocaleMigliore"
    private Button btnLocaleMigliore; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbCitta"
    private ComboBox<String> cmbCitta; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML // fx:id="cmbAnno"
    private ComboBox<Integer> cmbAnno; // Value injected by FXMLLoader

    @FXML // fx:id="cmbLocale"
    private ComboBox<Business> cmbLocale; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	Business b= this.cmbLocale.getValue();
    	double x= Double.parseDouble(this.txtX.getText());
    	String s="";
    	List<Business> business= new LinkedList<>(this.model.percorsoBest(b, x));
    	for(Business bi: business) {
    		s+= bi+"\n";
    	}
    	this.txtResult.appendText(s);
    	
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {

    	this.txtResult.clear();
    	int year= this.cmbAnno.getValue();
    	String city= this.cmbCitta.getValue();
    	this.model.creaGrafo(city, year);

    	this.cmbLocale.getItems().addAll(this.model.getBusinessCity(city));
    	
    	
    	
    	this.txtResult.appendText( this.model.nVertici());
    	this.txtResult.appendText( this.model.nArchi());
    	

    }

    @FXML
    void doLocaleMigliore(ActionEvent event) {
    	
    	this.txtResult.clear();
    	int year= this.cmbAnno.getValue();
    	String city= this.cmbCitta.getValue();
    	
    
    	Business b=this.model.bestLocale();
    	this.txtResult.appendText("LOCALE MIGLIORE: "+b.toString());

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnLocaleMigliore != null : "fx:id=\"btnLocaleMigliore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCitta != null : "fx:id=\"cmbCitta\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbLocale != null : "fx:id=\"cmbLocale\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	for(int i=2005; i<2014; i++) {
    		this.cmbAnno.getItems().add(i);
    	}
    	
    	for(Business b : this.model.getAllBusiness()) {
    		if(!this.cmbCitta.getItems().contains(b.getCity()))
    			this.cmbCitta.getItems().add(b.getCity());
    	
    	}
    	
  
    	
    }
}
