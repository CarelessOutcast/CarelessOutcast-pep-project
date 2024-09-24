package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    /**
     * 
     * @param account
     * @return
     */
    public Account addAccount(Account account) {
        if (valid(account)) {
            return accountDAO.insertAccount(account);
        } else {
            return null;
        }
    }

    /**
     * 
     * @param account
     * @return
     */
    public Account getAccount(Account account) {
        // Check if the account is valid
        if (exists(account)) {
            return accountDAO.getAccount(account);
        } else {
            return null;
        }
    }

    /**
     * 
     * @param id
     * @return
     */
    public Boolean isAccount(int id) {
        return accountDAO.isAccount(id);
    }

    /**
     * 
     * @param account
     * @return
     */
    private Boolean exists(Account account){
        // It exists if it's not a valid username
        return !validateUsername(account.getUsername()) 
            && (account.getUsername().length() >= 0);
    }

    /**
     * 
     * @param account
     * @return 
     */
    private Boolean valid(Account account) {
        return validateUsername(account.getUsername())
            && validatePassword(account.getPassword());
    }

    /**
     * 
     * @param username
     * @return
     */
    private Boolean validateUsername(String username){
        // - required not_blank username 
        // - required unique username 
        return !accountDAO.isUsername(username)
            && (username.length() >= 0);
    }

    /**
     * 
     * @param password
     * @return
     */
    private Boolean validatePassword(String password){
        // - required password (>= 4 chars)
        return (password.length() >= 4);
    }
    
}
