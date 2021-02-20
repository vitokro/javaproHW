package ua.kiev.prog.db.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "Transactions")
public class Transaction {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "account_from_id")
    private Account accountFrom;

    @ManyToOne
    @JoinColumn(name = "account_to_id")
    private Account accountTo;

    @Column(nullable = false)
    private BigDecimal sum;

    @Column(nullable = false)
    private Date date;

    public Transaction(Account accountFrom, Account accountTo, BigDecimal sum, Date date) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.sum = sum;
        this.date = date;
    }

    public Transaction() {
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

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "accountFrom=" + accountFrom +
                ", accountTo=" + accountTo +
                ", sum=" + sum +
                ", date=" + date +
                '}';
    }
}
