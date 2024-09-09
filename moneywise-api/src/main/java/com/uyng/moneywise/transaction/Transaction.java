package com.uyng.moneywise.transaction;

import com.uyng.moneywise.category.Category;
import com.uyng.moneywise.common.entity.BaseEntityWithUser;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction extends BaseEntityWithUser {

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDate date;

    private String description;

    @ManyToMany
    @JoinTable(
            name = "transaction_links",
            joinColumns = @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "child_id")
    )
    @Builder.Default
    private Set<Transaction> childrenTransactions = new HashSet<>();

    @ManyToMany(mappedBy = "childrenTransactions")
    @Builder.Default
    private Set<Transaction> parentTransactions = new HashSet<>();

    public void addChildTransaction(Transaction transaction) {
        this.childrenTransactions.add(transaction);
        transaction.parentTransactions.add(this);
    }

    public void removeChildTransaction(Transaction transaction) {
        this.childrenTransactions.remove(transaction);
        transaction.parentTransactions.remove(this);
    }

    public void setChildrenTransactions(Set<Transaction> childrenTransactions) {
        this.childrenTransactions = childrenTransactions;
        for (Transaction transaction : childrenTransactions) {
            transaction.parentTransactions.add(this);
        }
    }
}
