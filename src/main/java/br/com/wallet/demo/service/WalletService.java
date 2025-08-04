package br.com.wallet.demo.service;


import br.com.wallet.demo.DTO.WalletMapper;
import br.com.wallet.demo.DTO.WalletRequestDTO;
import br.com.wallet.demo.DTO.WalletResponseDTO;
import br.com.wallet.demo.model.WalletModel;
import br.com.wallet.demo.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.Optional;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;



    public WalletResponseDTO postWallet(WalletRequestDTO wallet) {
        WalletModel walletModel = WalletMapper.toWalletModel(wallet);
        walletRepository.save(walletModel);
        return WalletMapper.toWalletResponse(walletModel);
    }

    public ArrayList<WalletResponseDTO> getAllWallets() {
        ArrayList<WalletResponseDTO> wallets = new ArrayList<>();
        walletRepository.findAll().forEach(wallet -> wallets.add(WalletMapper.toWalletResponse(wallet)));
        return wallets;
    }

    public WalletResponseDTO getWallet(int id) {
        Optional<WalletModel> wallet = walletRepository.findById(id);
        return walletRepository.findById(id)
                .map(WalletMapper::toWalletResponse)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Wallet with id " + id + " not found"
                ));
    }

    public ResponseEntity<String> deleteWallet(int id) {
        WalletModel wallet = walletRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet "+ id + " not found"));
        walletRepository.delete(wallet);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public WalletResponseDTO updateWallet(int id, WalletRequestDTO walletRequestDTO) {
        WalletModel wallet = WalletMapper.toWalletModel(walletRequestDTO);
        WalletModel oldWallet = walletRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet "+ id + " not found"));
        oldWallet.setName(wallet.getName());
        oldWallet.setBalance(wallet.getBalance());
        walletRepository.save(oldWallet);
        return WalletMapper.toWalletResponse(wallet);
    }






}
