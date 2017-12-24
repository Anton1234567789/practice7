package ua.nure.sokolov.practice7.entity;

import ua.nure.sokolov.practice7.entity.Author;

import java.util.ArrayList;
import java.util.List;

public class OldCard {
    private Boolean sent;

    private String thema;

    private String type;

    private String country;

    private String year;

    private String valuable;

    private List<Author> authors;


    public boolean isSent(){
        if (sent == null){
            return false;
        }
        return sent;
    }

    public List<Author> getAuthors() {
        if (authors == null){
            authors = new ArrayList<>();
        }
        return authors;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder()
                .append(thema).append(System.lineSeparator())
                .append(type).append(System.lineSeparator())
                .append(country).append(System.lineSeparator())
                .append(year).append(System.lineSeparator())
                .append(valuable).append(System.lineSeparator());
        for (Author author : authors){
            result.append(author).append(System.lineSeparator());
        }

        return result.toString();
    }

    public Boolean getSent() {
        return sent;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    public String getThema() {
        return thema;
    }

    public void setThema(String thema) {
        this.thema = thema;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getValuable() {
        return valuable;
    }

    public void setValuable(String valuable) {
        this.valuable = valuable;
    }
}
