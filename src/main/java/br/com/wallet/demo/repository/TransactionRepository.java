package br.com.wallet.demo.repository;

import br.com.wallet.demo.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository <TransactionModel, Integer> {
    String id (int id);
}
