public class Stock {
    static final int NUM_OF_FLIPS = 200;

    String name;
    String tickerSymbol;
    double prevPrice;
    double price;

    int[] sequence;
    int maxLevel;
    int marketReturn;
    char[][] chart;

    public Stock(String name, String tickerSymbol, double price){
        this.name = name;
        this.tickerSymbol = tickerSymbol;
        this.price = price;
    }

    public void simulatePriceAction(LFSR rng){
        sequence = new int[NUM_OF_FLIPS];
        maxLevel = 0;
        marketReturn = 0;

        for(int i = 0; i < NUM_OF_FLIPS; ++i){
            sequence[i] = rng.generate();
            if(sequence[i] == 1)
                ++marketReturn;
            else
                --marketReturn;

            if(Math.abs(marketReturn) > maxLevel)
                maxLevel = Math.abs(marketReturn);
        }

        prevPrice = price;
        price += price * (marketReturn / 100.0);
        chart = null;
    }

    public void createPriceActionChart(){
        if(chart != null)
            return;

        chart = new char[(maxLevel * 2) + 1][NUM_OF_FLIPS];
        
        for(int row = 0; row < chart.length; ++row){
            for(int col = 0; col < chart[row].length; ++col){
                if(row == maxLevel)
                    chart[row][col] = '-';
                else
                    chart[row][col] = ' ';
            }
        }

        int row = maxLevel;
        for(int col = 0; col < chart[row].length; ++col){
            if(sequence[col] == 1){
                if(col != 0 && sequence[col] == sequence[col - 1])
                    --row;
                chart[row][col] = '/';
            } else {
                if(col != 0 && sequence[col] == sequence[col - 1])
                    ++row;
                chart[row][col] = '\\';
            }
        }
    }

    public void displayPriceActionChart(){
        createPriceActionChart();

        for(int i = 0; i < chart.length; ++i){
            for(int j = 0; j < chart[i].length; ++j){
                System.out.print(chart[i][j]);
            }
            System.out.println();
        }

        System.out.print("Current Share Price for " + name + " (" + tickerSymbol + ") - $");
        System.out.printf("%.2f", price); 
        System.out.print(" | Yesterday's Return: ");
        double priceDiff = price - prevPrice;
        System.out.printf("%.2f", priceDiff);
        System.out.print("($) ~ ");
        System.out.print(marketReturn + ".00");
        System.out.println("(%)");
    }
}
