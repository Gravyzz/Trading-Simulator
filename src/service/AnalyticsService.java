package service;
import java.util.*;
import java.util.stream.Collectors;

import market.Market;
import model.Portfolio;
import model.Position;


public class AnalyticsService {

    private final Portfolio portfolio;
    private final Market market;

    public AnalyticsService(Portfolio portfolio, Market market){
        this.portfolio =  portfolio;
        this.market = market;
    }

    public double getTotalPortfolioValue(){
        return portfolio.getAllPositions().stream()
                .mapToDouble(p -> p.getValue(getCurrentPrice(p))).sum();
    }

    public double getTotalProfit(){
        return portfolio.getAllPositions().stream()
                .mapToDouble(p -> p.getProfit(getCurrentPrice(p))).sum();
    }


    public List<Position> getPositionsByValue(){
        return portfolio.getAllPositions().stream()
                .sorted(Comparator.comparingDouble((Position p) -> p.getValue(getCurrentPrice(p))).reversed())
                .collect(Collectors.toList());
    }

    public Position getMostValuablePosition(){
        return portfolio.getAllPositions().stream()
                .max(Comparator.comparingDouble((Position p) -> p.getValue(getCurrentPrice(p)) ))   // максимум по стоимости
                .orElse(null);                                            // достать из Optional, или null если пусто
    }

    public List<String> getTickersByProfit(){
        return portfolio.getAllPositions().stream()
                .filter(p -> p.getProfit(getCurrentPrice(p)) > 0)
                .map(Position::getTicker)
                .collect(Collectors.toList());
    }

    private double getCurrentPrice(Position p){
        return market.getStock(p.getTicker()).getPrice();
    }

}
