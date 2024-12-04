import java.util.Map;
import java.util.HashMap;

public class Investor {
    double liquidity;
    double investmentAmount;
    Map<Stock, Integer> portfolio;

    public Investor(){
        liquidity = 100;
        portfolio = new HashMap<Stock,Integer>();
    }

    public boolean buyShares(StockMarket market, String tickerSymbol, int numShares){
        Stock stock = null;

        for(Stock currStock : market.stocks){
            if(currStock.tickerSymbol == tickerSymbol)
                stock = currStock;
        }

        if(stock == null){
            System.out.println("----------------------------------");
            System.out.println("             FAILURE.             ");
            System.out.println("----------------------------------");
            System.out.println(tickerSymbol + " is not a valid stock.");
            System.out.println("----------------------------------");
            return false;
        }
            

        double transactionAmount = stock.price * numShares;
        if(transactionAmount > liquidity){
            System.out.println("----------------------------------");
            System.out.println("             FAILURE.             ");
            System.out.println("----------------------------------");
            System.out.print("Insufficient Funds. Total Liquidity is $");
            System.out.printf("%.2f", liquidity);
            System.out.print(" but Transaction Amount is $");
            System.out.printf("%.2f", transactionAmount);
            System.out.println();
            System.out.println("----------------------------------");
            return false;
        }

        if(portfolio.containsKey(stock)){
            portfolio.computeIfPresent(stock, (key, val) -> val + numShares);
        } else {
            portfolio.computeIfAbsent(stock, key -> numShares);
        }
        liquidity -= transactionAmount;
        investmentAmount += transactionAmount;
        System.out.println("----------------------------------");
        System.out.println("             SUCCESS.             ");
        System.out.println("----------------------------------");
        return true;
    }

    public boolean sellShares(String tickerSymbol, int numShares){
        Stock stock = null;

        for(Stock currStock : portfolio.keySet()){
            if(currStock.tickerSymbol == tickerSymbol)
                stock = currStock;
        }

        if(stock == null){
            System.out.println("----------------------------------");
            System.out.println("             FAILURE.             ");
            System.out.println("----------------------------------");
            System.out.println(tickerSymbol + " is not a company you are invested in.");
            System.out.println("----------------------------------");
            return false;
        }

        if(numShares > portfolio.get(stock)){
            System.out.println("----------------------------------");
            System.out.println("             FAILURE.             ");
            System.out.println("----------------------------------");
            System.out.println("Insuffient shares of " + tickerSymbol + " currently owned. Portfolio contains " + portfolio.get(stock) + 
                " while you are requesting to sell " + numShares);
            System.out.println("----------------------------------");
            return false;
        }

        double transactionAmount = stock.price * numShares;
        portfolio.computeIfPresent(stock, (key, val) -> val - numShares);
        if(portfolio.get(stock) == 0)
            portfolio.remove(stock);
        liquidity += transactionAmount;
        investmentAmount -= transactionAmount;
        System.out.println("----------------------------------");
        System.out.println("             SUCCESS.             ");
        System.out.println("----------------------------------");
        return true;
    }

    public void viewPortfolio(){
        System.out.println("----------------------------------");
        System.out.println("       Investment Portfolio       ");
        System.out.println("----------------------------------");

        for(Stock stock : portfolio.keySet()){
            System.out.println(stock.name + " (" + stock.tickerSymbol + ") - " + portfolio.get(stock));
        }
        System.out.println("----------------------------------");
    }

    public void update(){
        double currInvestmentAmount = 0;
        for(Stock stock : portfolio.keySet()){
            currInvestmentAmount += stock.price * portfolio.get(stock);
        }
        investmentAmount = currInvestmentAmount;
    }
}
