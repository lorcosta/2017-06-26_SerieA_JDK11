package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.seriea.db.Adiacenza;
import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	private Graph<Team,DefaultWeightedEdge> graph;
	private SerieADAO dao=new SerieADAO();
	private Map<String,Team> idMapTeam;
	private Simulator sim;
	public Model() {
		this.idMapTeam=new HashMap<>();
		this.sim=new Simulator();
	}
	public Graph<Team,DefaultWeightedEdge> creaGrafo() {
		this.graph=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		List<Team> teams=dao.listTeams(idMapTeam);
		Graphs.addAllVertices(this.graph, teams);
		List<Adiacenza> adiacenze=dao.getAdiacenze(idMapTeam);
		for(Adiacenza a:adiacenze) {
			if(!this.graph.containsEdge(a.getT1(),a.getT2()) && !a.getT1().equals(a.getT2()) &&
					this.graph.containsVertex(a.getT1()) && this.graph.containsVertex(a.getT2())) {
				Graphs.addEdgeWithVertices(this.graph, a.getT1(), a.getT2(), a.getPeso());
			}
		}
		System.out.println("Vertici: "+this.graph.vertexSet().size());
		System.out.println("Archi: "+this.graph.edgeSet().size());
		return this.graph;
	}
	public Integer getNumVertici() {
		return this.graph.vertexSet().size();
	}
	public Integer getNumArchi() {
		return this.graph.edgeSet().size();
	}
	public List<Team> getTeams(){
		return dao.listTeams(idMapTeam);
	}
	public List<Adiacenza> calcolaConnessioni(Team t) {
		List<DefaultWeightedEdge> vicini=new ArrayList<>(this.graph.edgesOf(t));
		List<Adiacenza> a=new ArrayList<>();
		for(DefaultWeightedEdge e:vicini) {
			a.add(new Adiacenza(this.graph.getEdgeSource(e),this.graph.getEdgeTarget(e),this.graph.getEdgeWeight(e)));
		}
		Collections.sort(a);
		return a;
	}
	public List<Season> getSeasons(){
		return dao.listSeasons();
	}
	public Map<Team,Integer> simula(Season s) {
		List<Match> matches=dao.getMatchBySeason(s,this.idMapTeam);
		sim.init(s,matches);
		sim.run();
		return sim.getSquadraTifosi();
	}
}
