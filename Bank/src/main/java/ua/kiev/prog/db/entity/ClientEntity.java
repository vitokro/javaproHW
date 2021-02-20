package ua.kiev.prog.db.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Clients")
public class ClientEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "client_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "clientEntity", cascade = CascadeType.ALL)
    private Set<Account> accounts = new HashSet<>();

    public ClientEntity(String name) {
        this.name = name;
    }

    public ClientEntity() {
    }

    public void addAccount(Account account){
        accounts.add(account);
    }

    public boolean delAccount(Account account){
        return accounts.remove(account);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name +
                '}';
    }
}
