package it.polito.tdp.formulaone.model;

public class CostruttoreConPunti implements Comparable<CostruttoreConPunti>{
	
	private Constructor c;
	private int punti;
	public CostruttoreConPunti(Constructor c, int punti) {
		super();
		this.c = c;
		this.punti = punti;
	}
	public Constructor getC() {
		return c;
	}
	public int getPunti() {
		return punti;
	}
	@Override
	public int compareTo(CostruttoreConPunti arg0) {
		// TODO Auto-generated method stub
		return this.punti-arg0.punti;
	}
	
	

}
