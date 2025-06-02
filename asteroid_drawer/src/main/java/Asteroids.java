
import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Asteroids {

	ArrayList<Asteroid> alAsteroids = new ArrayList<>();

	public void draw(Graphics g) {
		for (int i = 0; i < alAsteroids.size(); i++) {
			Asteroid get = alAsteroids.get(i);
			get.draw(g);
		}
	}

	public boolean add(Asteroid e) {
		return alAsteroids.add(e);
	}

	public Asteroid getAsteroid(int i) {
		return alAsteroids.get(i);
	}

	public void clear() {
		alAsteroids.clear();
	}

	public Object[] toArray() {
		return alAsteroids.toArray();
	}

	public int size() {
		return alAsteroids.size();
	}

	public void setVisible(int index) {
		for (int i = 0; i < alAsteroids.size(); i++) {
			Asteroid get = alAsteroids.get(i);
			get.editBoool(false);
		}
		Asteroid get = alAsteroids.get(index);
		get.editBoool(true);
	}

	public void saveAsteroids(String filename) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		String data = "";
		for (int i = 0; i < alAsteroids.size(); i++) {
			Asteroid get = alAsteroids.get(i);
			data += get.returnFileData();
			out.println(get.returnFileData());

		}
		out.close();
		System.out.println("Saved " + data + "to file.");
	}

	public void loadAsteroids(String filename) throws FileNotFoundException, IOException {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String line;
		while ((line = in.readLine()) != null) {
			String[] cords = line.split(";");
			Asteroid asteroid = new Asteroid();
			for (int i = 0; i < cords.length; i++) {
				Point point = new Point(Integer.parseInt(cords[i]), Integer.parseInt(cords[i + 1]));
				asteroid.add(point);

				i++;
			}
			alAsteroids.add(asteroid);

		}
	}
}
