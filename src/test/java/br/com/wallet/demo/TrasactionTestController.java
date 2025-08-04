package br.com.wallet.demo;




import br.com.wallet.demo.DTO.*;
import br.com.wallet.demo.model.WalletModel;
import br.com.wallet.demo.repository.TransactionRepository;
import br.com.wallet.demo.repository.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TrasactionTestController {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository repository;

    @Test
    public void trasactionDepositTest() {
        WalletModel wallet = new WalletModel();
        wallet.setBalance(0);
        wallet = walletRepository.save(wallet);
        int amount = 500;

        DepositRequestDTO deposit = new DepositRequestDTO(wallet.getId(), amount);

        WalletResponseDTO response = restTemplate.patchForObject(
                "/transaction/"+wallet.getId()+"/deposit",
                deposit,
                WalletResponseDTO.class
        );

        assertNotNull(response);
        assertEquals(amount, response.getBalance());
    }

    @Test
    public void trasactionWithdrawTest() {
        WalletModel wallet = new WalletModel();
        wallet.setBalance(500);
        wallet = walletRepository.save(wallet);
        int amount = 500;

        WithdrawaRequestDTO withidraw = new WithdrawaRequestDTO(wallet.getId(), amount);

        WalletResponseDTO response = restTemplate.patchForObject(
                "/transaction/"+wallet.getId()+"/withdraw",
                withidraw,
                WalletResponseDTO.class
        );

        assertNotNull(response);
        assertEquals(0, response.getBalance());
    }

    @Test
    public void trasactionTransferTest() {
        WalletModel walletTo = new WalletModel();
        walletTo.setBalance(500);
        walletTo = walletRepository.save(walletTo);
        WalletModel walletFrom = new WalletModel();
        walletFrom.setBalance(500);
        walletFrom = walletRepository.save(walletFrom);


        TransferRequestDTO transfer = new TransferRequestDTO(walletFrom.getId(), walletTo.getId(), 500);

        WalletResponseDTO response = restTemplate.patchForObject(
                "/transaction/"+ walletTo.getId()+"/transfer",
                transfer,
                WalletResponseDTO.class
        );
        WalletModel wallet = walletRepository.findById(walletFrom.getId()).get();

        assertNotNull(response);
        assertEquals(1000, response.getBalance());
        assertEquals(0, wallet.getBalance());
    }

    @Test
    public void trasactionStatementTest() {
        WalletModel wallet = new WalletModel();
        wallet.setName("test");
        wallet.setBalance(500);
        wallet = walletRepository.save(wallet);
        int amount = 500;

        DepositRequestDTO deposit = new DepositRequestDTO(wallet.getId(), amount);



        ResponseEntity<List<TransactionResponseDTO>> response = restTemplate.exchange(
                "/transaction/" + wallet.getId() + "/statement",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TransactionResponseDTO>>() {}
        );

        List<TransactionResponseDTO> list = response.getBody();

        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());




    }
}
