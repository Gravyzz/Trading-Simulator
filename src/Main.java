import exception.InvalidStockException;
import model.Sector;
import model.Stock;

public class Main {
    public static void main(String[] args) {

        Stock apple = new Stock("AAPL", "Apple Inc.", Sector.TECH, 150.25);
        System.out.println(apple);                    // AAPL (Apple Inc.) | Технологии | $150.25
        System.out.println(apple.getPrice());         // 150.25

        apple.setPrice(155.50);                        // цена изменилась
        System.out.println(apple.getPrice());         // 155.5

// валидация конструктора
        try {
            new Stock("", "Empty", Sector.TECH, 100);  // пустой тикер
        } catch (InvalidStockException e) {
            System.out.println("Поймали: " + e.getMessage());
        }
        try {
            new Stock("BAD", "Bad Co", Sector.TECH, -5);  // отрицательная цена
        } catch (InvalidStockException e) {
            System.out.println("Поймали: " + e.getMessage());
        }

// валидация setPrice
        try {
            apple.setPrice(-10);                        // отрицательная цена
        } catch (InvalidStockException e) {
            System.out.println("Поймали: " + e.getMessage());
        }

// equals по тикеру
        Stock appleCopy = new Stock("AAPL", "Другое имя", Sector.FINANCE, 999);
        System.out.println(apple.equals(appleCopy));   // true (по ticker!)


    }
}
