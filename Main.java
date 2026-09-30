package Ex03;

public class Main {
    public static void main(String[] args) {
        AccountRegistrationService service = new AccountRegistrationService();
        System.out.println("         HỆ THỐNG MỞ TÀI KHOẢN RIKKEI BANK (EX03)");
        service.registerAccount("10100019283", "NGUYEN VAN QUYET", 5000000.00, "SAVING");
        service.registerAccount("10100019283", "NGUYEN VAN QUYET", 1000000.00, "SAVING");
    }
}