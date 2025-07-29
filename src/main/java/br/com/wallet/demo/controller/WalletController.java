package br.com.wallet.demo.controller;


import br.com.wallet.demo.DTO.WalletRequestDTO;
import br.com.wallet.demo.DTO.WalletResponseDTO;
import br.com.wallet.demo.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping
    public ResponseEntity<WalletResponseDTO> postWallet(@RequestBody WalletRequestDTO wallet) {
        return ResponseEntity.status(HttpStatus.CREATED).body(walletService.postWallet(wallet));
    }

    @GetMapping
    public ResponseEntity<ArrayList<WalletResponseDTO>> getWallet() {
        return ResponseEntity.ok().body(walletService.getAllWallets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WalletResponseDTO> getWallet(@PathVariable int id) {
        return ResponseEntity.ok().body(walletService.getWallet(id));
    }

    @PutMapping("/{id}")
    public WalletResponseDTO updateWallet(@PathVariable int id, @RequestBody WalletRequestDTO wallet) {
        return walletService.updateWallet(id, wallet);
    }


        @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWallet(@PathVariable int id) {
       return walletService.deleteWallet(id);
    }


}

