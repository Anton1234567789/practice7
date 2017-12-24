package ua.nure.sokolov.practice7.entity;

public class Author {
    private String name;

    private Boolean exist;

    public boolean isExist(){
        if (exist != null){
                return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getExist() {
        return exist;
    }

    public void setExist(Boolean exist) {
        this.exist = exist;
    }
}
