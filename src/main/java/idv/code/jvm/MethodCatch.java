package idv.code.jvm;

public class MethodCatch {
    public static void main(String[] args) {

    }

    class VIP implements Customer {
        @Override
        public boolean isVIP() {
            return true;
        }
    }

    class NOT_VIP implements Customer {
        @Override
        public boolean isVIP() {
            return false;
        }
    }

    abstract class MerchantOther<T extends Customer> {
        public double actionPrice(double price, T customer) {
            return price * 0.08;
        }
    }

    class VIPOnlyMerchant extends MerchantOther<VIP> {
        @Override
        public double actionPrice(double price, VIP customer) {
            return price * 0.07;
        }
    }
}


