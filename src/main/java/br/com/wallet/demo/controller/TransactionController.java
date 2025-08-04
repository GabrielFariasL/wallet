package br.com.wallet.demo.controller;

import br.com.wallet.demo.DTO.*;
import br.com.wallet.demo.model.TransactionModel;
import br.com.wallet.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @PatchMapping("/{id}/transfer")
    public ResponseEntity<WalletResponseDTO> transfer(@RequestBody TransferRequestDTO transferRequestDTO) {
        return ResponseEntity.ok().body(transactionService.transfer(transferRequestDTO.fromWalletId(), transferRequestDTO.toWalletId(), transferRequestDTO.amount()));
    }

    @PatchMapping("/{id}/deposit")
    public ResponseEntity<WalletResponseDTO> deposit(@RequestBody DepositRequestDTO depositRequestDTO) {
        return ResponseEntity.ok().body(transactionService.deposit(depositRequestDTO.walletId(),depositRequestDTO.amount()));
    }

    @PatchMapping("/{id}/withdraw")
    public ResponseEntity<WalletResponseDTO> withdraw(@RequestBody WithdrawaRequestDTO withdrawaRequestDTO) {
        return ResponseEntity.ok().body(transactionService.withdrawal(withdrawaRequestDTO.walletId(), withdrawaRequestDTO.amount()));
    }

    @GetMapping("/{id}/statement")
    public List<TransactionResponseDTO> statement(@PathVariable Integer id) {
        return transactionService.transactionPerWallet(id);
    }


}
