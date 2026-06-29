package order;

public class MarketOrder extends Order {

    public MarketOrder(String ticker, int quantity){
        super(ticker, quantity);
    }

    @Override
    public boolean canExecute(double currentPrice){
        return true;
    }

    @Override
    public double getExecutionPrice(double currentPrice){
        return currentPrice;
    }

}
