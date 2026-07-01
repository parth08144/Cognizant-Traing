// Target Interface
interface PaymentProcessor {
    void processPayment(double amount);
}


class PayPalGateway {

    public void sendPayment(double amount) {
        System.out.println("PayPal Payment Successful");
        System.out.println("Amount Paid: ₹" + amount);
    }
}


class StripeGateway {

    public void makeCharge(double amount) {
        System.out.println("Stripe Payment Successful");
        System.out.println("Amount Paid: ₹" + amount);
    }
}


class PayPalAdapter implements PaymentProcessor {

    private PayPalGateway paypalGateway;

    public PayPalAdapter(PayPalGateway paypalGateway) {
        this.paypalGateway = paypalGateway;
    }

    @Override
    public void processPayment(double amount) {
        paypalGateway.sendPayment(amount);
    }
}


class StripeAdapter implements PaymentProcessor {

    private StripeGateway stripeGateway;

    public StripeAdapter(StripeGateway stripeGateway) {
        this.stripeGateway = stripeGateway;
    }

    @Override
    public void processPayment(double amount) {
        stripeGateway.makeCharge(amount);
    }
}


public class AdapterPatternDemo {

    public static void main(String[] args) {

        
        PaymentProcessor paypal = new PayPalAdapter(new PayPalGateway());
        paypal.processPayment(1500);

        System.out.println();

        
        PaymentProcessor stripe = new StripeAdapter(new StripeGateway());
        stripe.processPayment(2500);
    }
}
