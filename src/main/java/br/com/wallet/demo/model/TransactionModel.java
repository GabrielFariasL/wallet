package br.com.wallet.demo.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;


import java.time.LocalDateTime;

@Entity (name = "transaction")
@Transactional
public class TransactionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private WalletModel wallet;

    private LocalDateTime date;
    private int amount;
    private int BalanceAfter;
    private int BalanceBefore;

    private Type type;
    public enum Type {
        DEPOSIT, WITHDRAWAL,TRANSFER_SENDER,TRANSFER_RECEIVER
    }



    public TransactionModel() {
    }

    public int getId() {
        return id;
    }


    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public WalletModel getWallet() {
        return wallet;
    }

    public void setWallet(WalletModel wallet) {
        this.wallet = wallet;
    }

    public Type getType() {
        return type;
    }

    public void setType(String type) {
        this.type = Type.valueOf(type);
    }

    public int getBalanceAfter() {
        return BalanceAfter;
    }

    public void setBalanceAfter(int balanceAfter) {
        BalanceAfter = balanceAfter;
    }

    public int getBalanceBefore() {
        return BalanceBefore;
    }

    public void setBalanceBefore(int balanceBefore) {
        BalanceBefore = balanceBefore;
    }


}
