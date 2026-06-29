package market;

import model.Stock;

public class PriceUpdater implements Runnable {

    private final Market market;
    private volatile boolean running;

    public PriceUpdater(Market market){
        this.market = market;
        this.running = true;
    }

    @Override
    public void run(){
        while (running){

            for (Stock stock : market.getAllStocks()){
                double currentPrice = stock.getPrice();
                double change = (Math.random() - 0.5) * 0.1;
                double newPrice = Math.max(1, currentPrice * (1 + change));
                market.updatePrice(stock.getTicker(), newPrice);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e){
                running = false;
            }
        }
    }

    public void stop(){
        this.running = false;
    }


}
