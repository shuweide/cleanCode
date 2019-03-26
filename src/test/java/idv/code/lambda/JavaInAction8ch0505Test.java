package idv.code.lambda;

import idv.code.dto.Trader;
import idv.code.dto.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class JavaInAction8ch0505Test {

    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");
    List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );

    //1. Find all transactions in the year 2011 and sort them by value (small to high).
    @Test
    public void question01() {
        transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .forEach(System.out::println);
    }

    //2. What are all the unique cities where the traders work?
    @Test
    public void question02() {
        transactions.stream().map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .forEach(System.out::println);
    }
    //3. Find all traders from Cambridge and sort them by name.
    @Test
    public void question03(){
//        transactions.stream().filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
//                .sorted(Comparator.comparing(transaction -> transaction.getTrader().getName()))
//                .forEach(System.out::println);

        transactions.stream().map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .forEach(System.out::println);
    }
    //4. Return a string of all traders’ names sorted alphabetically.
    @Test
    public void question04(){
//        transactions.stream()
//                .sorted(Comparator.comparing(transaction -> transaction.getTrader().getName()))
//                .map(transaction -> transaction.getTrader().getName())
//                .distinct()
//                .forEach(System.out::println);

        String tradeNameString = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + n2);
        System.out.println(tradeNameString);
    }
    //5. Are any traders based in Milan?
    @Test
    public void question05(){
        boolean anyTraderfromMilan = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        Assert.assertTrue(anyTraderfromMilan);
    }
    //6. Print all transactions’ values from the traders living in Cambridge.
    @Test
    public void question06(){
        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);
    }
    //7. What’s the highest value of all the transactions?
    @Test
    public void question07(){
        transactions.stream().map(Transaction::getValue).reduce(Integer::max).ifPresent(System.out::println);
    }
    //8. Find the transaction with the smallest value.
    @Test
    public void question08(){
        //1.
        transactions.stream().sorted(Comparator.comparing(Transaction::getValue)).findFirst().ifPresent(System.out::println);
        //2.
        transactions.stream().reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2).ifPresent(System.out::println);
        //3.
        transactions.stream().min(Comparator.comparing(Transaction::getValue)).ifPresent(System.out::println);
    }

}
