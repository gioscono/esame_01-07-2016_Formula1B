package it.polito.tdp.formulaone;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.formulaone.model.Circuit;
import it.polito.tdp.formulaone.model.Constructor;
import it.polito.tdp.formulaone.model.CostruttoreConPunti;
import it.polito.tdp.formulaone.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormulaOneController {
	
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Circuit> boxCircuiti;

    @FXML
    private TextField textInputK;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCreaGrafo(ActionEvent event) {

    	Circuit c = boxCircuiti.getValue();
    	if(c==null){
    		txtResult.appendText("Errore: selezionare un circuito.\n");
    		return;
    	}
    	List<CostruttoreConPunti> classifica = model.creaGrafo(c);
    	Collections.sort(classifica);
    	for(CostruttoreConPunti cc : classifica){
    		txtResult.appendText(cc.getC().getName()+"-"+cc.getPunti()+"\n");
    	}
    }

    @FXML
    void doTrovaDreamTeam(ActionEvent event) {

    	String k = textInputK.getText();
    	try{
    		int kk = Integer.parseInt(k);
    		List<Constructor> costruttori = model.avviaRicorsione(kk);
    		System.out.println(costruttori);
    	}catch(NumberFormatException e){
    		txtResult.appendText("Errore: inserire k valido.\n");
    		return;
    	}
    }

    @FXML
    void initialize() {
        assert boxCircuiti != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK != null : "fx:id=\"textInputK\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FormulaOne.fxml'.";

    }
    
    public void setModel(Model model){
    	this.model = model;
    	boxCircuiti.getItems().addAll(model.getAllCircuits());
    }
}
