import exception.InvalidStockException;
import exception.StockNotFoundException;
import market.Market;
import model.Sector;
import model.Stock;

public class Main {
    public static void main(String[] args) {
        Market market = new Market();
        market.addStock(new Stock("AAPL", "Apple Inc.", Sector.TECH, 150.25));
        market.addStock(new Stock("SBER", "Сбербанк", Sector.FINANCE, 280.50));
        market.addStock(new Stock("GOOGL", "Alphabet", Sector.TECH, 140.00));

        System.out.println("Акций на бирже: " + market.size());

        System.out.println(market.getStock("AAPL"));

        market.updatePrice("AAPL", 160.00);
        System.out.println(market.getStock("AAPL").getPrice());

        System.out.println("Все акции:");
        market.getAllStocks().forEach(System.out::println);

        System.out.println("Сектор TECH:");
        market.getStocksBySector(Sector.TECH).forEach(System.out::println);

// исключения
        try {
            market.getStock("XXXX");                               // нет такой
        } catch (StockNotFoundException e) {
            System.out.println("Поймали: " + e.getMessage());
        }
        try {
            market.updatePrice("YYYY", 100);                       // нет такой
        } catch (StockNotFoundException e) {
            System.out.println("Поймали: " + e.getMessage());
        }

// защитное копирование
        market.getAllStocks().clear();                             // чистим полученный список
        System.out.println("После clear, акций всё ещё: " + market.size());  // 3 (оригинал цел!)


    }
}
