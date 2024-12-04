import java.util.ArrayList;
import java.util.Scanner;

public class StockMarketSimulator {
    static StockMarket market = new StockMarket();
    static LFSR rng = new LFSR(128, new int[]{120,125,126,127}, "29C64A2C16173033338438288147465");
    static Investor investor = new Investor();
    static int day = 1;
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String userInput;
        Boolean end = false;

        System.out.println("--------------------");
        System.out.println("StockMarketSimulator");
        System.out.println("--------------------");

        while(true){
            System.out.println("Good Morning, Investor!");
            System.out.println("Here is what your account looks like on Day " + day);
            System.out.print("Total Liquidity: $");
            System.out.printf("%.2f", investor.liquidity);
            System.out.println();
            System.out.print("Total Investments: $");
            System.out.printf("%.2f", investor.investmentAmount);
            System.out.println();

            System.out.println("Please select from the following options:");
            System.out.println(" - (B)uy Shares");
            System.out.println(" - (S)ell Shares");
            System.out.println(" - (M)arket View");
            System.out.println(" - (P)ortfolio View");
            System.out.println(" - (C)ontinue to Opening Bell");
            System.out.println(" - (E)nd Program");

            userInput = scanner.nextLine();

            switch(userInput){
                case "B":
                    investor.buyShares(market, scanner);
                    break;
                case "S":
                    investor.sellShares(scanner);
                    break;
                case "M":
                    market.viewStockMarket();
                    break;
                case "P":
                    investor.viewPortfolio();
                    break;
                case "C":
                    market.simulateMarket(rng);
                    investor.update();
                    ++day;
                    break;
                case "E":
                    end = true;
                    break;
                default:
                    System.out.println("Invalid Input. Please Try Again.");
            }

            if(day % 7 == 0)
                if(!investor.payBills())
                    end = true;

            if(end){
                System.out.println("You made it to Day " + day + ".");
                System.out.println("Ending Program...");
                scanner.close();
                break;
            }
        }
    }
}
