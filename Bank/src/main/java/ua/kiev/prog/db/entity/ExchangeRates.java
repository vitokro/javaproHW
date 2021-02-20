package ua.kiev.prog.db.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Exchange_rates")
public class ExchangeRates {
    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private Currency base_currency;

    @Column(nullable = false)
    private BigDecimal buy;

    @Column(nullable = false)
    private BigDecimal sale;

    public ExchangeRates(Currency currency, Currency base_currency, BigDecimal buy, BigDecimal sale) {
        this.currency = currency;
        this.base_currency = base_currency;
        this.buy = buy;
        this.sale = sale;
    }

    public ExchangeRates() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Currency getBase_currency() {
        return base_currency;
    }

    public void setBase_currency(Currency base_currency) {
        this.base_currency = base_currency;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    public void setBuy(BigDecimal buy) {
        this.buy = buy;
    }

    public BigDecimal getSale() {
        return sale;
    }

    public void setSale(BigDecimal sale) {
        this.sale = sale;
    }

    @Override
    public String toString() {
        return "ExchangeRates{" +
                "currency=" + currency +
                ", base_currency=" + base_currency +
                ", buy=" + buy +
                ", sale=" + sale +
                '}';
    }
}
