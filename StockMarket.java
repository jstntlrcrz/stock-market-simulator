import java.util.ArrayList;
import java.util.Scanner;

public class StockMarket{
    ArrayList<Stock> stocks = new ArrayList<Stock>();

    public StockMarket(){
        stocks.add(new Stock("Alphabet Inc.", "GOOG", 170.82));
        stocks.add(new Stock("Apple Inc.", "AAPL", 234.93));
        stocks.add(new Stock("NVIDIA Corporation", "NVDA", 140.26));
        stocks.add(new Stock("Microsoft Corporation", "MSFT", 431.20));
        stocks.add(new Stock("Amazon.com Inc", "AMZN", 213.44));
        stocks.add(new Stock("Meta Platforms Inc", "META", 613.65));
        stocks.add(new Stock("Tesla Inc", "TSLA", 351.42));
        stocks.add(new Stock("Netflix Inc", "NFLX", 902.17));
        stocks.add(new Stock("Visa Inc", "V", 313.01));
        stocks.add(new Stock("Justin Inc.", "JSTN", 100));
    }

    public void viewStockMarket(){
        System.out.println("----------------------------------");
        System.out.println("           Stock Market           ");
        System.out.println("----------------------------------");

        for(Stock stock : stocks){
            System.out.print(stock.name + " (" + stock.tickerSymbol + ") - $");
            System.out.printf("%.2f\n", stock.price);
        }

        System.out.println("----------------------------------");
    }

    public void viewLineChart(Scanner scanner){
        System.out.println("What stock chart would you like to view? (Please input stock ticker symbol)");
        String tickerSymbol = scanner.nextLine();

        Stock stock = null;
        for(Stock currStock : stocks){
            if(currStock.tickerSymbol.equals(tickerSymbol))
                stock = currStock;
        }

        if(stock == null){
            System.out.println("----------------------------------");
            System.out.println("             FAILURE.             ");
            System.out.println("----------------------------------");
            System.out.println(tickerSymbol + " is not a valid stock.");
            System.out.println("----------------------------------");
        } else {
            stock.displayPriceActionChart();
        }
    }

    public void simulateMarket(LFSR rng){
        for(Stock stock : stocks){
            stock.simulatePriceAction(rng);
        }
    }
}