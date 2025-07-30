package br.com.wallet.demo.repository;

import br.com.wallet.demo.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository <TransactionModel, Integer> {
    String id (int id);

    public List<TransactionModel> findByWalletId(int wallet);
}
