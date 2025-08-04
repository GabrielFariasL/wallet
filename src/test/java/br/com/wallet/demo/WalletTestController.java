package br.com.wallet.demo;

import br.com.wallet.demo.DTO.TransactionResponseDTO;
import br.com.wallet.demo.DTO.WalletRequestDTO;
import br.com.wallet.demo.DTO.WalletResponseDTO;
import br.com.wallet.demo.service.WalletService;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class WalletTestController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void postTest() {
        WalletRequestDTO requestDTO = new WalletRequestDTO();
        requestDTO.setBalance(100);
        requestDTO.setName("test");

        WalletResponseDTO response = restTemplate.postForObject(
                "/wallet",
                requestDTO,
                WalletResponseDTO.class
        );

        Assertions.assertNotNull(response);
        Assertions.assertEquals(100, response.getBalance());
        Assertions.assertEquals("test", response.getName());
    }

    @Test
    public void getByIdTest() {
        WalletRequestDTO requestDTO = new WalletRequestDTO();
        requestDTO.setBalance(100);
        requestDTO.setName("test");
        WalletResponseDTO walletCreate =  walletService.postWallet(requestDTO);

        WalletResponseDTO response = restTemplate.getForObject(
                "/wallet/" + walletCreate.getId(),
                WalletResponseDTO.class
        );

        Assertions.assertNotNull(response);
        Assertions.assertEquals(100, response.getBalance());
        Assertions.assertEquals("test", response.getName());
    }

    @Test
    public void getAllTest() {
        WalletRequestDTO requestDTO = new WalletRequestDTO();
        requestDTO.setBalance(100);
        requestDTO.setName("test");
        WalletRequestDTO requestDTO2 = new WalletRequestDTO();
        requestDTO2.setBalance(200);
        requestDTO2.setName("test2");
        WalletRequestDTO requestDTO3 = new WalletRequestDTO();
        requestDTO3.setBalance(300);
        requestDTO3.setName("test3");
        walletService.postWallet(requestDTO);
        walletService.postWallet(requestDTO2);
        walletService.postWallet(requestDTO3);


        ResponseEntity<List<WalletResponseDTO>> response = restTemplate.exchange(
                "/wallet",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<WalletResponseDTO>>() {}
        );

        Assertions.assertNotEquals(0, response.getBody().size());
        Assertions.assertNotNull(response);
    }


}
