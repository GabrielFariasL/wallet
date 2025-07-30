package br.com.wallet.demo.controller;

import br.com.wallet.demo.DTO.*;
import br.com.wallet.demo.model.TransactionModel;
import br.com.wallet.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @PutMapping("/transfer")
    public WalletResponseDTO transfer(@RequestBody TransferRequestDTO transferRequestDTO) {
        return transactionService.transfer(transferRequestDTO.fromWalletId(), transferRequestDTO.toWalletId(), transferRequestDTO.amount());
    }

    @PutMapping("/deposit")
    public WalletResponseDTO deposit(@RequestBody DepositRequestDTO depositRequestDTO) {
        return transactionService.deposit(depositRequestDTO.walletId(),depositRequestDTO.amount());
    }

    @PutMapping("/withdraw")
    public WalletResponseDTO withdraw(@RequestBody WithdrawaRequestDTO withdrawaRequestDTO) {
        return transactionService.withdrawal(withdrawaRequestDTO.walletId(), withdrawaRequestDTO.amount());
    }

    @GetMapping("/statement")
    public List<TransactionModel> statement(@RequestBody StatamentDTO statamentDTO) {
        return transactionService.transactionPerWallet(statamentDTO.walletId());
    }


}
