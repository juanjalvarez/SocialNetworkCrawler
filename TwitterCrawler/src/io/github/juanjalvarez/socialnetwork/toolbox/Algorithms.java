package io.github.juanjalvarez.socialnetwork.toolbox;

import java.util.HashSet;

import twitter4j.User;

/**
 * Collection of algorithms used throughout the application.
 * 
 * @author Juan J. Alvarez <juanalvarez2@mail.usf.edu>
 *
 */
public class Algorithms {

	/**
	 * Yields a string representation of the phonetic code of the given string.
	 * 
	 * @param s
	 *            String to get the phonetic code from.
	 * @return Phonetic code of the given string.
	 */
	public static String soundex(String s) {
		char[] charArray = s.toUpperCase().toCharArray();
		if (charArray.length == 0)
			return "ZERO";
		char firstChar = charArray[0];
		for (int i = 0; i < charArray.length; i++) {
			switch (charArray[i]) {
			case 'B':
			case 'F':
			case 'P':
			case 'V': {
				charArray[i] = '1';
				break;
			}
			case 'C':
			case 'G':
			case 'J':
			case 'K':
			case 'Q':
			case 'S':
			case 'X':
			case 'Z': {
				charArray[i] = '2';
				break;
			}
			case 'D':
			case 'T': {
				charArray[i] = '3';
				break;
			}

			case 'L': {
				charArray[i] = '4';
				break;
			}
			case 'M':
			case 'N': {
				charArray[i] = '5';
				break;
			}
			case 'R': {
				charArray[i] = '6';
				break;
			}
			default: {
				charArray[i] = '0';
				break;
			}
			}
		}
		StringBuilder sb = new StringBuilder();
		if (Character.isAlphabetic(firstChar))
			sb.append(firstChar);
		else
			sb.append('0');
		for (int i = 1; i < charArray.length; i++)
			if (charArray[i] != charArray[i - 1] && charArray[i] != '0')
				sb.append(charArray[i]);
		String result = sb.toString();
		return result.length() < 4 ? result + Utils.strRepeat("0", 4 - result.length())
				: result.length() > 4 ? result.substring(0, 4) : result;
	}

	/**
	 * Generates a string that is the key of a relation between two users. The
	 * key is formed by the lower ID followed by an underscore and then the
	 * larger ID.
	 * 
	 * @param a
	 *            First user.
	 * @param b
	 *            Second user.
	 * @return String key of a relation between two users.
	 */
	public static String generateKey(User a, User b) {
		if (b.getId() > a.getId()) {
			User tmp = a;
			a = b;
			b = tmp;
		}
		return a.getId() + "_" + b.getId();
	}

	/**
	 * Creates a string representing the set of characters in the target string.
	 * 
	 * @param s
	 *            Target string.
	 * @return String representation of the set of characters in the target
	 *         string.
	 */
	public static String eliminateRepeatedCharacters(String s) {
		HashSet<Character> map = new HashSet<Character>();
		for (Character c : s.toCharArray())
			map.add(c);
		StringBuilder sb = new StringBuilder();
		for (Character c : map)
			sb.append(c);
		return sb.toString();
	}

	/**
	 * Finds and yields the longest substring common to both given strings.
	 * 
	 * @param str1
	 *            First string.
	 * @param str2
	 *            Second string.
	 * @return Longest substring common to both given strings.
	 */
	public static String longestCommonSubstring(String str1, String str2) {
		String longest = "", section;
		int x, y;
		for (x = 0; x < str1.length(); x++)
			for (y = x; y < str1.length(); y++) {
				section = str1.substring(x, y + 1);
				if (str2.contains(section) && section.length() > longest.length())
					longest = section;
			}
		return longest;
	}

	/**
	 * Yields the longest common substring name similarity ratio.
	 * 
	 * @param str1
	 *            First string.
	 * @param str2
	 *            Second string.
	 * @return Longest common substring name similarity ratio.
	 */
	public static double LCSStringSimilarity(String str1, String str2) {
		String name1 = str1, name2 = str2;
		String lcs = "";
		while (true) {
			lcs = longestCommonSubstring(name1, name2);
			if (lcs.equals(""))
				break;
			name1 = name1.replace(lcs, "");
			name2 = name2.replace(lcs, "");
		}
		return ((double) (name1.length() + name2.length()) / (double) (str1.length() + str2.length())) / 2.0;
	}

	/**
	 * Compares both given arrays and yields a similarity ratio.
	 * 
	 * @param firstList
	 *            First array.
	 * @param secondList
	 *            Second array.
	 * @return Similarity ratio between both given arrays.
	 */
	public static <E> double listSimilarityTest(E[] firstList, E[] secondList) {
		double hits = 0.0;
		if (firstList == null || secondList == null)
			return hits;
		E[] arr1 = Utils.getSet(firstList);
		E[] arr2 = Utils.getSet(secondList);
		HashSet<E> set = new HashSet<E>();
		for (E e : arr1)
			set.add(e);
		for (E e : arr2)
			if (set.contains(e))
				hits += 1.0;
			else
				set.add(e);
		return (hits / (double) set.size());
	}

	/**
	 * Converts a char array to a Character array.
	 * 
	 * @param arr
	 *            char array to be converted.
	 * @return Character array formed from the given char array.
	 */
	public static Character[] primitiveArrayToWrapperArray(char[] arr) {
		Character[] newArr = new Character[arr.length];
		for (int x = 0; x < arr.length; x++)
			newArr[x] = new Character(arr[x]);
		return newArr;
	}

	/**
	 * Claculates the factorial of any positive number.
	 * 
	 * @param n
	 *            The number which to calculate the factorial of.
	 * @return Factorial of the given number b.
	 */
	public static synchronized long factorial(long n) {
		long total = 0;
		while (n > 0)
			total += n--;
		return total;
	}
}