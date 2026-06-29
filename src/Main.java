import market.Market;
import market.PriceUpdater;
import model.*;
import repository.TradeRepository;
import service.AnalyticsService;
import service.TradingService;
import ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        // рынок + акции
        Market market = new Market();
        market.addStock(new Stock("AAPL", "Apple Inc.", Sector.TECH, 150.0));
        market.addStock(new Stock("SBER", "Сбербанк", Sector.FINANCE, 280.0));
        market.addStock(new Stock("GOOGL", "Alphabet", Sector.TECH, 140.0));
        market.addStock(new Stock("GAZP", "Газпром", Sector.ENERGY, 170.0));

        // портфель со стартовым капиталом
        Portfolio portfolio = new Portfolio(10000.0);

        // сервисы
        TradingService trading = new TradingService(market, portfolio);
        AnalyticsService analytics = new AnalyticsService(portfolio, market);
        TradeRepository repository = new TradeRepository("trades.txt");
        PriceUpdater updater = new PriceUpdater(market);

        // собираем UI и запускаем
        ConsoleUI ui = new ConsoleUI(market, portfolio, trading, analytics, repository, updater);
        ui.run();
    }
}