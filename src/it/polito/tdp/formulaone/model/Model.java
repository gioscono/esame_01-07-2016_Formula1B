package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	
	private FormulaOneDAO dao;
	private SimpleDirectedWeightedGraph<Constructor, DefaultWeightedEdge> grafo;
	
	public Model(){
		dao = new FormulaOneDAO();
	}

	public List<Circuit> getAllCircuits(){
		return dao.getAllCircuits();
	}

	public List<CostruttoreConPunti> creaGrafo(Circuit c) {
		
		grafo = new SimpleDirectedWeightedGraph<Constructor, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		List<Race> gareSulCircuito = dao.getAllRacesForCircuit(c);
		System.out.println(gareSulCircuito.size());
		Set<Constructor> costruttori = new HashSet<>();
		for(Race r : gareSulCircuito){
			costruttori.addAll(dao.getCostruttoriPerRace(r));
		}
		
		Graphs.addAllVertices(grafo, costruttori);
		System.out.println(grafo);
		System.out.println(grafo.vertexSet().size()+"-"+grafo.edgeSet().size());
		
		
		for(Constructor c1: costruttori){
			for(Constructor c2 : costruttori){
				if(!c1.equals(c2)){
					int vittorie = 0;
					for(Race r : gareSulCircuito){
						if(dao.getVittoreC1SuC2(c1, c2, r)>0)
							vittorie +=1; 
					}
					DefaultWeightedEdge a = Graphs.addEdgeWithVertices(grafo, c1, c2, vittorie);
					
					System.out.println(a);
				}
			}
		}
		System.out.println(grafo.vertexSet().size()+"-"+grafo.edgeSet().size());
		List<CostruttoreConPunti> classifica = new ArrayList<>();
		for(Constructor cc : grafo.vertexSet()){
			int punti = 0;
			for(DefaultWeightedEdge a : grafo.outgoingEdgesOf(cc)){
				punti += grafo.getEdgeWeight(a);
			}
			for(DefaultWeightedEdge a : grafo.incomingEdgesOf(cc)){
				punti-=grafo.getEdgeWeight(a);
			}
			classifica.add(new CostruttoreConPunti(cc, punti));
		}
		return classifica;
	}

	public List<Constructor> avviaRicorsione(int k) {
		
		List<Constructor> parziale = new ArrayList<>();
		List<Constructor> finale = new ArrayList<>();
		ricorsione(k, parziale, finale);
		return finale;
		
	}

	private void ricorsione(int k, List<Constructor> parziale, List<Constructor> finale) {
		
		if(parziale.size()==k){
			if(punteggio(parziale)>punteggio(finale)){
				finale.clear();
				finale.addAll(parziale);
			}
			return;
		}
		for(Constructor c : grafo.vertexSet()){
			if(!parziale.contains(c)){
				parziale.add(c);
				
				ricorsione(k, parziale, finale);
				
				parziale.remove(c);
			}
		}
		
	}

	private int punteggio(List<Constructor> parziale) {
		
		int punti = 0;
		for(Constructor c : parziale){
			for(DefaultWeightedEdge a : grafo.outgoingEdgesOf(c)){
				if(!parziale.contains(grafo.getEdgeTarget(a))){
					punti += grafo.getEdgeWeight(a);
				}
			}
		}
		return punti;
	}

}
