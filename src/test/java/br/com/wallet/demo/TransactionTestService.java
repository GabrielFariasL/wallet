package br.com.wallet.demo;

import br.com.wallet.demo.DTO.TransactionResponseDTO;
import br.com.wallet.demo.DTO.WalletRequestDTO;
import br.com.wallet.demo.DTO.WalletResponseDTO;
import br.com.wallet.demo.model.TransactionModel;
import br.com.wallet.demo.service.TransactionService;
import br.com.wallet.demo.service.WalletService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;


@SpringBootTest
@ActiveProfiles("test")
public class TransactionTestService {


    @Autowired
    private TransactionService transactionService;

    @Autowired
    private WalletService walletService;

    @Test
    void testTransactionDeposit() {
        WalletRequestDTO request = new WalletRequestDTO("claudio", 0);
        WalletResponseDTO response = walletService.postWallet(request);
        int balance =500;

        WalletResponseDTO deposit = transactionService.deposit(response.getId(), balance);


        Assertions.assertEquals(balance, deposit.getBalance() );
    }


    @Test
    void testTransactionWithdraw() {
        WalletRequestDTO request = new WalletRequestDTO("claudio", 100);
        WalletResponseDTO response = walletService.postWallet(request);
        int balance =100;

        WalletResponseDTO deposit = transactionService.withdrawal(response.getId(), balance);

        Assertions.assertEquals(0, deposit.getBalance());
    }

    @Test
    void testTransactionTransfer() {
        WalletRequestDTO requestTo = new WalletRequestDTO("claudio", 0);
        WalletRequestDTO requestFrom = new WalletRequestDTO("vinicius", 200);
        WalletResponseDTO walletTo = walletService.postWallet(requestTo);
        WalletResponseDTO walletFrom = walletService.postWallet(requestFrom);
        int balance =200;

        WalletResponseDTO transfer = transactionService.transfer(walletFrom.getId(),walletTo.getId(),balance);

        Assertions.assertEquals(balance, transfer.getBalance());
    }

    @Test
    void testTransactionStatamet() {
        WalletRequestDTO request = new WalletRequestDTO("claudio", 0);
        WalletResponseDTO response = walletService.postWallet(request);
        int amount =500;
        WalletResponseDTO deposit = transactionService.deposit(response.getId(), amount);




        List<TransactionResponseDTO> transactionList = transactionService.transactionPerWallet(deposit.getId());
        TransactionResponseDTO statement = transactionList.get(0);

        Assertions.assertEquals(500, statement.getAmount());
        Assertions.assertEquals(500, statement.getBalanceAfter());
        Assertions.assertEquals(0, statement.getBalanceBefore());
        Assertions.assertEquals(TransactionModel.Type.DEPOSIT, statement.getType());
        Assertions.assertNotNull(statement.getDate() );

    }

}
