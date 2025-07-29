package br.com.wallet.demo.controller;

import br.com.wallet.demo.DTO.DepositRequestDTO;
import br.com.wallet.demo.DTO.TransferRequestDTO;
import br.com.wallet.demo.DTO.WalletResponseDTO;
import br.com.wallet.demo.DTO.WithdrawaRequestDTO;
import br.com.wallet.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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


}
