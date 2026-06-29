package order;

import exception.InvalidOrderException;

public abstract class Order {

    protected final String ticker;
    protected final int quantity;

    public Order(String ticker, int quantity){
        if (ticker == null || ticker.isBlank()) throw new InvalidOrderException("Тикер не указан");
        if (quantity <= 0) throw new InvalidOrderException("Количество должно быть положительным");

        this.ticker = ticker;
        this.quantity = quantity;
    }

    public String getTicker() {
        return ticker;
    }

    public int getQuantity() {
        return quantity;
    }

    public abstract boolean canExecute(double currentPrice);
    public abstract double getExecutionPrice(double currentPrice);

}
