import java.util.Scanner;

public class StockMarket{
    public static void main(String[] args){
        System.out.println("Welcome to StockMarketSimulator!");
        StockMarket market = new StockMarket();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you ready for the Opening Bell? (Y/N)");
        String userInput = scanner.nextLine();
        if(userInput.equals("Y")){
            while(userInput.equals("Y")){
                market.simulateMarket();
                System.out.println("Are you ready for the next day? (Y/N)");
                userInput = scanner.nextLine();
            }
        }
    }

    public boolean coinFlip(){
        double rand = Math.random();
        if(rand < 0.5)
            return true;
        else
            return false;
    }

    public void simulateMarket(){
        int numOfFlips = 30;
        boolean[] sequence = new boolean[numOfFlips];
        for(int i = 0; i < numOfFlips; ++i){
            boolean bool = coinFlip();
            sequence[i] = bool;
        }
        display(sequence, numOfFlips);
    }

    public void display(boolean[] sequence, int numOfFlips){
        char[][] screen = new char[((numOfFlips-1)*2)+1][numOfFlips];
        
        for(int i = 0; i < screen.length - 1; ++i){
            for(int j = 0; j < screen[i].length - 1; ++j){
                screen[i][j] = '*';
            }
        }

        int level = numOfFlips - 1;
        for(int i = 0; i < screen[level].length - 1; ++i){
            if(sequence[i]){
                if(i != 0 && sequence[i] == sequence[i - 1])
                    --level;
                screen[level][i] = '/';
            } else {
                if(i != 0 && sequence[i] == sequence[i - 1])
                    ++level;
                screen[level][i] = '\\';
            }
        }

        for(int i = 0; i < screen.length - 1; ++i){
            for(int j = 0; j < screen[i].length - 1; ++j){
                System.out.print(screen[i][j]);
            }
            System.out.println();
        }

    }
}