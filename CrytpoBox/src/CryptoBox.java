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

	public int[][] multiply(int[][] firstMatrix, int[][] secondMatrix) {
		int r1 = firstMatrix.length;
		int c1 = firstMatrix[0].length;
		int r2 = secondMatrix.length;
		int c2 = secondMatrix[0].length;

		int[][] product = new int[r1][c2];
		for (int i = 0; i < r1; i++) {
			for (int j = 0; j < c2; j++) {
				for (int k = 0; k < c1; k++) {
					product[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
				}
			}
		}

		return product;
	}

	public String encodeMessage(String textToEncode) {
		int length = textToEncode.length();
		int columns = (int) Math.ceil(length / 3.0);
		int[][] M = new int[3][columns];

		for (int i = 0; i < length; i++) {
			M[i % 3][i / 3] = (int) textToEncode.charAt(i);
		}

		int[][] B = multiply(C, M);

		StringBuilder encoded = new StringBuilder();
		for (int col = 0; col < B[0].length; col++) {
			for (int row = 0; row < 3; row++) {
				encoded.append(B[row][col]).append(";");
			}
		}

		return encoded.toString();
	}

	public String decodeMessage(String encodedMessage) {
		String[] parts = encodedMessage.split(";");
		int totalNumbers = parts.length;
		int columns = totalNumbers / 3;

		int[][] B = new int[3][columns];
		for (int i = 0; i < parts.length; i++) {
			B[i % 3][i / 3] = Integer.parseInt(parts[i]);
		}

		int[][] M = multiply(invC, B);

		StringBuilder decoded = new StringBuilder();
		for (int col = 0; col < M[0].length; col++) {
			for (int row = 0; row < M.length; row++) {
				int ascii = M[row][col];
				if (ascii != 0) {
					decoded.append((char) ascii);
				}
			}
		}

		return decoded.toString();
	}

}
