package br.com.wallet.demo.service;


import br.com.wallet.demo.model.WalletModel;
import br.com.wallet.demo.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public WalletModel postWallet(WalletModel wallet) {
        return walletRepository.save(wallet);
    }

    public WalletModel getWallet(int id) {
        Optional<WalletModel> wallet = walletRepository.findById(id);
        return wallet.orElse(null);
    }

    public ResponseEntity<String> deleteWallet(int id) {
        Optional<WalletModel> wallet = walletRepository.findById(id);
        if (wallet.isPresent()) {
            walletRepository.delete(wallet.get());
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wallet "+ id+ " not found");
        }
        return null;
    }

    public WalletModel updateWallet(int id, WalletModel wallet) {
        var oldWallet = walletRepository.findById(id).orElse(null);
        if (oldWallet != null) {
            oldWallet.setName(wallet.getName());
            oldWallet.setBalance(wallet.getBalance());
            walletRepository.save(oldWallet);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wallet " + id + " not found");
        }
        return walletRepository.save(wallet);
    }




    public WalletModel transfer(int from, int  to, int amount) {
            WalletModel fromWallet = getWallet(from);
            WalletModel toWallet = getWallet(to);

            if (fromWallet == null || toWallet == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One or both wallets not found");
            }

            if (fromWallet.getBalance() < amount) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance in source wallet");
            }

            fromWallet.setBalance(fromWallet.getBalance() - amount);
            toWallet.setBalance(toWallet.getBalance() + amount);

            walletRepository.saveAll(List.of(fromWallet, toWallet));

            return toWallet;
        }
}
