package br.com.wallet.demo.DTO;

import br.com.wallet.demo.model.TransactionModel;
import br.com.wallet.demo.model.WalletModel;

import java.util.List;

public class TransactionMapper {

    public static TransactionResponseDTO toTransactionResponseDTO(TransactionModel transactionModel){
        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
        transactionResponseDTO.setId(transactionModel.getId());
        transactionResponseDTO.setAmount(transactionModel.getAmount());
        transactionResponseDTO.setDate(transactionModel.getDate());
        transactionResponseDTO.setBalanceAfter(transactionModel.getBalanceAfter());
        transactionResponseDTO.setBalanceBefore(transactionModel.getBalanceBefore());
        transactionResponseDTO.setType(transactionModel.getType());
        WalletModel wallet = transactionModel.getWallet();
        transactionResponseDTO.setWallet(wallet.getId());
        return transactionResponseDTO;
    }

}
