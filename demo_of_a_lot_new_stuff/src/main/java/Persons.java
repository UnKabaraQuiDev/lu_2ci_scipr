import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import lu.pcy113.pclib.PCUtils;

public class Persons {

	private List<Person> persons = new ArrayList<Person>();

	public void saveToFile(String filename) throws IOException {
		final PrintWriter out = new PrintWriter(new FileWriter(filename));
		persons.forEach(p -> out.println(p.toString()));
		out.flush();
		out.close();
	}

	public void loadFromFile(String filename) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		persons.clear();
		String line;
		while ((line = in.readLine()) != null) {
			add(new Person(line.split(";")[0], Integer.parseInt(line.split(";")[1])));
		}
		in.close();
	}

	public boolean add(Person p) {
		return persons.add(p);
	}

	@Override
	public String toString() {
		return "Persons [persons=" + persons + "]";
	}

}
