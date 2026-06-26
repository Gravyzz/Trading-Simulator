package model;

public enum TradeType {

    BUY("Покупка"),
    SELL("Продажа");

    private final String description;

    TradeType(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}
