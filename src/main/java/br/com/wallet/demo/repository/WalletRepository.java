package br.com.wallet.demo.repository;

import br.com.wallet.demo.model.WalletModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<WalletModel, Integer> {
    String id(int id);
}
