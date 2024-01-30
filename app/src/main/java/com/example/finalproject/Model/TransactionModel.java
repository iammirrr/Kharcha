package com.example.finalproject.Model;

public class TransactionModel
{
    String transactionName;
    String transactionCategory;
    String transactionNote;
    String transactionAmount;
    String transactionType;
    String transactionBookmarked;

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public String getTransactionBookmarked()
    {
        return transactionBookmarked;
    }

    public void setTransactionBookmarked(String transactionBookmarked) {
        this.transactionBookmarked = transactionBookmarked;
    }

    public TransactionModel(String transactionName, String transactionCategory, String transactionNote, String transactionAmount, String transactionType, String transactionBookmarked) {
        this.transactionName = transactionName;
        this.transactionCategory = transactionCategory;
        this.transactionNote = transactionNote;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
        this.transactionBookmarked = transactionBookmarked;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionWallet(String transactionWallet) {
        this.transactionName = transactionWallet;
    }

    public String getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(String transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public String getTransactionNote() {
        return transactionNote;
    }

    public void setTransactionNote(String transactionNote) {
        this.transactionNote = transactionNote;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }



    public TransactionModel() {
    }
}
