public class Stock {
    String name;
    String tickerSymbol;
    double price;

    public Stock(String name, String tickerSymbol, double price){
        this.name = name;
        this.tickerSymbol = tickerSymbol;
        this.price = price;
    }

    public int simulatePriceAction(LFSR rng){
        int numOfFlips = 30;
        int[] sequence = new int[numOfFlips];
        int level = 0;
        int maxLevel = 0;

        for(int i = 0; i < numOfFlips; ++i){
            int randomNum = rng.generate();
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
        
        return displayPriceAction(sequence, maxLevel, numOfFlips);
    }

    public int displayPriceAction(int[] sequence, int maxLevel, int numOfFlips){
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
        
        System.out.print("Today's Return for " + tickerSymbol + ":");

        if(marketReturn > 0){
            System.out.print("-");
            System.out.println(marketReturn);
        } else if(marketReturn <= 0){
            System.out.print("+");
            System.out.println(Math.abs(marketReturn));
        }

        if(marketReturn > 0)
            price -= price * (marketReturn / 100.0);
        else if(marketReturn <= 0)
            price += price * (Math.abs(marketReturn) / 100.0);

        return marketReturn;
    }
}
