package br.com.wallet.demo.DTO;

import br.com.wallet.demo.model.TransactionModel;

import java.time.LocalDateTime;

public class TransactionResponseDTO {
    private int id;
    private int wallet;
    private LocalDateTime date;
    private int amount;
    private int balanceAfter;
    private int balanceBefore;
    TransactionModel.Type type;

    public TransactionModel.Type getType() {
        return type;
    }

    public void setType(TransactionModel.Type type) {
        this.type = type;
    }

    public int getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(int balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public int getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(int balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
