package model;
import exception.InsufficientFundsException;
import exception.InvalidOrderException;

import java.util.*;


public class Portfolio {

    private double balance;
    private final Map<String, Position> positions;


    public Portfolio(double balance){
        if (balance < 0) throw new InvalidOrderException("Баланс не может быть отрицательным");
        this.balance = balance;
        this.positions = new HashMap<>();
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) throw new InvalidOrderException("Сумма должна быть положительной");
        balance += amount;
    }

    public void withdraw(double amount){
        if (amount <= 0) throw new InvalidOrderException("Сумма должна быть положительной");
        if (amount > balance) throw new InsufficientFundsException("Недостаточно средств");
        balance-=amount;
    }

    public Position getPosition(String ticker){
        return positions.get(ticker);
    }

    public boolean hasPosition(String ticker){
        return positions.containsKey(ticker);
    }

    public void addPosition(Position position){
        positions.put(position.getTicker(), position);
    }

    public void removePosition(String ticker){
        positions.remove(ticker);
    }

    public List<Position> getAllPositions(){
        return new ArrayList<>(positions.values());
    }

    @Override
    public String toString(){
        return "Портфель: баланс $" + getBalance() +", позиций: " + positions.size();
    }



}
