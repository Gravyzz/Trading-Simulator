import exception.InsufficientFundsException;
import exception.InvalidOrderException;
import exception.InvalidStockException;
import exception.StockNotFoundException;
import market.Market;
import model.Portfolio;
import model.Position;
import model.Sector;
import model.Stock;
import service.TradingService;

public class Main {
    public static void main(String[] args) {
        // Подготовка: рынок с акциями + портфель с деньгами
        Market market = new Market();
        market.addStock(new Stock("AAPL", "Apple Inc.", Sector.TECH, 150.0));
        market.addStock(new Stock("SBER", "Сбербанк", Sector.FINANCE, 280.0));

        Portfolio portfolio = new Portfolio(10000.0);
        TradingService trading = new TradingService(market, portfolio);

// ПОКУПКА
        trading.buy("AAPL", 10);                              // купить 10 AAPL по 150 = 1500
        System.out.println(portfolio.getBalance());           // 8500.0 (10000 - 1500)
        System.out.println(portfolio.getPosition("AAPL"));    // AAPL: 10 шт. по $150.0 (средняя)

// ДОКУПКА (цена изменилась)
        market.updatePrice("AAPL", 160.0);
        trading.buy("AAPL", 10);                              // купить ещё 10 по 160 = 1600
        System.out.println(portfolio.getBalance());           // 6900.0 (8500 - 1600)
        System.out.println(portfolio.getPosition("AAPL").getQuantity());     // 20
        System.out.println(portfolio.getPosition("AAPL").getAveragePrice()); // 155.0 (средняя 150 и 160)

// ПРОДАЖА
        trading.sell("AAPL", 5);                              // продать 5 по 160 = 800
        System.out.println(portfolio.getBalance());           // 7700.0 (6900 + 800)
        System.out.println(portfolio.getPosition("AAPL").getQuantity());     // 15

// ИСТОРИЯ
        System.out.println("История сделок:");
        trading.getTradeHistory().forEach(System.out::println);

// ИСКЛЮЧЕНИЯ
        try {
            trading.buy("AAPL", 1000);                        // не хватит денег
        } catch (InsufficientFundsException e) {
            System.out.println("Поймали: " + e.getMessage());
        }
        try {
            trading.sell("GOOGL", 5);                         // нет позиции
        } catch (InvalidOrderException e) {
            System.out.println("Поймали: " + e.getMessage());
        }
        try {
            trading.sell("AAPL", 1000);                       // больше, чем есть
        } catch (InvalidOrderException e) {
            System.out.println("Поймали: " + e.getMessage());
        }
        try {
            trading.buy("XXXX", 5);                           // нет такой акции
        } catch (StockNotFoundException e) {
            System.out.println("Поймали: " + e.getMessage());
        }

    }
}
