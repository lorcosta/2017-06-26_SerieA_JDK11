package it.polito.tdp.seriea.model;

public class Classifica implements Comparable<Classifica>{
	private Team squadra;
	private Integer punti;
	/**
	 * @param squadra
	 * @param punti
	 */
	public Classifica(Team squadra, Integer punti) {
		super();
		this.squadra = squadra;
		this.punti = punti;
	}
	public Team getSquadra() {
		return squadra;
	}
	public void setSquadra(Team squadra) {
		this.squadra = squadra;
	}
	public Integer getPunti() {
		return punti;
	}
	public void setPunti(Integer punti) {
		this.punti = punti;
	}
	@Override
	public int compareTo(Classifica other) {
		return this.punti.compareTo(other.punti);
	}
	
}
