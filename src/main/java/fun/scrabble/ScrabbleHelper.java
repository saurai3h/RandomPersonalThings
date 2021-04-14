package fun.scrabble;

import java.io.*;
import java.util.*;
import java.util.stream.Collector;

/**
 * A very simple scrabble helper to generate best words trying to generate maximum score for you.
 * 1. Optimizes only on score generated from available letters. Board's state (and hence the squared lables) is ignored for now.
 * 2. Also, since the board state is unknown, invalid states can be reached if the word overlaps with some other word. So skip those words.
 */
public class ScrabbleHelper {

    /* Generates all permutations for a word. */
    static List<Word> getPermutations(List<Letter> combination) {
        if (combination.size() == 1) {
            return new ArrayList<>(){{add(new Word(combination));}};
        }
        List<Letter> combinationExceptFirst = combination.subList(1, combination.size());
        List<Word> wordsExceptFirst = getPermutations(combinationExceptFirst);
        List<Word> words = new ArrayList<>();
        for (int i = 0 ; i < combination.size() ; ++i) {
            for (Word word : wordsExceptFirst) {
                List<Letter> letters = new ArrayList<>();
                List<Letter> lettersExceptFirst = word.letters;
                for (int j = 0, index = 0; j < combination.size(); ++j) {
                    if (j==i) {
                        letters.add(combination.get(0));
                    } else {
                        letters.add(lettersExceptFirst.get(index));
                        index++;
                    }
                }
                words.add(new Word(letters));
            }
        }
        return words;
    }

    /* Generates all possible words with the given list of letters. */
    // Note that the maximum #letters = 7 in Scrabble so this list is <= 13699 in size.
    static List<Word> getAllWordsPossible(List<Letter> letters) {
        List<Word> words = new ArrayList<>();
       // If there are n letters, number of words possible is nP1 + nP2 + nP3 .. + nPn. (Choose and permute)
        int n = letters.size();
        for (int i = 1; i < (1<<n); ++i) {
            // All the set bits in i represent letters to be chosen.
            int num = i;
            List<Letter> combination = new ArrayList<>();
            int count = 0;
            while (num > 0 && count < n) {
                if (num % 2 == 1) {
                    // Bit is set. Use this letter.
                    combination.add(letters.get(count));
                }
                num /= 2;
                ++count;
            }
            words.addAll(getPermutations(combination));
        }
        return words;
    }

    // Version-1 : args is the array of words which are already on the scrabble board. Terrible way to represent board
    // but hey, wait for next version.
    public static void main(String[] args) throws IOException {

        List<Letter> availableLettersToPlayer = new ArrayList<>() {{
            add(new Letter('N', 1));
            add(new Letter('S', 1));
            add(new Letter('A', 4));
            add(new Letter('I', 1));
            add(new Letter('G', 2));
            add(new Letter('T', 1));
            add(new Letter('A', 1));
        }};

        List<Word> words = getAllWordsPossible(availableLettersToPlayer);
        Set<String> dictionaryWords = getDictionaryWords();

        List<DictionaryWordWithScore> wordsWithScores = new ArrayList<>();
        words.forEach(word -> {
            String strWord =
                    word.letters
                            .stream()
                            .map(letter -> letter.alphabet)
                            .collect(
                                    Collector.of(
                                    StringBuilder::new,
                                    StringBuilder::append,
                                    StringBuilder::append,
                                    StringBuilder::toString));

            for (String boardWord : args) {
                // boardWord is used as a prefix.
                String prefixWord = boardWord + strWord;
                // boardWord is used as a suffix.
                String sufixWord = strWord + boardWord;
                Integer score = word.letters.stream().map(letter -> letter.score).reduce(Integer::sum).get();

                if (dictionaryWords.contains(prefixWord.toLowerCase())) {
                    wordsWithScores.add(new DictionaryWordWithScore(strWord, prefixWord, score));
                }

                if (dictionaryWords.contains(sufixWord.toLowerCase())) {
                    wordsWithScores.add(new DictionaryWordWithScore(strWord, sufixWord, score));
                }
            }


        });
        wordsWithScores.sort((o1, o2) -> o2.score - o1.score);
        wordsWithScores.forEach(wordWithScore -> {
            if (wordWithScore.wordToForm.endsWith("T"))
            System.out.println("Using " + wordWithScore.lettersToSelect + ", made " + wordWithScore.wordToForm + " which gets " + wordWithScore.score + " points.");
        });
    }

    static Set<String> getDictionaryWords() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("data/words.txt")));
        String word;
        Set<String> dictionary = new HashSet<>();
        while ((word = bufferedReader.readLine()) != null) {
            dictionary.add(word);
        }
        return dictionary;
    }

    static class DictionaryWordWithScore {
        String lettersToSelect;
        String wordToForm;
        Integer score;
        DictionaryWordWithScore(String lettersToSelect, String wordToForm, Integer score) {
            this.lettersToSelect = lettersToSelect;
            this.wordToForm = wordToForm;
            this.score = score;
        }
    }

    static class Letter {
        Character alphabet;
        Integer score;
        Letter(Character alphabet, Integer score) {
            this.alphabet = alphabet;
            this.score = score;
        }
    }

    static class Word {
        List<Letter> letters;
        Word(List<Letter> letters) {
            this.letters = letters;
        }
    }
}
