package ui;

import exception.*;
import market.Market;
import market.PriceUpdater;
import model.Portfolio;
import model.Position;
import model.Trade;
import repository.TradeRepository;
import service.AnalyticsService;
import service.TradingService;
import java.util.*;

public class ConsoleUI {

   private final Market market;
    private final Portfolio portfolio;
    private final TradingService trading;
    private final AnalyticsService analytics;
    private final TradeRepository repository;
    private final PriceUpdater updater;
    private final Scanner scanner;

    public ConsoleUI(Market market, Portfolio portfolio, TradingService trading,
                     AnalyticsService analytics, TradeRepository repository, PriceUpdater updater){
        this.market = market;
        this.portfolio = portfolio;
        this.trading = trading;
        this.analytics = analytics;
        this.repository = repository;
        this.updater = updater;
        this.scanner = new Scanner(System.in);
    }

    public void run(){

        Thread priceThread = new Thread(updater);
        priceThread.start();
        System.out.println("Биржа запущена, цены обновляются в реальном времени...");


        while (true) {
            printMenu();
            String command = scanner.nextLine();

            switch (command) {
                case "1" -> showMarket();
                case "2" -> buyStock();
                case "3" -> sellStock();
                case "4" -> showPortfolio();
                case "5" -> showAnalytics();
                case "6" -> showHistory();
                case "0" -> {
                    updater.stop();
                    repository.save(trading.getTradeHistory());
                    System.out.println("Биржа закрыта. История сохранена. До свидания!");
                    return;
                }
                default -> System.out.println("Неизвестная команда");
            }
        }
    }

    private void printMenu(){
        System.out.println("\n=== Биржевой симулятор ===");
        System.out.println("1 - Показать рынок");
        System.out.println("2 - Купить акции");
        System.out.println("3 - Продать акции");
        System.out.println("4 - Мой портфель");
        System.out.println("5 - Аналитика");
        System.out.println("6 - История сделок");
        System.out.println("0 - Выход");
        System.out.print("Выбор: ");
    }


    private void showMarket(){
        System.out.println("--- Рынок ---");
        market.getAllStocks().forEach(System.out::println);
    }

    private void buyStock(){
        System.out.print("Тикер: ");
        String ticker = scanner.nextLine();
        System.out.print("Количество: ");
        try {
            int quantity = Integer.parseInt(scanner.nextLine());
            trading.buy(ticker, quantity);
            System.out.println("Куплено!");
        } catch (NumberFormatException e) {
            System.out.println("Количество должно быть числом");
        } catch (StockNotFoundException | InsufficientFundsException | InvalidOrderException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void sellStock(){
        System.out.print("Тикер: ");
        String ticker = scanner.nextLine();
        System.out.print("Количество: ");
        try {
            int quantity = Integer.parseInt(scanner.nextLine());
            trading.sell(ticker, quantity);
            System.out.println("Продано!");
        } catch (NumberFormatException e) {
            System.out.println("Количество должно быть числом");
        } catch (StockNotFoundException | InsufficientFundsException | InvalidOrderException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void showPortfolio(){
        System.out.println("--- Портфель ---");
        System.out.println("Баланс: $" + portfolio.getBalance());
        System.out.println("Позиции:");
        List<Position> positions = portfolio.getAllPositions();
        if (positions.isEmpty()) {
            System.out.println("Позиций нет");
        } else {
            positions.forEach(System.out::println);
        }
    }


    private void showAnalytics(){
        System.out.println("--- Аналитика ---");
        System.out.println("Стоимость портфеля: $" + analytics.getTotalPortfolioValue());
        System.out.println("Общая прибыль: $" + analytics.getTotalProfit());
        Position best = analytics.getMostValuablePosition();
        if (best != null) {
            System.out.println("Самая ценная позиция: " + best);
        } else {
            System.out.println("Позиций нет");
        }
        System.out.println("Прибыльные тикеры: " + analytics.getTickersByProfit());
    }

    private void showHistory(){
        System.out.println("--- История сделок ---");
        List<Trade> history = trading.getTradeHistory();
        if (history.isEmpty()) {
            System.out.println("Сделок пока нет");
        } else {
            history.forEach(System.out::println);
        }
    }


}
