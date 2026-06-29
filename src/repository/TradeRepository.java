package repository;

import model.Trade;
import model.TradeType;
import order.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import service.*;

public class TradeRepository {

    private final String fileName;

    public TradeRepository(String fileName){
        this.fileName = fileName;
    }

    public void save(List<Trade> trades){
        List<String> lines = trades.stream()
                .map(t -> t.getTicker() + ";" + t.getType().name() + ";" + t.getQuantity()
                        + ";" + t.getPrice() + ";" + t.getTimestamp())
                .collect(Collectors.toList());
        try {
            Files.write(Paths.get(fileName), lines);
        } catch (IOException e){
            System.out.println("Ошибка сохранения: " + e.getMessage());
        }
    }

    public List<Trade> load(){
        List<Trade> trades = new ArrayList<>();

        if (!Files.exists(Paths.get(fileName))) {
            return trades;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line : lines) {
                String[] parts = line.split(";");

                String ticker = parts[0];
                TradeType type = TradeType.valueOf(parts[1]);
                int quantity = Integer.parseInt(parts[2]);
                double price = Double.parseDouble(parts[3]);
                long timestamp = Long.parseLong(parts[4]);

                Trade trade = new Trade(ticker, type, quantity, price, timestamp);
                trades.add(trade);
            }
        } catch (IOException e){
            System.out.println("Ошибка загрузки: " + e.getMessage());
        }

        return trades;
    }



}
