import java.math.BigInteger;
import java.time.Year;

public class LFSR {
    private int[] registers;
    private int[] tapPos;
    private BigInteger seed;

    public LFSR(){
        this.registers = new int[4];
        this.tapPos = new int[]{2,3};
        this.seed = new BigInteger("9");

        setLocalVars();
    }

    public LFSR(int numBits, int[] tapPos, String seed){
        this.registers = new int[numBits];
        this.tapPos = tapPos;
        this.seed = new BigInteger(seed, 16);

        setLocalVars();
    }

    public int generate(){
        int discardedBit;
        int tempBit = 0;
        int newBit;
        
        for(int i = tapPos.length - 2; i >= 0; --i){
            if(i == tapPos.length - 2)
                tempBit = registers[tapPos[i+1]] ^ registers[tapPos[i]];
            else{
                tempBit = tempBit ^ registers[tapPos[i]];
            }
        }
        newBit = tempBit;
        discardedBit = registers[registers.length-1];

        for(int i = registers.length - 2; i >= 0; --i){
            registers[i+1] = registers[i];
        }

        registers[0] = newBit;

        return discardedBit;
    }

    private void setLocalVars(){
        String seedString = this.seed.toString(2);
        int j = 1;
        for(int i = seedString.length() - 1; i >= 0; --i){
            this.registers[this.registers.length-j] = seedString.charAt(i) - '0';
            ++j;
        }
    }
}
