import java.util.ArrayList;
import java.util.List;

interface Stock {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

interface Observer {
    void update(String stockName, double price);
}

class StockMarket implements Stock {

    private List<Observer> observers = new ArrayList<>();
    private String stockName;
    private double price;

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(stockName, price);
        }
    }

    public void setStockPrice(String stockName, double price) {
        this.stockName = stockName;
        this.price = price;
        System.out.println("Stock Updated: " + stockName + " = ₹" + price);
        notifyObservers();
    }
}

class MobileApp implements Observer {

    @Override
    public void update(String stockName, double price) {
        System.out.println("Mobile App: " + stockName + " price updated to ₹" + price);
    }
}

class WebApp implements Observer {

    @Override
    public void update(String stockName, double price) {
        System.out.println("Web App: " + stockName + " price updated to ₹" + price);
    }
}

public class ObserverPatternDemo {

    public static void main(String[] args) {

        StockMarket stockMarket = new StockMarket();

        Observer mobile = new MobileApp();
        Observer web = new WebApp();

        stockMarket.registerObserver(mobile);
        stockMarket.registerObserver(web);

        stockMarket.setStockPrice("TCS", 3560.50);

        System.out.println();

        stockMarket.removeObserver(web);

        stockMarket.setStockPrice("Infosys", 1495.75);
    }
}
