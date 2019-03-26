package idv.code.passvalue;

public class House {
    
    private Person person;
    
    public House(Person person) {
        this.person = person;
    }
    
    public void getInfo(){
        System.out.println(String.format("name:%s,cash:%d,debt:%d", person.getName(), person.getCash(), person.getDebt()));
    }
}
