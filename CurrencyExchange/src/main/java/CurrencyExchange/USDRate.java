package CurrencyExchange;

import CurrencyExchange.db.Id;

public class USDRate {
    @Id
    private int id;
    private String date;
    private float saleRate;
    private float purchaseRate;

    public USDRate() {
    }

    public USDRate(String date, float saleRate, float purchaseRate) {
        this.date = date;
        this.saleRate = saleRate;
        this.purchaseRate = purchaseRate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getSaleRate() {
        return saleRate;
    }

    public void setSaleRate(float saleRate) {
        this.saleRate = saleRate;
    }

    public float getPurchaseRate() {
        return purchaseRate;
    }

    public void setPurchaseRate(float purchaseRate) {
        this.purchaseRate = purchaseRate;
    }

    @Override
    public String toString() {
        return String.format("%s\t\t%s\t\t%s", date, saleRate, purchaseRate);
    }
}
