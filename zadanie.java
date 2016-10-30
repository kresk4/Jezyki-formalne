public class zadanie {
  
  private static void finiteAutomatonMatcher(String t, String pattern, int[][] delta, int m, String alphabet) {
    int n = t.length();
    int[] arr = new int[n];
    int q = 0;
    int j = 0;
    for (int i = 0; i < n; i++) {
      char letter = t.charAt(i);
      int letterIndex = alphabet.indexOf(letter);
      q = delta[q][letterIndex];
      if (j == pattern.length()) {
        if (t.charAt(i-1) == pattern.charAt(0)){
          j = 1;
        }
        else {
          j = 0;
        }
      }
      if (letter == pattern.charAt(j)) {
        j++;
      } else {
        j = 0;
        if (letter == pattern.charAt(j)) {
          j = 1;
        }
      }
      System.out.println("letter = " + letter + " j = " + j);

      if(j == pattern.length()) {
        arr[i-j +1 ] = 1;
      }
      if (m == q) {
        if (pattern.length() == 1 && j == 1) {
          arr[i] = 1;
        } else if (pattern.length() > 1) {
          int s = i-m+1;
          if (s+pattern.length() > t.length()) {
            continue;
          } else if (t.substring(s, s+pattern.length()).equals(pattern)) {
            arr[s] = 1;
          }
        }
      } 

    }
    for (int i = 0; i < n; i++) {
      if(arr[i] == 1) {
        System.out.println("Wzorzec występuje z przesunięciem " + i);
      }
    }
  }
  
  private static int[][] computeTransitionFunction(String pattern, String alphabet) {
    int a = alphabet.length();
    int m = pattern.length();
    int[][] delta = new int[m][a];
    for (int q = 0; q < m; q++) {
      for (int i = 0; i < a; i++) {
        int k = min(m+1, q+2);
        do {
          k--;
        } while (properSubset(pattern, alphabet, i, k, q) && k > 0);
        delta[q][i] = k;
      }
    }
    return delta;
  }
  private static boolean properSubset(String pattern, String alphabet, int i, int k, int q) {
    String pq = pattern + alphabet.charAt(i);
    String pk = pattern;
    if (k < pk.length()) {
      pk = pattern.substring(0, k+1);
    }
    if (q < pq.length()) {
      pq = pattern.substring(0, q+1) + alphabet.charAt(i);
    }
    if (pk.length() == pq.length()) {
      if (pq.charAt(pq.length()-1) == pk.charAt(pq.length()-1) && pq.contains(pk)) {
        return false;
      }
    }
    return pq.contains(pk);
  }
  
  private static int min(int a, int b) {
    if (a > b) {
      return b;
    }
    return a;
  }
  
  private static String createAlphabet() {
    String alphabet = "ĄĆĘŁŃÓŚŻŹąćęłńóśżź";
    for (int i = 33; i < 127; i++) {
      alphabet += (char)i;
    }
    return alphabet;
  }
  
  public static void main(String[] args) {
    if (args.length != 2) {
      System.err.println("\nWymagane są dwa parametry:\n  pattern = args[0];\n  text = args[1].\n");
      return;
    }
    String pattern = args[0];
    String text = args[1];
    String alphabet = createAlphabet();
    int[][] delta = computeTransitionFunction(pattern, alphabet);
    finiteAutomatonMatcher(text, pattern, delta, pattern.length()-1, alphabet);
  }
}
