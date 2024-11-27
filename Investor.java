public class Investor {
    float amount;

    public Investor(){
        amount = 100;
    }

    public void update(int percent){
        if(percent > 0)
            amount -= amount * (percent / 100.0);
        else if(percent <= 0)
            amount += amount * (Math.abs(percent) / 100.0);
    }
}
