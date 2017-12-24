package ua.nure.sokolov.practice7.entity;

import java.util.ArrayList;
import java.util.List;

public class OldCards{
    private List<OldCard> oldCard;

    public List<OldCard> getOldCard() {
        if (oldCard == null){
            oldCard = new ArrayList<>();
        }
        return oldCard;
    }

    @Override
    public String toString() {
        if (oldCard == null || oldCard.size() == 0){
            return "OldCards contains no cards";
        }
        StringBuilder results = new StringBuilder();
        for (OldCard oldCard : oldCard){
            results.append(oldCard).append(System.lineSeparator());
        }
        return results.toString();
    }
}
