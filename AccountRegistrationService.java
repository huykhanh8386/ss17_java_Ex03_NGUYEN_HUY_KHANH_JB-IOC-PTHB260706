package Ex03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class AccountRegistrationService {
    public boolean registerAccount(
            String accountNumber,
            String holderName,
            double initialBalance,
            String accountType) {
        String checkSql = """
                SELECT 1
                FROM bank_accounts
                WHERE account_number = ?
                """;
        String insertSql = """
                INSERT INTO bank_accounts
                (account_number, account_holder, balance, account_type)
                VALUES (?, ?, ?, ?)
                """;
        try (
                Connection con = ConnectDB.openConnection()
        ) {
            try (
                    PreparedStatement checkStmt =
                            con.prepareStatement(checkSql)
            ) {
                checkStmt.setString(1, accountNumber);
                try (
                        ResultSet rs = checkStmt.executeQuery()
                ) {
                    if (rs.next()) {
                        System.out.println("[TỪ CHỐI] Số tài khoản " + accountNumber + " đã tồn tại trong hệ thống!");
                        return false;
                    }
                }
            }
            try (
                    PreparedStatement insertStmt = con.prepareStatement(insertSql)
            ) {
                insertStmt.setString(1, accountNumber);
                insertStmt.setString(2, holderName);
                insertStmt.setDouble(3, initialBalance);
                insertStmt.setString(4, accountType);
                int rowsAffected = insertStmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("[THÀNH CÔNG] Đăng ký mở tài khoản ngân hàng thành công!");
                    System.out.println(" - Số tài khoản : " + accountNumber);
                    System.out.println(" - Chủ tài khoản: " + holderName);
                    System.out.printf(" - Số dư đầu kỳ : %,.2f VND%n", initialBalance);
                    System.out.println(" - Loại hình    : " + accountType);
                    return true;
                } else {
                    System.out.println("[THẤT BẠI] Không thể tạo tài khoản!");
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
            return false;
        }
    }
}