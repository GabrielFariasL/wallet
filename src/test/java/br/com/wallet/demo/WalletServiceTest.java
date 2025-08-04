package br.com.wallet.demo;

import br.com.wallet.demo.DTO.WalletRequestDTO;
import br.com.wallet.demo.DTO.WalletResponseDTO;
import br.com.wallet.demo.model.WalletModel;
import br.com.wallet.demo.service.WalletService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class WalletServiceTest {

    @Autowired
    private WalletService walletService;

    @Test
    public void postTest(){
        WalletRequestDTO wallet = new WalletRequestDTO();
        wallet.setBalance(500);
        wallet.setName("test");

        WalletResponseDTO walletResponseDTO = walletService.postWallet(wallet);

        Assertions.assertNotNull(walletResponseDTO);
        Assertions.assertEquals(walletResponseDTO.getBalance(), 500);
        Assertions.assertEquals(walletResponseDTO.getName(), "test");

    }

    @Test
    public void getTest(){
        WalletRequestDTO wallet = new WalletRequestDTO();
        wallet.setBalance(500);
        wallet.setName("test");
        WalletResponseDTO walletResponseDTO = walletService.postWallet(wallet);

        walletResponseDTO = walletService.getWallet(walletResponseDTO.getId());

        Assertions.assertNotNull(walletResponseDTO);
        Assertions.assertEquals(walletResponseDTO.getBalance(), 500);
        Assertions.assertEquals(walletResponseDTO.getName(), "test");
    }

    @Test
    public void updateTest(){
        WalletRequestDTO wallet = new WalletRequestDTO();
        wallet.setBalance(500);
        wallet.setName("test");
        WalletResponseDTO walletCreate = walletService.postWallet(wallet);
        WalletRequestDTO newWallet = new WalletRequestDTO();
        newWallet.setName("test2");
        newWallet.setBalance(200);

        WalletResponseDTO walletResponseDTO = walletService.updateWallet(walletCreate.getId(),newWallet);

        Assertions.assertNotNull(walletResponseDTO);
        Assertions.assertEquals(walletResponseDTO.getBalance(), 200);
        Assertions.assertEquals(walletResponseDTO.getName(), "test2");
    }

    @Test
    public void deleteTest(){
        WalletRequestDTO wallet = new WalletRequestDTO();
        wallet.setBalance(500);
        wallet.setName("test");
        WalletResponseDTO walletCreate = walletService.postWallet(wallet);
        walletService.deleteWallet(walletCreate.getId());

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            walletService.getWallet(walletCreate.getId());
        });
    }
}
