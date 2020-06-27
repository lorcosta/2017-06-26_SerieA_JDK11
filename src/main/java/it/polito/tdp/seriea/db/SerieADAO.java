package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;

public class SerieADAO {

	public List<Season> listSeasons() {
		String sql = "SELECT season, description FROM seasons";
		List<Season> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Season(res.getInt("season"), res.getString("description")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Team> listTeams(Map<String,Team> idMapTeam) {
		String sql = "SELECT team FROM teams";
		List<Team> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Team t=new Team(res.getString("team"));
				result.add(t);
				if(!idMapTeam.containsKey(t.getTeam())) {
					idMapTeam.put(t.getTeam(), t);
				}
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Adiacenza> getAdiacenze(Map<String,Team> idMapTeam){
		String sql="SELECT t1.team as t1, t2.team as t2, COUNT(*) as peso " + 
				"FROM matches m, teams t1, teams t2 " + 
				"WHERE t1.team<>t2.team AND (m.HomeTeam=t1.team OR m.AwayTeam=t1.team) AND (m.HomeTeam=t2.team OR m.AwayTeam=t2.team) " + 
				"GROUP BY t1.team, t2.team";
		List<Adiacenza> adiacenze=new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Adiacenza a=new Adiacenza(idMapTeam.get(res.getString("t1")),idMapTeam.get(res.getString("t2")),res.getDouble("peso"));
				adiacenze.add(a);
			}

			conn.close();
			return adiacenze;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}

