package br.com.wallet.demo;

import br.com.wallet.demo.model.WalletModel;
import br.com.wallet.demo.repository.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class WalletTestRepository {

    @Autowired
    private WalletRepository walletRepository;

    @Test
    public void createWalletTest () {
        WalletModel wallet = new WalletModel();
        wallet.setName("Test");
        wallet.setBalance(1000);

        WalletModel walletResponse = walletRepository.save(wallet);

        Assertions.assertEquals("Test", walletResponse.getName());
        Assertions.assertEquals(1000, walletResponse.getBalance());
    }

    @Test
    public void findWalletIdTest () {
        WalletModel wallet = new WalletModel();
        wallet.setName("Test");
        wallet.setBalance(1000);
        WalletModel walletCreat = walletRepository.save(wallet);

        Optional<WalletModel> walletResponse = walletRepository.findById(walletCreat.getId());

        Assertions.assertEquals(walletCreat.getId(), walletResponse.get().getId());
        Assertions.assertEquals(walletCreat.getName(), walletResponse.get().getName());
        Assertions.assertEquals(walletCreat.getBalance(), walletResponse.get().getBalance());
    }

    @Test
    public void findAllWalletsTest () {
        WalletModel wallet = new WalletModel();
        wallet.setName("Test");
        wallet.setBalance(1000);
        walletRepository.save(wallet);
        WalletModel wallet2 = new WalletModel();
        wallet2.setName("Test2");
        wallet2.setBalance(2000);
        walletRepository.save(wallet2);

        List<WalletModel> wallets = walletRepository.findAll();

        Assertions.assertEquals(2, wallets.size());
        Assertions.assertEquals(wallet.getId(), wallets.get(0).getId());
        Assertions.assertEquals(wallet.getName(), wallets.get(0).getName());
        Assertions.assertEquals(wallet.getBalance(), wallets.get(0).getBalance());
        Assertions.assertEquals(wallet2.getId(), wallets.get(1).getId());
        Assertions.assertEquals(wallet2.getName(), wallets.get(1).getName());
        Assertions.assertEquals(wallet2.getBalance(), wallets.get(1).getBalance());
    }

    @Test
    public void deleteWalletTest () {
        WalletModel wallet = new WalletModel();
        wallet.setName("Test");
        wallet.setBalance(1000);
        walletRepository.save(wallet);

        walletRepository.deleteById(wallet.getId());

        Optional<WalletModel> walletDeleted = walletRepository.findById(wallet.getId());

        Assertions.assertEquals(Optional.empty(), walletDeleted);
    }

}
