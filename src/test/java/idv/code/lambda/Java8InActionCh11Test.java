package idv.code.lambda;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static idv.code.lambda.ExchangeService.Money;
import static idv.code.lambda.ExchangeService.getRate;
import static java.util.stream.Collectors.toList;

public class Java8InActionCh11Test {

    public static void delay() {
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Random random = new Random();

    public static void randomDelay() {

        int delay = 500 + random.nextInt(2000);
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    class Shop {

        private String name;

        Shop(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        private Random random = new Random();

        public double getPrice(String product) {
            return calculatePrice(product);
        }

        public String getPriceDiscount(String product) {
            double price = calculatePrice(product);
            Code code = Code.values()[random.nextInt(Code.values().length)];
            return String.format("%s:%.2f:%s", name, price, code);
        }

        public String getPriceDiscountRandom(String product) {
            double price = calculatePriceRandom(product);
            Code code = Code.values()[random.nextInt(Code.values().length)];
            return String.format("%s:%.2f:%s", name, price, code);
        }

        public Future<Double> getPriceAsync(String product) {
            return CompletableFuture.supplyAsync(() -> calculatePrice(product)); //等於下面的code,只是只有一句
//            CompletableFuture<Double> futurePrice = new CompletableFuture<>();
//            new Thread(() -> {
//                try {
//                    double price = calculatePrice(product);
//                    futurePrice.complete(price);
//                }catch (Exception ex){
//                    futurePrice.completeExceptionally(ex);
//                }
//            }).start();
//            return futurePrice;
        }

        private double calculatePrice(String product) {
            if ("exception".equals(product))
                throw new RuntimeException("product not available!");
            delay();
            return random.nextDouble() * product.charAt(0) + product.charAt(1);
        }

        private double calculatePriceRandom(String product) {
            if ("exception".equals(product))
                throw new RuntimeException("product not available!");
            randomDelay();
            return random.nextDouble() * product.charAt(0) + product.charAt(1);
        }
    }

    @Test
    public void testPriceAsyncNormal() {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
        long invocationTime = ((System.nanoTime() - start));
        System.out.println("Invocation returned after " + invocationTime / 1_000_000 + " msecs");

        try {
            double price = futurePrice.get();
            System.out.printf("Price is %.2f%n", price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        long retrievalTime = System.nanoTime() - start;
        System.out.println("Price returned after " + retrievalTime / 1_000_000 + " msecs");
    }

    @Test(expected = ExecutionException.class)
    public void testPriceAsyncException() throws ExecutionException {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("exception");
        long invocationTime = ((System.nanoTime() - start));
        System.out.println("Invocation returned after " + invocationTime / 1_000_000 + " msecs");

        try {
            double price = futurePrice.get();
            System.out.printf("Price is %.2f%n", price);
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        long retrievalTime = System.nanoTime() - start;
        System.out.println("Price returned after " + retrievalTime / 1_000_000 + " msecs");
    }

    List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"),
            new Shop("Family"),
            new Shop("Seven-Eleven"),
            new Shop("Hi-Life"),
            new Shop("OK")
    );

    private List<String> findPricesStream(String product) {
        return shops.stream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(toList());
    }

    private List<String> findPricesParallelStream(String product) {
        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(toList());
    }

    private List<String> findPricesCompletableFuture(String product) {
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(() ->
                                String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))))
                        .collect(toList());
        return priceFutures.stream().map(CompletableFuture::join).collect(toList());
    }

    private Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100),
            runnable -> {
                Thread t = new Thread(runnable);
                t.setDaemon(true);
                return t;
            });

    //test CompletableFuture combine use getPrice,getRate
    private Executor executor2 = Executors.newFixedThreadPool(Math.min(shops.size() * 2, 100),
            runnable -> {
                Thread t = new Thread(runnable);
                t.setDaemon(true);
                return t;
            });

