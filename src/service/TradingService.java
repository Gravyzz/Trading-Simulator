package service;

import exception.InsufficientFundsException;
import exception.InvalidOrderException;
import market.Market;
import model.Portfolio;
import model.Position;
import model.Trade;
import model.TradeType;

import java.util.ArrayList;
import java.util.List;

public class TradingService {

    private final Market market;
    private final Portfolio portfolio;
    private final List<Trade> tradeHistory;

    public TradingService(Market market, Portfolio portfolio){
        this.market = market;
        this.portfolio =  portfolio;
        this.tradeHistory = new ArrayList<>();
    }


    public void buy(String ticker, int quantity){
        if (quantity <= 0) throw new InvalidOrderException("Количество должно быть положительным");
        double price = market.getStock(ticker).getPrice();   // цена ЗА АКЦИЮ (150)
        double cost = price * quantity;                        // СТОИМОСТЬ сделки (1500)

        if (cost > portfolio.getBalance()) throw new InsufficientFundsException("Недостаточно средств");
        portfolio.withdraw(cost);                              // списываем СТОИМОСТЬ

        if (portfolio.hasPosition(ticker)){
            portfolio.getPosition(ticker).addShares(quantity, price);   // передаём ЦЕНУ за акцию
        } else {
            portfolio.addPosition(new Position(ticker, quantity, price)); // ЦЕНА за акцию
        }

        tradeHistory.add(new Trade(ticker, TradeType.BUY, quantity, price));  // ЦЕНА за акцию
    }

    public void sell(String ticker, int quantity){
        if (quantity <= 0) throw new InvalidOrderException("Количество должно быть положительным");
        if (!portfolio.hasPosition(ticker)) throw new InvalidOrderException("Нет позиции по " + ticker);
        Position position = portfolio.getPosition(ticker);
        if (quantity > position.getQuantity()) throw new InvalidOrderException("Недостаточно акций для продажи");
        double price = market.getStock(ticker).getPrice();
        double revenue = price * quantity;
        position.removeShares(quantity);
        if (position.getQuantity() == 0) {portfolio.removePosition(ticker);}
        portfolio.deposit(revenue);
        tradeHistory.add(new Trade(ticker, TradeType.SELL, quantity, price));
    }

    public List<Trade> getTradeHistory(){
        return new ArrayList<>(tradeHistory);
    }

}
