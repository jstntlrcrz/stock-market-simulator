import java.util.Map;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Scanner;

public class Investor {
    static final int LIQUIDITY_START = 10000;
    static final int WEEKLY_BILL_PAYMENT = 2000;

    double liquidity;
    double investmentAmount;
    double prevInvestmentAmount;
    Map<Stock,ShareInfo> portfolio;

    public Investor(){
        liquidity = LIQUIDITY_START;
        portfolio = new HashMap<Stock,ShareInfo>();
    }

    public boolean buyShares(StockMarket market, Scanner scanner){
        System.out.printf("What stock would you like to buy? (Please input stock ticker symbol) (Total Liquidity: $%.2f)\n", liquidity);
        String tickerSymbol = scanner.nextLine();

        Stock stock = null;
        for(Stock currStock : market.stocks){
            if(currStock.tickerSymbol.equals(tickerSymbol))
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

        System.out.println("How many shares of " + tickerSymbol + " would you like to buy?");
        int numShares = Integer.parseInt(scanner.nextLine());
        
        double transactionAmount = stock.price * numShares;
        if(transactionAmount > liquidity){
            System.out.println("----------------------------------");
            System.out.println("             FAILURE.             ");
            System.out.println("----------------------------------");
            System.out.printf("Insufficient Funds. Total Liquidity is $%.2f but Transaction Amount is $%.2f\n", liquidity, transactionAmount);
            System.out.println("----------------------------------");
            return false;
        }
 
        if(!portfolio.containsKey(stock))
            portfolio.put(stock, new ShareInfo(numShares, stock.price));
        else{
            ShareInfo currShareInfo = portfolio.get(stock);
            currShareInfo.numShares += numShares;
            currShareInfo.totalAmount += numShares * stock.price;
            if(currShareInfo.map.containsKey(stock.price))
                currShareInfo.map.put(stock.price,currShareInfo.map.get(stock.price) + numShares);
            else{
                currShareInfo.map.put(stock.price,numShares);
                currShareInfo.pq.add(stock.price);
            }
        }

        liquidity -= transactionAmount;
        investmentAmount += transactionAmount;
        System.out.println("----------------------------------");
        System.out.println("             SUCCESS.             ");
        System.out.println("----------------------------------");
        return true;
    }

    public boolean sellShares(Scanner scanner){
        System.out.println("What stock would you like to sell? (Please input stock ticker symbol)");
        String tickerSymbol = scanner.nextLine();

        Stock stock = null;
        for(Stock currStock : portfolio.keySet()){
            if(currStock.tickerSymbol.equals(tickerSymbol))
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

        System.out.println("How many shares of " + tickerSymbol + " you like to sell?");
        int numShares = Integer.parseInt(scanner.nextLine());
        
        if(numShares > portfolio.get(stock).numShares){
            System.out.println("----------------------------------");
            System.out.println("             FAILURE.             ");
            System.out.println("----------------------------------");
            System.out.println("Insuffient shares of " + tickerSymbol + " currently owned. Portfolio contains " + portfolio.get(stock).numShares + 
                " while you are requesting to sell " + numShares);
            System.out.println("----------------------------------");
            return false;
        }

        double transactionAmount = stock.price * numShares;
        ShareInfo currShareInfo = portfolio.get(stock);
        currShareInfo.numShares -= numShares;

        if(portfolio.get(stock).numShares == 0)
            portfolio.remove(stock);
        else{
            while(numShares > 0){
                double lowestPrice = currShareInfo.pq.peek();
                if(currShareInfo.map.get(lowestPrice) > numShares){
                    currShareInfo.map.put(lowestPrice,currShareInfo.map.get(lowestPrice) - numShares);
                    currShareInfo.totalAmount -= lowestPrice * numShares;
                    numShares = 0;
                }
                else{
                    int numLowestPriceShares = currShareInfo.map.get(lowestPrice);
                    numShares -= numLowestPriceShares;
                    currShareInfo.totalAmount -= lowestPrice * numLowestPriceShares;
                    currShareInfo.map.remove(lowestPrice);
                    currShareInfo.pq.remove(lowestPrice);
                }
            }
        }

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
            double dollarChange = (stock.price * portfolio.get(stock).numShares) - portfolio.get(stock).totalAmount;
            double percentChange = ((stock.price/(portfolio.get(stock).totalAmount / portfolio.get(stock).numShares)) - 1) * 100;
            System.out.print(stock.name + " (" + stock.tickerSymbol + ") - " + portfolio.get(stock).numShares + " | ");
            System.out.printf("%.2f", dollarChange);
            System.out.print("($) ~ ");
            System.out.printf("%.2f", percentChange);
            System.out.println("(%)");
        }
        System.out.println("----------------------------------");
    }

    public boolean payBills(){
        if(liquidity >= WEEKLY_BILL_PAYMENT){
            liquidity -= WEEKLY_BILL_PAYMENT;
            System.out.println("----------------------------------");
            System.out.println("             SUCCESS.             ");
            System.out.println("----------------------------------");
            System.out.println("$" + WEEKLY_BILL_PAYMENT + " has been deducted from your Liquidity to pay for your weekly bills.");
            System.out.println("----------------------------------");
            return true;
        }
        else{
            System.out.println("----------------------------------");
            System.out.println("             FAILURE.             ");
            System.out.println("----------------------------------");
            System.out.println("You were unable to pay $" + WEEKLY_BILL_PAYMENT + " for your weekly bills.");
            System.out.println("----------------------------------");
            return false;
        }
    }

    public void update(){
        double currInvestmentAmount = 0;
        for(Stock stock : portfolio.keySet()){
            currInvestmentAmount += stock.price * portfolio.get(stock).numShares;
        }
        prevInvestmentAmount = investmentAmount;
        investmentAmount = currInvestmentAmount;
    }

    public class ShareInfo{
        int numShares;
        double totalAmount;
        Map<Double,Integer> map;
        PriorityQueue<Double> pq;

        public ShareInfo(int numShares, double price){
            this.numShares = numShares;
            this.totalAmount += numShares * price;
            this.map = new HashMap<Double,Integer>();
            map.put(price, numShares);
            this.pq = new PriorityQueue<Double>();
            pq.add(price);
        }
    }
}
