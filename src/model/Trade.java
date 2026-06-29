package model;

import exception.InvalidOrderException;

public class Trade {

    private final String ticker;
    private final TradeType type;
    private final int quantity;
    private final double price;
    private final long timestamp;

    public Trade(String ticker, TradeType type, int quantity, double price) {
        this(ticker, type, quantity, price, System.currentTimeMillis());
    }


    public Trade(String ticker, TradeType type, int quantity, double price, long timestamp) {
        if (ticker == null || ticker.isBlank() || type == null || quantity<=0 || price <= 0) throw new InvalidOrderException("Ошибка");

        this.ticker = ticker;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = timestamp;
    }



    public String getTicker() {
        return ticker;
    }

    public double getPrice() {
        return price;
    }

    public TradeType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getTotalCost(){
        return quantity * price;
    }

    @Override
    public String toString(){
        return type.getDescription() + " " + getTicker() + ": " + getQuantity() + " шт. по $" + getPrice() + " = $" + getTotalCost();
    }
}
