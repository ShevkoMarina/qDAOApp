package model;

public class RawTransaction {
    private long nonce;
    private long gas;
    private long gasLimit;
    private String addressTo;
    private long value;
    private String data;

    public RawTransaction(
            long nonce,
            long gas,
            long gasLimit,
            String addressTo,
            String data,
            long value) {
        this.nonce = nonce;
        this.gas = gas;
        this.gasLimit = gasLimit;
        this.addressTo = addressTo;
        this.value = value;
        this.data = data;
    }

    public long getNonce() {
        return nonce;
    }

    public long getGas() {
        return gas;
    }

    public long getGasLimit() {
        return gasLimit;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public long getValue() {
        return value;
    }

    public String getData() {
        return data;
    }
}