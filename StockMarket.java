import java.util.ArrayList;

public class StockMarket{
    ArrayList<Stock> stocks = new ArrayList<Stock>();

    public StockMarket(){
        stocks.add(new Stock("Alphabet Inc.", "GOOG", 170.82));
        stocks.add(new Stock("Apple Inc.", "AAPL", 234.93));
        stocks.add(new Stock("Justin Inc.", "JSTN", 10));
    }

    public void viewStockMarket(){
        System.out.println("----------------------------------");
        System.out.println("           Stock Market           ");
        System.out.println("----------------------------------");

        for(Stock stock : stocks){
            System.out.print(stock.name + " (" + stock.tickerSymbol + ") - $");
            System.out.printf("%.2f", stock.price);
            System.out.println();
        }

        System.out.println("----------------------------------");
    }

    public void simulateMarket(LFSR rng){
        for(Stock stock : stocks){
            stock.simulatePriceAction(rng);
        }
    }
}