package match;

import core.game.Game;
import core.player.Player;
import test01.player.SixChuan;

import java.util.ArrayList;

public class SimpleClient3 {

    public static void main(String[] args) {

		Player one = new SixChuan();
		Player another = new SixChuan();
		int gameNumbers = 500;
		ArrayList<Game> games = arrangeGames(one, another, gameNumbers);

		Stopwatch stopwatch = new Stopwatch();
		runSingleThread(games);
		//runMultiThread(games);
		System.out.println(stopwatch.elapsedTime());

		System.out.print(one.name() + "(" + one.scores() + "), ");
		System.out.print(another.name() + "(" + another.scores() + "), ");


	}

	private static ArrayList<Game> arrangeGames(Player one, Player another, int gameNumbers){
		Player black = one;
		Player white = another;
		ArrayList<Game> games = new ArrayList<>(gameNumbers);

		for (int i = 0; i < gameNumbers; i++) {
			Player bClone;
			Player wClone;
			try {
				bClone = (Player) black.clone();
				wClone = (Player) white.clone();
				games.add(new Game(bClone, wClone));
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}

			//交换黑白棋
			Player temp;
			temp = black;
			black = white;
			white = temp;
		}
		return games;
	}

	private static void runSingleThread(ArrayList<Game> games) {
		for (Game game:games){
			game.run();
		}
	}

	private static void runMultiThread(ArrayList<Game> games){

		ArrayList<Thread> gameThreads = new ArrayList<>();
    	for (Game game : games){
    		gameThreads.add(game.start());
		}

		for (Thread thread : gameThreads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
