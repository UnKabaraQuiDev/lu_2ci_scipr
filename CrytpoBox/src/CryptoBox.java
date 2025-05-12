/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author loegu
 */
public class CryptoBox {

	private int[][] C = { { -3, 5, 6 }, { -1, 2, 2 }, { 1, -1, -1 } };
	private int[][] invC = { { 0, 1, 2 }, { -1, 3, 0 }, { 1, -2, 1 } };

	// appends spaces to the given string to get a string length of n
	public String padString(String string, int n) {
		int currentLength = string.length();
		int paddingLength = (n - (currentLength % n)) % n;
		StringBuilder paddedString = new StringBuilder(string);
		for (int i = 0; i < paddingLength; i++) {
			paddedString.append(" ");
		}
		return paddedString.toString();
	}

	public int[][] multiply(int[][] A, int[][] B) {
		int[][] result = new int[A.length][B[0].length];
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < B[0].length; j++) {
				result[i][j] = 0;
				for (int k = 0; k < A[0].length; k++) {
					result[i][j] += A[i][k] * B[k][j];
				}
			}
		}

		return result;
	}

	public String encodeMessage(String textToEncode) {
		int[][] textMatrix = new int[textToEncode.length() / 3][3];
		// fill array with default 32
		for (int i = 0; i < textToEncode.length(); i += 3) {
			textMatrix[i / 3][0] = textToEncode.charAt(i);
			textMatrix[i / 3][1] = textToEncode.charAt(i + 1);
			textMatrix[i / 3][2] = textToEncode.charAt(i + 2);
		}
		// fill the last row with 32
		if (textToEncode.length() % 3 != 0) {
			for (int i = textToEncode.length() / 3; i < textMatrix.length; i++) {
				textMatrix[i][0] = 32;
				textMatrix[i][1] = 32;
				textMatrix[i][2] = 32;
			}
		}

		final int[][] result = multiply(textMatrix, C);
		StringBuilder encodedMessage = new StringBuilder();
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				encodedMessage.append((char) result[i][j]);
			}
		}
		
		return encodedMessage.toString();
	}

	public String decodeMessage(String encodedMessage) {
		String[] split = encodedMessage.split(";");
		return null;
	}

}
