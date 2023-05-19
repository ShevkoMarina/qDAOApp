package model;

public class TokenInfo {

    private long balance;
    private long weight;


    public TokenInfo(long balance, long weight) {
        this.balance = balance;
        this.weight = weight;
    }

    public long getBalance() {
        return balance;
    }

    public long getWeight() {
        return weight;
    }
}