    private List<String> findPricesCompletableFutureExecutor(String product) {
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(() ->
                                String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)), executor))
                        .collect(toList());
        return priceFutures.stream().map(CompletableFuture::join).collect(toList());
    }

    @Test
    public void testFindPricesStream() {
        //Done in 8015 msecs
        long start = System.nanoTime();
        System.out.println(findPricesStream("iphoneX"));
        System.out.println("Done in " + (System.nanoTime() - start) / 1_000_000 + " msecs");
    }

    @Test
    public void testFindPricesParallelStream() {
        //Done in 2015 msecs
        long start = System.nanoTime();
        System.out.println(findPricesParallelStream("iphoneX"));
        System.out.println("Done in " + (System.nanoTime() - start) / 1_000_000 + " msecs");
    }

    @Test
    public void testFindPricesCompletableFuture() {
        //Done in 3011 msecs
        long start = System.nanoTime();
        System.out.println(findPricesCompletableFuture("iphoneX"));
        System.out.println("Done in " + (System.nanoTime() - start) / 1_000_000 + " msecs");
    }

    @Test
    public void testFindPricesCompletableFuture_SelfDefineExecutor() {
        //Done in 1014 msecs
        long start = System.nanoTime();
        System.out.println(findPricesCompletableFutureExecutor("iphoneX"));
        System.out.println("Done in " + (System.nanoTime() - start) / 1_000_000 + " msecs");
    }

    //add Discount service

    enum Code {
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }

    public class Quote {
        private final String shopName;
        private final double price;
        private final Code discountCode;

        public Quote(String shopName, double price, Code discountCode) {
            this.shopName = shopName;
            this.price = price;
            this.discountCode = discountCode;
        }

        public String getShopName() {
            return shopName;
        }

        public double getPrice() {
            return price;
        }

        public Code getDiscountCode() {
            return discountCode;
        }
    }

    public Quote parse(String s) {
        String[] split = s.split(":");
        String shopName = split[0];
        double price = Double.parseDouble(split[1]);
        Code discountCode = Code.valueOf(split[2]);
        return new Quote(shopName, price, discountCode);
    }

    static class Discount {
        public static String applyDiscount(Quote quote) {
            return quote.getShopName() + " price is " + apply(quote.getPrice(), quote.getDiscountCode());
        }

        public static String applyDiscountRandom(Quote quote) {
            return quote.getShopName() + " price is " + applyRandom(quote.getPrice(), quote.getDiscountCode());
        }

        private static double apply(double price, Code code) {
            delay();
            return new BigDecimal(price * (100 - code.percentage) / 100).doubleValue();
        }

        private static double applyRandom(double price, Code code) {
            randomDelay();
            return new BigDecimal(price * (100 - code.percentage) / 100).doubleValue();
        }
    }

    private List<String> findPricesDiscountStream(String product) {
        return shops.stream()
                .map(shop -> shop.getPriceDiscount(product))
                .map(this::parse)
                .map(Discount::applyDiscount)
                .collect(toList());
    }

    private List<String> findPricesDiscountCompletableFuture(String product) {
        List<CompletableFuture<String>> pricefutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceDiscount(product), executor))
                        .map(future -> future.thenApply(this::parse))
                        .map(future -> future.thenCompose(quote ->
                                CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))
                        .collect(toList());

        return pricefutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }

    @Test
    public void testFindPrices_discount_stream() {
//        Done in 16069 msecs
        long start = System.nanoTime();
        System.out.println(findPricesDiscountStream("iphoneX"));
        System.out.println("Done in " + (System.nanoTime() - start) / 1_000_000 + " msecs");
    }

    @Test
    public void testFindPrices_discount_stream_CompletableFuture() {
        //Done in 2024 msecs
        long start = System.nanoTime();
        System.out.println(findPricesDiscountCompletableFuture("iphoneX"));
        System.out.println("Done in " + (System.nanoTime() - start) / 1_000_000 + " msecs");
    }

    public List<String> findPricesInUSD(String product) {
        // Here, the for loop has been replaced by a mapping function...
        Stream<CompletableFuture<String>> priceFuturesStream = shops
                .stream()
                .map(shop -> CompletableFuture
                        .supplyAsync(() -> shop.getPrice(product), executor2)
                        .thenCombine(
                                CompletableFuture.supplyAsync(() -> getRate(Money.EUR, Money.USD), executor2),
                                (price, rate) -> price * rate)
                        .thenApply(price -> shop.getName() + " price is " + price));
        // However, we should gather the CompletableFutures into a List so that the asynchronous
        // operations are triggered before being "joined."
        List<CompletableFuture<String>> priceFutures = priceFuturesStream.collect(Collectors.toList());
        List<String> prices = priceFutures
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        return prices;
    }

    @Test
    public void testFindPrices_stream_CompletableFuture_combine() {
        //Done in 1014 msecs
        long start = System.nanoTime();
        System.out.println(findPricesInUSD("iphoneX"));
        System.out.println("Done in " + (System.nanoTime() - start) / 1_000_000 + " msecs");
    }

    private Stream<CompletableFuture<String>> findPricesDiscountCompletableFutureStream(String product) {
        Stream<CompletableFuture<String>> pricefutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceDiscountRandom(product), executor))
                        .map(future -> future.thenApply(this::parse))
                        .map(future -> future.thenCompose(quote ->
                                CompletableFuture.supplyAsync(() -> Discount.applyDiscountRandom(quote), executor)));
        return pricefutures;
    }

    //測試不等所有商店都回應才顯示訊息
    @Test
    public void testFindPrices_discount_stream_CompletableFuture_thenAccept() {
        long start = System.nanoTime();
        CompletableFuture[] futures = findPricesDiscountCompletableFutureStream("iphoneX")
                .map(future -> future.thenAccept(s -> System.out.println(s + " (done in " +
                        ((System.nanoTime() - start) / 1_000_000) +
                        " msecs)"))).toArray(CompletableFuture[]::new);

//        LetsSaveBig price is 102.069 (done in 2232 msecs)
//        Hi-Life price is 114.885 (done in 2646 msecs)
//        BestPrice price is 180.03599999999997 (done in 2781 msecs)
//        MyFavoriteShop price is 213.68 (done in 3120 msecs)
//        Seven-Eleven price is 142.732 (done in 3225 msecs)
//        Family price is 179.75 (done in 3529 msecs)
//        OK price is 134.4 (done in 4084 msecs)
//        BuyItAll price is 126.01 (done in 4238 msecs)
//        All shops have now responded in 4238 msecs

        CompletableFuture.allOf(futures).join(); //會等所有完成或TimeOut，如果使用anyOf則只會等一個

//        Seven-Eleven price is 173.53 (done in 1646 msecs)
//        All shops have now responded in 1646 msecs
//        CompletableFuture.anyOf(futures).join(); //有傳回就完成
        System.out.println("All shops have now responded in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
    }

}
