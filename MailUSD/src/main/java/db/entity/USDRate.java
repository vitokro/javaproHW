package db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class USDRate implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    private String date;
    private Double saleRate;
    private Double purchaseRate;

    public USDRate() {
    }

    public USDRate(String date, Double saleRate, Double purchaseRate) {
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

    public Double getSaleRate() {
        return saleRate;
    }

    public void setSaleRate(Double saleRate) {
        this.saleRate = saleRate;
    }

    public Double getPurchaseRate() {
        return purchaseRate;
    }

    public void setPurchaseRate(Double purchaseRate) {
        this.purchaseRate = purchaseRate;
    }

    @Override
    public String toString() {
        return String.format("%s\t\t%s\t\t%s", date, saleRate, purchaseRate);
    }
}
