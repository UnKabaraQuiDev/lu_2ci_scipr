import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		Persons persons = new Persons();

		persons.add(new Person("a", 12));
		persons.add(new Person("b", 13));
		persons.add(new Person("c", 14));
		
		persons.saveToFile("./out.txt");

		persons.loadFromFile("./out.txt");
		
		System.out.println(persons);
	}

}
