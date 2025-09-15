
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WordConfig {

	private List<String> knownWords = Arrays.asList("apple", "brave", "crate", "dwarf", "eagle", "flute", "glide");
	private String targetWord;

	public WordConfig() {
		try {
			knownWords = Files.readAllLines(Paths.get("words.txt")).stream().map(s -> s.trim()).filter(s -> !s.isEmpty()).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startGame() {
		Random rand = new Random();
		targetWord = knownWords.get(rand.nextInt(knownWords.size())).toLowerCase();
		System.out.println(targetWord);
		targetWord = "robot"; 
	}

	public boolean isValidWord(String word) {
		return knownWords.contains(word.toLowerCase());
	}

	public String getTargetWord() {
		return targetWord;
	}
}
