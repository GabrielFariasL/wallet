package br.com.wallet.demo.DTO;

public record TransferRequestDTO(Integer fromWalletId, Integer toWalletId, Integer amount) {
}
