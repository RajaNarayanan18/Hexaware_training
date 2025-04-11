package dao;

import entity.Account;
public interface AccountDAO {
    void createAccount(Account account) throws Exception;
    void deposit(String accNum, double amount) throws Exception;
    void withdraw(String accNum, double amount) throws Exception;
    void calculateInterest(String accNum) throws Exception;
}



