package order;


import exception.InvalidOrderException;

public class LimitOrder extends Order {

    private final double limitPrice;

    public LimitOrder(String ticker, int quantity, double limitPrice){
        super(ticker, quantity);
        if (limitPrice <= 0) throw new InvalidOrderException("Лимитная цена должна быть положительной");
        this.limitPrice = limitPrice;
    }

    @Override
    public boolean canExecute(double currentPrice){
        return currentPrice <= limitPrice;
    }

    @Override
    public double getExecutionPrice(double currentPrice){
        return currentPrice;
    }

    public double getLimitPrice() {
        return limitPrice;
    }
}
