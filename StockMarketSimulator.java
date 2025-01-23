import java.util.Scanner;

public class StockMarketSimulator {
    static StockMarket market = new StockMarket();
    static LFSR rng = new LFSR(128, new int[]{120,125,126,127}, System.currentTimeMillis());
    static Investor investor = new Investor();
    static int day = 1;
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String userInput;
        Boolean endProgram = false;
        Boolean simulateDay = false;

        System.out.println("----------------------------------");
        System.out.println(" Welcome to StockMarketSimulator! ");
        System.out.println("----------------------------------");

        System.out.println("Your objective is to make enough money in the Stock Market to be able to continually pay your bills ($" + Investor.WEEKLY_BILL_PAYMENT + ") by the end of each week. Good luck!\n");


        while(true){
            System.out.println("Good Morning, Investor!");
            if(day != 1 && investor.investmentAmount != 0){
                double dollarChange = Math.abs(investor.investmentAmount - investor.prevInvestmentAmount);
                double percentChange = Math.abs((1 - (investor.investmentAmount / investor.prevInvestmentAmount)) * 100);
                if(investor.investmentAmount > investor.prevInvestmentAmount){
                    System.out.print("Yesterday was a GREEN day for you! Your investments rose ");
                    System.out.printf("%.2f", dollarChange);
                    System.out.print("($) ~ ");
                    System.out.printf("%.2f", percentChange);
                    System.out.print("(%)\n");
                } else if(investor.investmentAmount < investor.prevInvestmentAmount){
                    System.out.print("Yesterday was a RED day for you... Your investments fell -");
                    System.out.printf("%.2f", dollarChange);
                    System.out.print("($) ~ -");
                    System.out.printf("%.2f", percentChange);
                    System.out.print("(%)\n");
                } else
                    System.out.println("Yesterday was a EVEN day for you. Your investments didn't change.");
            }
            System.out.println("Here is what your account looks like on (Day " + day + ")");
            System.out.print("Total Liquidity: $");
            System.out.printf("%.2f\n", investor.liquidity);
            System.out.print("Total Investments: $");
            System.out.printf("%.2f\n", investor.investmentAmount);

            while(true){
                System.out.println("Please select from the following options:");
                System.out.println(" - (B)uy Shares");
                System.out.println(" - (S)ell Shares");
                System.out.println(" - (M)arket View");
                System.out.println(" - (L)ine Chart View");
                System.out.println(" - (P)ortfolio View");
                System.out.println(" - (C)ontinue to Opening Bell");
                System.out.println(" - (E)nd Program");

                userInput = scanner.nextLine();

                switch(userInput){
                    case "B":
                        market.viewStockMarket();
                        investor.buyShares(market, scanner);
                        break;
                    case "S":
                        investor.viewPortfolio();
                        investor.sellShares(scanner);
                        break;
                    case "M":
                        market.viewStockMarket();
                        break;
                    case "L":
                        if(day != 1){
                            market.viewStockMarket();
                            market.viewLineChart(scanner);
                        }
                        else{
                            System.out.println("----------------------------------");
                            System.out.println("             FAILURE.             ");
                            System.out.println("----------------------------------");
                            System.out.println("  There are no charts to display  ");
                            System.out.println("----------------------------------");
                        }
                        break;
                    case "P":
                        investor.viewPortfolio();
                        break;
                    case "C":
                        market.simulateMarket(rng);
                        investor.update();
                        ++day;
                        simulateDay = true;
                        break;
                    case "E":
                        endProgram = true;
                        break;
                    default:
                        System.out.println("Invalid Input. Please Try Again.");
                }

                if(simulateDay){
                    simulateDay = false;
                    break;
                }

                if(endProgram)
                    break;
            }

            if(day % 7 == 0)
                if(!investor.payBills())
                    endProgram = true;

            if(endProgram){
                System.out.println("\nYou made it to Day " + day + ".");
                System.out.println("\"The biggest risk you can take is not taking any risk\" - Mark Zuckerberg");
                System.out.println("Ending Program...");
                scanner.close();
                break;
            }
        }
    }
}
