package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Simulator {
	private Map<Team,Integer> squadraTifosi;//mappa con squadra e relativo numero di tifosi
	private List<Team> squadre;//DISTINCT squadre della stagione
	private Integer T=1000;//numero di tifosi iniziali in ogni squadra
	private Integer P=10;//percentuale di tifosi di riferimento che si spostano in base allo scarto goal del match
	private PriorityQueue<Event> queue;
	
	public void init(Season s, List<Match> matches) {
		this.squadraTifosi=new HashMap<>();
		this.squadre=new ArrayList<>();
		for(Match m:matches) {
			if(!this.squadre.contains(m.getHomeTeam())) {
				this.squadre.add(m.getHomeTeam());
			}else if(!this.squadre.contains(m.getAwayTeam())) {
				this.squadre.add(m.getAwayTeam());
			}
		}
		for(Team team:this.squadre) {
			this.squadraTifosi.put(team, T);//1000 tifosi ad ogni squadra inizialmente
			team.setPuntiClassifica(0);
		}
		this.queue=new PriorityQueue<>();
		for(Match m:matches) {
			this.queue.add(new Event(m));
		}
	}

	public void run() {
		while(!this.queue.isEmpty()) {
			Event e=this.queue.poll();
			System.out.println(e);
			processEvent(e);
		}
	}

	private void processEvent(Event e) {
		Team home=e.getMatch().getHomeTeam();
		Team away=e.getMatch().getAwayTeam();
		Integer scarto=null,homeGoal=null,awayGoal=null;
		if(this.squadraTifosi.get(home)<this.squadraTifosi.get(away)) {
			Double rand=Math.random();
			Double th_ta=(double) (this.squadraTifosi.get(home)/this.squadraTifosi.get(away));
			if(rand<(1-th_ta)) {
				homeGoal=e.getMatch().getFthg();
				homeGoal--;
				awayGoal=e.getMatch().getFtag();
			}else {
				homeGoal=e.getMatch().getFthg();
				awayGoal=e.getMatch().getFtag();
			}
		}else if(this.squadraTifosi.get(home)>this.squadraTifosi.get(away)) {
			Double rand=Math.random();
			Double ta_th=(double)(this.squadraTifosi.get(away)/this.squadraTifosi.get(home));
			if(rand<(1-ta_th)) {
				awayGoal=e.getMatch().getFtag();
				awayGoal--;
				homeGoal=e.getMatch().getFthg();
			}else {
				homeGoal=e.getMatch().getFthg();
				awayGoal=e.getMatch().getFtag();
			}
		}else {//stesso numero di tifosi
			//la decisione sullo spostamento si basa solo sul risultato finale
			homeGoal=e.getMatch().getFthg();
			awayGoal=e.getMatch().getFtag();
		}
		scarto=homeGoal-awayGoal;
		if(scarto>0) {
			//ha vinto la squadra di casa
			home.setPuntiClassifica(home.getPuntiClassifica()+3);
			//il scarto*this.P% di tifosi della squadra ospite passa a quella di casa
			Integer tifosiIniziali=this.squadraTifosi.get(away);
			Integer spostamento=this.squadraTifosi.get(away)*scarto*this.P;
			if(tifosiIniziali-spostamento>=0) {
				this.squadraTifosi.remove(away);
				this.squadraTifosi.put(away, tifosiIniziali-spostamento);
				tifosiIniziali=this.squadraTifosi.get(home);
				this.squadraTifosi.remove(home);
				this.squadraTifosi.put(home, tifosiIniziali+spostamento);
			}
			
		}else if(scarto<0) {
			//ha vinto la squadra ospite
			away.setPuntiClassifica(away.getPuntiClassifica()+3);
			//il scarto*this.P% di tifosi della squadra di casa passa a quella ospite
			Integer tifosiIniziali=this.squadraTifosi.get(home);
			Integer spostamento=this.squadraTifosi.get(home)*scarto*this.P;
			if(tifosiIniziali-spostamento>=0) {
				this.squadraTifosi.remove(home);
				this.squadraTifosi.put(home, tifosiIniziali-spostamento);
				tifosiIniziali=this.squadraTifosi.get(away);
				this.squadraTifosi.remove(away);
				this.squadraTifosi.put(away, tifosiIniziali+spostamento);
			}
		}else {
			//pareggio, nessun tifoso si muove
			home.setPuntiClassifica(home.getPuntiClassifica()+1);
			away.setPuntiClassifica(away.getPuntiClassifica()+1);
		}
		
	}
	public Map<Team,Integer>getSquadraTifosi() {
		return this.squadraTifosi;
	}
	
}
