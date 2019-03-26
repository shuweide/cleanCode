package idv.code.jvm;

public class NaiveMerchant extends Merchant {

    @Override
    public Double actionPrice(double price) {
        return 0.9 * price;
    }

    public static void main(String[] args) {
        Merchant merchant = new NaiveMerchant();
        // price 必须定义成 Number 类型
        Number price = merchant.actionPrice(40);
        System.out.println(price);
    }
}