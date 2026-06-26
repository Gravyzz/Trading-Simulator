package market;
import java.util.*;
import java.util.stream.Collectors;

import exception.*;
import model.*;

public class Market {

    private final Map<String, Stock> stocks;

    public Market(){
        this.stocks =  new HashMap<>();
    }


    public void addStock(Stock stock){
        stocks.put(stock.getTicker(), stock);
    }

    public Stock getStock(String ticker){
        if (stocks.get(ticker) == null) throw new StockNotFoundException("Акция " + ticker + " не найдена");
        return stocks.get(ticker);
    }

    public void updatePrice(String ticker, double newPrice){
        getStock(ticker).setPrice(newPrice);
    }

    public List<Stock> getAllStocks(){
        return new ArrayList<>(stocks.values());
    }

    public List<Stock> getStocksBySector(Sector sector){
        return stocks.values().stream()
                .filter(s -> s.getSector() == sector)
                .collect(Collectors.toList());
    }

    public int size(){
        return stocks.size();
    }

}
