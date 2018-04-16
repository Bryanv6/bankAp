package Bank.application;

import Bank.model.Accounts;
import Bank.model.User;

import java.util.List;

/**
 * Created by bryanvillegas on 4/15/18.
 */
public interface BankDao {

    public boolean insertUser(User user);
    public User getUser(String user, String password);
    public List<User> getAllUsers();
    public boolean updateUser(User user);
    public boolean deleteUser(int id);

    public boolean insertAccount(int id);
    public List<Accounts> getAllAccounts(int id);
    public boolean updateAccount(int id, double amount);
    public boolean deleteAccount(int id);



}
