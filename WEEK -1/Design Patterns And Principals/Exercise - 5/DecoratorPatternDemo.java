interface Notifier {
    void send(String message);
}

class EmailNotifier implements Notifier {

    @Override
    public void send(String message) {
        System.out.println("Sending Email: " + message);
    }
}

abstract class NotifierDecorator implements Notifier {

    protected Notifier notifier;

    public NotifierDecorator(Notifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void send(String message) {
        notifier.send(message);
    }
}

class SMSNotifierDecorator extends NotifierDecorator {

    public SMSNotifierDecorator(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("Sending SMS: " + message);
    }
}

class SlackNotifierDecorator extends NotifierDecorator {

    public SlackNotifierDecorator(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("Sending Slack Notification: " + message);
    }
}

public class DecoratorPatternDemo {

    public static void main(String[] args) {

        Notifier emailNotifier = new EmailNotifier();
        emailNotifier.send("Welcome to the Decorator Pattern!");

        System.out.println();

        Notifier smsNotifier = new SMSNotifierDecorator(new EmailNotifier());
        smsNotifier.send("Your OTP is 123456.");

        System.out.println();

        Notifier multiNotifier = new SlackNotifierDecorator(
                new SMSNotifierDecorator(
                        new EmailNotifier()));

        multiNotifier.send("Your order has been shipped!");
    }
}
