package model;

import exception.InvalidOrderException;

public class Position {

    private final String ticker;
    private int quantity;
    private double averagePrice;

    public Position(String ticker, int quantity, double averagePrice){
        if (ticker == null || ticker.isBlank()) throw new InvalidOrderException("Пусто");
        if (quantity <= 0) throw new InvalidOrderException("Минимум 1 акция");
        if (averagePrice <= 0) throw new InvalidOrderException("Цена олжна быть положительной");

        this.ticker = ticker;
        this.quantity = quantity;
        this.averagePrice = averagePrice;

    }

    public String getTicker() {
        return ticker;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getAveragePrice() {
        return averagePrice;
    }


    public void addShares(int amount, double price){
        averagePrice = (quantity * averagePrice + amount * price) / (quantity + amount);
        quantity+=amount;
    }

    public void removeShares(int amount){
        if ( amount > quantity) throw new InvalidOrderException("нельзя продать больше, чем есть");
        quantity-=amount;
    }

    public double getValue(double currentPrice){
        return quantity * currentPrice;
    }

    public double getProfit(double currentPrice){
        return (currentPrice - averagePrice) * quantity;
    }

    @Override
    public String toString(){
        return getTicker() + ": " + getQuantity() + " шт. по $" + getAveragePrice() + " (средняя)";
    }


}
