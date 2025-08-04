package br.com.wallet.demo.service;

import br.com.wallet.demo.DTO.TransactionMapper;
import br.com.wallet.demo.DTO.TransactionResponseDTO;
import br.com.wallet.demo.DTO.WalletMapper;
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
import java.util.stream.Collectors;

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
        transaction.setWallet(toWallet);
        transaction.setType(String.valueOf(TransactionModel.Type.TRANSFER_SENDER));
        transaction.setAmount(amount);
        transaction.setDate(LocalDateTime.now());
        transaction.setBalanceBefore(fromWallet.getBalance());
        transaction.setBalanceAfter(fromWallet.getBalance() - amount);
        transactionRepository.save(transaction);

        TransactionModel transaction1 = new TransactionModel();
        transaction1.setWallet(fromWallet);
        transaction1.setType(String.valueOf(TransactionModel.Type.TRANSFER_RECEIVER));
        transaction1.setAmount(amount);
        transaction1.setDate(LocalDateTime.now());
        transaction1.setBalanceBefore(toWallet.getBalance());
        transaction1.setBalanceAfter(toWallet.getBalance()+amount);
        transactionRepository.save(transaction1);

        walletRepository.saveAll(List.of(fromWallet, toWallet));

        return WalletMapper.toWalletResponse(toWallet);
    }

    public WalletResponseDTO deposit(int walletId, int amount) {

        WalletModel wallet = walletRepository.findById(walletId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "wallet "+walletId+" not found"));

        int balanceBefore = wallet.getBalance();
        int balanceAfter = balanceBefore + amount;

        wallet.setBalance(balanceAfter);
        walletRepository.save(wallet);

        walletRepository.save(wallet);

        TransactionModel transaction = new TransactionModel();
        transaction.setWallet(wallet);
        transaction.setType(String.valueOf(TransactionModel.Type.DEPOSIT));
        transaction.setAmount(amount);
        transaction.setDate(LocalDateTime.now());
        transaction.setBalanceBefore(balanceBefore);
        transaction.setBalanceAfter(balanceAfter);
        transactionRepository.save(transaction);

        return WalletMapper.toWalletResponse(wallet);

    }

    public WalletResponseDTO withdrawal(int walletId, int amount) {
        WalletModel wallet = walletRepository.findById(walletId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "wallet "+walletId+" not found"));

        if (wallet.getBalance() >= amount){

            wallet.setBalance(wallet.getBalance() - amount);


        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance in source wallet");
        }

        TransactionModel transaction = new TransactionModel();
        transaction.setWallet(wallet);
        transaction.setType(String.valueOf(TransactionModel.Type.WITHDRAWAL));
        transaction.setAmount(amount);
        transaction.setDate(LocalDateTime.now());
        transaction.setBalanceBefore(wallet.getBalance());
        transaction.setBalanceAfter(wallet.getBalance()+amount);
        transactionRepository.save(transaction);
        walletRepository.save(wallet);
        return WalletMapper.toWalletResponse(wallet);
    }

    public List<TransactionResponseDTO> transactionPerWallet(int walletId) {
        return transactionRepository.findByWalletId(walletId).stream().map(transaction -> TransactionMapper.toTransactionResponseDTO(transaction)).collect(Collectors.toList());
    }


}
