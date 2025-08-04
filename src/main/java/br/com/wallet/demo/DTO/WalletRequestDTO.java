package br.com.wallet.demo.DTO;


public class WalletRequestDTO {

    int id;
    String name;
    int balance;

    public int getId() {
        return id;
    }

    public WalletRequestDTO(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }

    public WalletRequestDTO(int id, String name, int balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public WalletRequestDTO() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
