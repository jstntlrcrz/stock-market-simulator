import java.util.Scanner;

public class StockMarket{
    public static void main(String[] args){
        StockMarket market = new StockMarket();
        LFSR random = new LFSR();
        Investor investor = new Investor();
        Scanner scanner = new Scanner(System.in);

        System.out.println("--------------------");
        System.out.println("StockMarketSimulator");
        System.out.println("--------------------");
        System.out.println("Good Morning, Investor!");
        System.out.print("Investment Total: $");
        System.out.printf("%.2f", investor.amount);
        System.out.println();
        System.out.println("Are you ready for the Opening Bell? (Y/N)");

        String userInput = scanner.nextLine();
        if(userInput.equals("Y")){
            while(userInput.equals("Y")){
                investor.update(market.simulateMarket(random));

                System.out.print("Investment Total: $");
                System.out.printf("%.2f", investor.amount);
                System.out.println();
                System.out.println("Are you ready for the next day? (Y/N)");

                userInput = scanner.nextLine();
            }
        }
        scanner.close();
    }

    public boolean coinFlip(){
        double rand = Math.random();
        if(rand < 0.5)
            return true;
        else
            return false;
    }

    public int simulateMarket(LFSR random){
        int numOfFlips = 30;
        int[] sequence = new int[numOfFlips];
        int level = 0;
        int maxLevel = 0;

        for(int i = 0; i < numOfFlips; ++i){
            int randomNum = random.generate();
            if(randomNum == 1){
                ++level;
            }
            else{
                --level;
            }

            if(Math.abs(level) > maxLevel)
                maxLevel = Math.abs(level);

            sequence[i] = randomNum;
        }
        
        return display(sequence, maxLevel, numOfFlips);
    }

    public int display(int[] sequence, int maxLevel, int numOfFlips){
        char[][] screen = new char[(maxLevel*2)+1][numOfFlips];
        
        for(int i = 0; i < screen.length; ++i){
            for(int j = 0; j < screen[i].length; ++j){
                if(i == maxLevel)
                    screen[i][j] = '-';
                else
                    screen[i][j] = ' ';
            }
        }

        int level = maxLevel;
        int marketReturn = 0;
        for(int i = 0; i < screen[level].length; ++i){
            if(sequence[i] == 1){
                if(i != 0 && sequence[i] == sequence[i - 1])
                    --level;
                screen[level][i] = '/';
                --marketReturn;
            } else {
                if(i != 0 && sequence[i] == sequence[i - 1])
                    ++level;
                screen[level][i] = '\\';
                ++marketReturn;
            }
        }

        for(int i = 0; i < screen.length; ++i){
            for(int j = 0; j < screen[i].length; ++j){
                System.out.print(screen[i][j]);
            }
            System.out.println();
        }
        
        System.out.print("Today's Return: ");

        if(marketReturn > 0){
            System.out.print("-");
            System.out.println(marketReturn);
        } else if(marketReturn <= 0){
            System.out.print("+");
            System.out.println(Math.abs(marketReturn));
        }

        return marketReturn;
    }
}