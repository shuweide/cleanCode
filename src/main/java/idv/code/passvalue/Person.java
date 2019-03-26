package idv.code.passvalue;

public class Person {
    private String name;
    private long cash = 0L;
    private long debt = 0L;
    private House house;
    
    public Person() {
        house = new House(this);
        house.getInfo();
    }
    
    public String getName() {
        return name;
    }
    
    public long getCash() {
        return cash;
    }
    
    public long getDebt() {
        return debt;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setCash(long cash) {
        this.cash = cash;
    }
    
    public void setDebt(long debt) {
        this.debt = debt;
    }
    
    public void update(){
        house.getInfo();
    }
}
