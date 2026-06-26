package model;

import exception.InvalidStockException;

import java.util.Objects;

public class Stock {

    private final String ticker;
    private final String companyName;
    private final Sector sector;

    private double price;

    public Stock(String ticker, String companyName, Sector sector, double price){
        if (ticker == null || ticker.isBlank()) throw new InvalidStockException("Нет кода акции");
        if (companyName == null || companyName.isBlank()) throw new InvalidStockException("Нет названия компании");
        if (sector == null) throw new InvalidStockException("Нет сектора");
        if (price<=0) throw new InvalidStockException("Цена не может быть нулевой/отрицательной");

        this.ticker = ticker;
        this.companyName = companyName;
        this.sector = sector;
        this.price = price;
    }

    public String getTicker() {
        return ticker;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Sector getSector() {
        return sector;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double newPrice) {
        if (newPrice<=0) throw new InvalidStockException("Цена не может быть нулевой/отрицательной");
        this.price = newPrice;
    }

    @Override
    public String toString(){
        return getTicker() + " (" + getCompanyName() + ") " + "| " + getSector().getDescription() + " | $" + getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Stock stock)) return false;
        return Objects.equals(getTicker(), stock.getTicker());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getTicker());
    }
}
