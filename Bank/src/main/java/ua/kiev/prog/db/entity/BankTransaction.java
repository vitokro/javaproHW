package ua.kiev.prog.db.entity;

import javax.persistence.*;
import java.math.BigDecimal;
//import java.sql.Date;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Transactions")
public class BankTransaction {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "account_from_id")
    private Account accountFrom;

    @Column(nullable = false, name = "sum_from")
    private BigDecimal sumFrom;

    @ManyToOne
    @JoinColumn(name = "account_to_id")
    private Account accountTo;

    @Column(nullable = false, name = "sum_to")
    private BigDecimal sumTo;

    @Column(nullable = false, name = "exchange_rate")
    private BigDecimal rate;

//    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date date = new Date(System.currentTimeMillis());

    public BankTransaction(Account accountFrom, Account accountTo, BigDecimal sumFrom, BigDecimal sumTo, BigDecimal rate) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.sumFrom = sumFrom;
        this.sumTo = sumTo;
        this.rate = rate;
    }

    public BankTransaction() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Account accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getSumFrom() {
        return sumFrom;
    }

    public void setSumFrom(BigDecimal sum) {
        this.sumFrom = sum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getSumTo() {
        return sumTo;
    }

    public void setSumTo(BigDecimal sumTo) {
        this.sumTo = sumTo;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankTransaction that = (BankTransaction) o;
        return accountFrom.equals(that.accountFrom) &&
                accountTo.equals(that.accountTo) &&
                sumFrom.equals(that.sumFrom) &&
                date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountFrom, accountTo, sumFrom, date);
    }

    @Override
    public String toString() {
        return String.format(
                "\nTRANSACTION:\n Date: %s\n From client: %s account: %s  sum: %s %s\n" +
                        " To client: %s  account: %s  sum: %s %s\n" +
                        " Exchange rate: %s\n",
                getDate(),
                getAccountFrom().getClientEntity().getName(),
                getAccountFrom().getName(),
                getAccountFrom().getCurrency(),
                getSumFrom(),
                getAccountTo().getClientEntity().getName(),
                getAccountTo().getName(),
                getAccountTo().getCurrency(),
                getSumTo(),
                getRate());

    }
}
