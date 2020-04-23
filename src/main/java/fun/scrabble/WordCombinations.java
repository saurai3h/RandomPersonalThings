package fun.scrabble;

import java.io.*;
import java.util.*;
import java.util.stream.Collector;

/**
 * A very simple scrabble helper to generate best words for a given state of the scrabble board.
 * 1. Optimizes only on score generated from available letters. Board's square labels are ignored for now.
 * 2. Also, since the board state is unknown, invalid states can be reached if the word overlaps with some other word.
 */
public class WordCombinations {

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

    // Maximum #letters = 7 so this list is <= 13699 in size.
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

    // args is the array of words which are already on the scrabble board.
    public static void main(String[] args) throws IOException {

        // letters is the list of letters available to the user.
        List<Letter> letters = new ArrayList<>();
        letters.add(new Letter('A', 1));
        letters.add(new Letter('U', 1));
        letters.add(new Letter('O', 1));
        letters.add(new Letter('I', 1));
        letters.add(new Letter('F', 4));
        letters.add(new Letter('S', 1));
        letters.add(new Letter('U', 1));

        List<Word> words = getAllWordsPossible(letters);
        Set<String> dictionaryWords = getDictionaryWords();

        List<InterestingWord> wordsWithScores = new ArrayList<>();
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
                Integer score = word.letters.stream().map(letter -> letter.value).reduce(Integer::sum).get();

                if (dictionaryWords.contains(prefixWord.toLowerCase())) {
                    wordsWithScores.add(new InterestingWord(strWord, prefixWord, score));
                }

                if (dictionaryWords.contains(sufixWord.toLowerCase())) {
                    wordsWithScores.add(new InterestingWord(strWord, sufixWord, score));
                }
            }


        });
        Collections.sort(wordsWithScores, (o1, o2) -> o2.score - o1.score);
        wordsWithScores.forEach(wordWithScore -> System.out.println("Using " + wordWithScore.lettersToSelect + ", made " + wordWithScore.wordToForm + " which gets " + wordWithScore.score + " points."));
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

    static class InterestingWord {
        String lettersToSelect;
        String wordToForm;
        Integer score;
        InterestingWord(String lettersToSelect, String wordToForm, Integer score) {
            this.lettersToSelect = lettersToSelect;
            this.wordToForm = wordToForm;
            this.score = score;
        }
    }

    static class Letter {
        Character alphabet;
        Integer value;
        Letter(Character alphabet, Integer value) {
            this.alphabet = alphabet;
            this.value = value;
        }
    }

    static class Word {
        List<Letter> letters;
        Word(List<Letter> letters) {
            this.letters = letters;
        }
    }
}
