package com.example.finalproject.Model;

public class WalletModel
{
    String walletName;
    String walletAmount;

    public WalletModel()
    {
    }

    public WalletModel(String walletName, String walletAmount)
    {
        this.walletName = walletName;
        this.walletAmount = walletAmount;
    }

    public String getWalletName()
    {
        return walletName;
    }

    public void setWalletName(String walletName)
    {
        this.walletName = walletName;
    }

    public String getWalletAmount()
    {
        return walletAmount;
    }

    public void setWalletAmount(String walletAmount)
    {
        this.walletAmount = walletAmount;
    }
}
