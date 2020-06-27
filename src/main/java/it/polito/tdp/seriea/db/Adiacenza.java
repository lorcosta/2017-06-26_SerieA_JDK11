package it.polito.tdp.seriea.db;

import it.polito.tdp.seriea.model.Team;

public class Adiacenza implements Comparable<Adiacenza>{
	private Team t1;
	private Team t2;
	private Double peso;
	public Team getT1() {
		return t1;
	}
	public void setT1(Team t1) {
		this.t1 = t1;
	}
	public Team getT2() {
		return t2;
	}
	public void setT2(Team t2) {
		this.t2 = t2;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	/**
	 * @param t1
	 * @param t2
	 * @param peso
	 */
	public Adiacenza(Team t1, Team t2, Double peso) {
		super();
		this.t1 = t1;
		this.t2 = t2;
		this.peso = peso;
	}
	@Override
	public int compareTo(Adiacenza o) {
		return -this.peso.compareTo(o.getPeso());
	}
	
}
