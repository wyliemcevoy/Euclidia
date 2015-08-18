package euclid.two.dim.team;

import java.util.ArrayList;

import euclid.two.dim.Player;

public class Game {
	private ArrayList<Player> players;

	public Game() {
		this.players = new ArrayList<Player>();
	}

	public void addPlayer(Player player) {
		this.players.add(player);
	}
}
