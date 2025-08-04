package br.com.wallet.demo.DTO;

import br.com.wallet.demo.model.WalletModel;

public class WalletMapper {

    public static WalletModel toWalletModel(WalletRequestDTO requestDTO) {
        WalletModel wallet = new WalletModel();
        wallet.setId(requestDTO.getId());
        wallet.setName(requestDTO.getName());
        wallet.setBalance(requestDTO.getBalance());
        return wallet;
    }

    public static WalletResponseDTO toWalletResponse(WalletModel wallet) {
        WalletResponseDTO walletResponse = new WalletResponseDTO();
        walletResponse.setId(wallet.getId());
        walletResponse.setName(wallet.getName());
        walletResponse.setBalance(wallet.getBalance());
        return walletResponse;
    }

}
