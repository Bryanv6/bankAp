package Bank.service;

import Bank.application.BankDao;
import Bank.application.BankDaoImp;
import Bank.model.Accounts;
import Bank.model.User;

import java.util.List;

/**
 * Created by bryanvillegas on 4/14/18.
 */
public class UserService {

    private static BankDao dao = new BankDaoImp();

    public static boolean insertUser(User user) {
        return dao.insertUser(user);
    }

    public static User getUser(String user, String password){
        return dao.getUser(user, password);
    }

    public static boolean insertAccount(int id) {
        return dao.insertAccount(id);
    }

    public static List<Accounts> getAllAccounts(int id){
        return dao.getAllAccounts(id);
    }
    public static boolean updateAccount(int id, double newAmount){
        return dao.updateAccount(id, newAmount);
    }
}
