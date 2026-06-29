import exception.InsufficientFundsException;
import exception.InvalidOrderException;
import exception.InvalidStockException;
import exception.StockNotFoundException;
import market.Market;
import model.Portfolio;
import model.Position;
import model.Sector;
import model.Stock;
import service.AnalyticsService;
import service.TradingService;

public class Main {
    public static void main(String[] args) {
        Market market = new Market();
        market.addStock(new Stock("AAPL", "Apple Inc.", Sector.TECH, 150.0));
        market.addStock(new Stock("SBER", "Сбербанк", Sector.FINANCE, 280.0));
        market.addStock(new Stock("GOOGL", "Alphabet", Sector.TECH, 140.0));

        Portfolio portfolio = new Portfolio(100000.0);
        TradingService trading = new TradingService(market, portfolio);

        trading.buy("AAPL", 10);    // 10 × 150 = 1500
        trading.buy("SBER", 5);     // 5 × 280 = 1400
        trading.buy("GOOGL", 20);   // 20 × 140 = 2800

        AnalyticsService analytics = new AnalyticsService(portfolio, market);

// цены изменились (рынок живёт)
        market.updatePrice("AAPL", 170.0);    // AAPL вырос (купили по 150)
        market.updatePrice("SBER", 250.0);    // SBER упал (купили по 280)
        market.updatePrice("GOOGL", 145.0);   // GOOGL чуть вырос

        System.out.println("Стоимость портфеля: " + analytics.getTotalPortfolioValue());
// AAPL: 10×170=1700, SBER: 5×250=1250, GOOGL: 20×145=2900 → 5850.0

        System.out.println("Общая прибыль: " + analytics.getTotalProfit());
// AAPL: (170-150)×10=200, SBER: (250-280)×5=-150, GOOGL: (145-140)×20=100 → 150.0

        System.out.println("Самая ценная позиция: " + analytics.getMostValuablePosition());
// GOOGL (стоимость 2900 — наибольшая)

        System.out.println("Позиции по стоимости:");
        analytics.getPositionsByValue().forEach(System.out::println);
// GOOGL (2900), AAPL (1700), SBER (1250)

        System.out.println("Прибыльные тикеры: " + analytics.getTickersByProfit());
// [AAPL, GOOGL] — у SBER убыток (-150), он не попадёт

    }
}
