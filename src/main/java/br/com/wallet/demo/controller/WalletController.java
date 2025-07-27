package br.com.wallet.demo.controller;


import br.com.wallet.demo.model.WalletModel;
import br.com.wallet.demo.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wallet}")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping
    public ResponseEntity<WalletModel> postWallet(@RequestBody WalletModel wallet) {
        walletService.postWallet(wallet);
        return ResponseEntity.status(HttpStatus.CREATED).body(wallet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WalletModel> getWallet(@PathVariable int id) {
        return ResponseEntity.ok().body(walletService.getWallet(id));
    }

    @PutMapping("{/id}")
    public WalletModel updateWallet(@PathVariable int id, @RequestBody WalletModel wallet) {
       return walletService.updateWallet(id,wallet);
    }

    @PutMapping("{/tranfer}")
    public WalletModel transfer(@RequestBody int from, int to, int amount) {
        return walletService.transfer(from,to,amount);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteWallet(int id) {
       return walletService.deleteWallet(id);
    }


}

