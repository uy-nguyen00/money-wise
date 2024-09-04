package com.uyng.moneywise.transaction;

import com.uyng.moneywise.category.Category;
import com.uyng.moneywise.common.entity.BaseEntityWithUser;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
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

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Transaction parentTransaction;

    @Transient
    @OneToMany(mappedBy = "parentTransaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Transaction> childrenTransactions = new HashSet<>();

    public Set<Transaction> addChildrenTransactions(Set<Transaction> childrenTransactions) {
        Set<Transaction> updatedChildren = new HashSet<>();
        for (Transaction child : childrenTransactions) {
            child.setParentTransaction(this);
            updatedChildren.add(child);
        }
        return updatedChildren;
    }
}
