package ValidAnagramProblem;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidAnagram {

    private final String potentialAnagram1;
    private final String potentialAnagram2;

    private final HashMap<Character, Integer> anagramMap1 = new HashMap<>();
    private final HashMap<Character, Integer> anagramMap2 = new HashMap<>();
    

    public static ValidAnagram getValidAnagramProblemInstance(String a1, String a2) {

            if (a1 == null || a2 == null ||
                a1.isEmpty() || a2.isEmpty() ||
                a1.isBlank() || a2.isBlank()
            )
                return null;

        return new ValidAnagram(a1.toLowerCase(), a2.toLowerCase());
    }

    public boolean areAnagrams() {
        if (potentialAnagram1.length() != potentialAnagram2.length())
            return false;

        setMap(potentialAnagram1, anagramMap1);
        setMap(potentialAnagram2, anagramMap2);

        for (char key : anagramMap1.keySet())
            if (!anagramMap2.containsKey(key) ||
                    !anagramMap1.get(key).equals(anagramMap2.get(key)))
                return false;



        return true;
    }

    private void setMap(String str, HashMap<Character, Integer> map) {
        for (char ch : str.toCharArray()) {
            if (map.containsKey(ch)) map.put(ch, map.get(ch) + 1);
            else map.put(ch, 1);
        }
    }


}
