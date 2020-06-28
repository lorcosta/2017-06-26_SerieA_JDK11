package it.polito.tdp.seriea.model;

public class Event implements Comparable<Event>{
	private Match match;

	/**
	 * @param match
	 */
	public Event(Match match) {
		super();
		this.match = match;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	@Override
	public int compareTo(Event o) {
		return this.match.getDate().compareTo(o.match.getDate());
	}

	@Override
	public String toString() {
		return match.getHomeTeam()+"-"+match.getAwayTeam()+"-->"+match.getFthg()+"-"+match.getFtag();
	}
	
}
