package br.com.wallet.demo.service;

import br.com.wallet.demo.DTO.WalletMappper;
import br.com.wallet.demo.DTO.WalletResponseDTO;
import br.com.wallet.demo.model.TransactionModel;
import br.com.wallet.demo.model.WalletModel;
import br.com.wallet.demo.repository.TransactionRepository;
import br.com.wallet.demo.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    public WalletResponseDTO transfer(int walletFromid, int walletToId, int amount) {

        if (amount <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid amount " + amount);
        }
        if (walletFromid == walletToId) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "it is not possible to transfer to the same wallet");
        }

        WalletModel fromWallet = walletRepository.findById(walletFromid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "wallet "+walletFromid+" not found"));
        WalletModel toWallet = walletRepository.findById(walletToId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "wallet "+walletToId+" not found"));

        if (fromWallet.getBalance() < amount) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance in source wallet");
        }

        fromWallet.setBalance(fromWallet.getBalance() - amount);
        toWallet.setBalance(toWallet.getBalance() + amount);



        TransactionModel transaction = new TransactionModel();
        transaction.setWalletId(walletToId);
        transaction.setType(String.valueOf(TransactionModel.Type.TRANSFER_SENDER));
        transaction.setAmount(amount);
        transaction.setDate(LocalDateTime.now());
        transaction.setBalanceBefore(fromWallet.getBalance());
        transaction.setBalanceAfter(fromWallet.getBalance() - amount);
        transactionRepository.save(transaction);

        TransactionModel transaction1 = new TransactionModel();
        transaction1.setWalletId(walletFromid);
        transaction1.setType(String.valueOf(TransactionModel.Type.TRANSFER_RECEIVER));
        transaction1.setAmount(amount);
        transaction1.setDate(LocalDateTime.now());
        transaction1.setBalanceBefore(toWallet.getBalance());
        transaction1.setBalanceAfter(toWallet.getBalance()+amount);
        transactionRepository.save(transaction1);

        walletRepository.saveAll(List.of(fromWallet, toWallet));

        return WalletMappper.toWalletResponse(toWallet);
    }

    public WalletResponseDTO deposit(int walletId, int amount) {

        WalletModel wallet = walletRepository.findById(walletId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "wallet "+walletId+" not found"));
        TransactionModel transaction = new TransactionModel();
        transaction.setWalletId(walletId);
        transaction.setType(String.valueOf(TransactionModel.Type.DEPOSIT));
        transaction.setAmount(amount);
        transaction.setDate(LocalDateTime.now());
        transaction.setBalanceBefore(wallet.getBalance());
        transaction.setBalanceAfter(wallet.getBalance()+amount);
        transactionRepository.save(transaction);

        wallet.setBalance(wallet.getBalance()+amount);

        walletRepository.save(wallet);

        return WalletMappper.toWalletResponse(wallet);

    }

    public WalletResponseDTO withdrawal(int walletId, int amount) {
        WalletModel wallet = walletRepository.findById(walletId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "wallet "+walletId+" not found"));

        if (wallet.getBalance() > amount){

            wallet.setBalance(wallet.getBalance() - amount);


        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance in source wallet");
        }

        TransactionModel transaction = new TransactionModel();
        transaction.setWalletId(walletId);
        transaction.setType(String.valueOf(TransactionModel.Type.WITHDRAWAL));
        transaction.setAmount(amount);
        transaction.setDate(LocalDateTime.now());
        transaction.setBalanceBefore(wallet.getBalance());
        transaction.setBalanceAfter(wallet.getBalance()+amount);
        transactionRepository.save(transaction);
        walletRepository.save(wallet);
        return WalletMappper.toWalletResponse(wallet);
    }
}
