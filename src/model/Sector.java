package model;

public enum Sector {
    TECH("Технологии"),
    FINANCE("Финансы"),
    ENERGY("Энергетика"),
    HEALTHCARE("Здравоохранение"),
    CONSUMER("Потребительский");

    private final String description;

    Sector(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }


}
