package ua.nure.sokolov.practice7;

import ua.nure.sokolov.practice7.controller.DOMController;
import ua.nure.sokolov.practice7.entity.Author;
import ua.nure.sokolov.practice7.entity.OldCard;
import ua.nure.sokolov.practice7.entity.OldCards;

import java.util.Collections;
import java.util.Comparator;

public class Sorter {
    public static final String VALID_XML_FILE = "input.xml";

    public static final Comparator<OldCard> SORT_QUESTIONS_BY_QUESTION_TEXT = new Comparator<OldCard>() {
        @Override
        public int compare(OldCard oldCard, OldCard t1) {
            return oldCard.getThema().compareTo(t1.getThema());
        }
    };

    /**
     * Sorts questions by answers number.
     */
    public static final Comparator<OldCard> SORT_QUESTIONS_BY_ANSWERS_NUMBER = new Comparator<OldCard>() {

        @Override
        public int compare(OldCard oldCard, OldCard t1) {
            return oldCard.getType().compareTo(t1.getType());
        }
    };

    /**
     * Sorts answers.
     */
    public static final Comparator<Author> SORT_ANSWERS_BY_CONTENT = new Comparator<Author>() {
        @Override
        public int compare(Author author, Author t1) {
            return author.getName().length() - t1.getName().length();
        }
    };

    /**
     * Sorts answers by correct.
     */
    public static final Comparator<Author> SORT_ANSWERS_BY_CORRECT = new Comparator<Author>() {
        @Override
        public int compare(Author author, Author t1) {
            if (author.isExist() && !t1.isExist()){
                return -1;
            }
            if (t1.isExist() && !author.isExist()){
                return 1;
            }
            return 0;
        }
    };

    // //////////////////////////////////////////////////////////
    // these methods take Test object and sort it
    // with according comparator
    // //////////////////////////////////////////////////////////

    public static final void sortQuestionsByQuestionText(OldCards oldCards) {
        Collections.sort(oldCards.getOldCard(), SORT_QUESTIONS_BY_QUESTION_TEXT);
    }

    public static final void sortQuestionsByAnswersNumber(OldCards oldCards) {
        Collections.sort(oldCards.getOldCard(), SORT_QUESTIONS_BY_ANSWERS_NUMBER);
    }

    public static final void sortAnswersByContent(OldCards oldCards) {
        for (OldCard oldCard: oldCards.getOldCard()) {
            Collections.sort(oldCard.getAuthors(), SORT_ANSWERS_BY_CONTENT);
        }
    }

    public static final void sortAnswersByCorrect(OldCards oldCards) {
        for (OldCard question : oldCards.getOldCard()) {
            Collections.sort(question.getAuthors(), SORT_ANSWERS_BY_CORRECT);
        }
    }

    public static void main(String[] args) throws Exception {
        // Test.xml --> Test object
        DOMController domController = new DOMController(
                VALID_XML_FILE);
        domController.parse(false);
        OldCards oldCards = domController.getOldCards();

        System.out.println("====================================");
        System.out.println(oldCards);
        System.out.println("====================================");

        System.out.println("====================================");
        Sorter.sortQuestionsByQuestionText(oldCards);
        System.out.println(oldCards);
        System.out.println("====================================");

        System.out.println("====================================");
        Sorter.sortAnswersByContent(oldCards);
        System.out.println(oldCards);
    }
}
