package ua.kiev.prog.db;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class USDRate {
    @Id
    private String date;
    private Float saleRate;
    private Float purchaseRate;

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

    public Float getSaleRate() {
        return saleRate;
    }

    public void setSaleRate(Float saleRate) {
        this.saleRate = saleRate;
    }

    public Float getPurchaseRate() {
        return purchaseRate;
    }

    public void setPurchaseRate(Float purchaseRate) {
        this.purchaseRate = purchaseRate;
    }

    @Override
    public String toString() {
        return String.format("%s\t\t%s\t\t%s", date, saleRate, purchaseRate);
    }
}
