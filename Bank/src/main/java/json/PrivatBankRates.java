package json;

import java.math.BigDecimal;

public class PrivatBankRates {
    private String ccy;
    private String base_ccy;
    private BigDecimal buy;
    private BigDecimal sale;

    public String getCcy() {
        return ccy;
    }

    public String getBase_ccy() {
        return base_ccy;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    public BigDecimal getSale() {
        return sale;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public void setBase_ccy(String base_ccy) {
        this.base_ccy = base_ccy;
    }

    public void setBuy(BigDecimal buy) {
        this.buy = buy;
    }

    public void setSale(BigDecimal sale) {
        this.sale = sale;
    }
}
