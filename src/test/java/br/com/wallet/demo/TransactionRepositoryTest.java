package br.com.wallet.demo;

import br.com.wallet.demo.model.TransactionModel;
import br.com.wallet.demo.model.WalletModel;
import br.com.wallet.demo.repository.TransactionRepository;
import br.com.wallet.demo.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
@DataJpaTest
public class TransactionRepositoryTest {


        @Autowired
        private TransactionRepository transactionRepository;

        @Autowired
        private WalletRepository walletRepository;

       @Test
        public void testFindByWalletId() {
            WalletModel wallet = new WalletModel();
            wallet.setBalance(1000);
            WalletModel savedWallet = walletRepository.save(wallet);

            TransactionModel transaction = new TransactionModel();
            transaction.setWallet(savedWallet);
            transaction.setAmount(500);
            transaction.setDate(LocalDateTime.now());
            transaction.setType("DEPOSIT");
            transaction.setBalanceBefore(1000);
            transaction.setBalanceAfter(1500);
            transactionRepository.save(transaction);

            List<TransactionModel> transactions = transactionRepository.findByWalletId(savedWallet.getId());

            assertFalse(transactions.isEmpty());
            assertEquals(1, transactions.size());
            assertEquals(500, transactions.get(0).getAmount());
        }
    @Test
    public void testSaveAndFindById() {
        WalletModel wallet = new WalletModel();
        wallet.setBalance(1000);
        WalletModel saved = walletRepository.save(wallet);

        Optional<WalletModel> found = walletRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals(1000, found.get().getBalance());
    }

    @Test
    public void testUpdate() {
        WalletModel wallet = new WalletModel();
        wallet.setBalance(1000);
        WalletModel saved = walletRepository.save(wallet);

        saved.setBalance(2000);
        WalletModel updated = walletRepository.save(saved);

        assertEquals(2000, updated.getBalance());
    }

    @Test
    public void testDelete() {
        WalletModel wallet = new WalletModel();
        wallet.setBalance(1000);
        WalletModel saved = walletRepository.save(wallet);

        walletRepository.delete(saved);

        Optional<WalletModel> found = walletRepository.findById(saved.getId());
        assertFalse(found.isPresent());
    }



}
