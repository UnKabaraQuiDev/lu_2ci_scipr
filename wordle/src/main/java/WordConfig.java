
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WordConfig {

	private List<String> knownWords = Arrays.asList("apple", "brave", "crate", "dwarf", "eagle", "flute", "glide");
	private String targetWord;

	public void startGame() {
		Random rand = new Random();
		targetWord = knownWords.get(rand.nextInt(knownWords.size())).toLowerCase();
		System.out.println(targetWord);
	}

	public boolean isValidWord(String word) {
		return knownWords.contains(word.toLowerCase());
	}

	public String getTargetWord() {
		return targetWord;
	}

}
