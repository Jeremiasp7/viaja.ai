package br.com.viajaai.entities;

import java.util.List;

import br.com.planejaai.framework.entity.ResourcesEntityAbstract;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "budgets")
@Getter
@Setter
@NoArgsConstructor
public class BudgetEntity extends ResourcesEntityAbstract {
    @ElementCollection
    @Column(name = "category")
    private List<String> categories;

    @Transient
    public Double getTotalAmount() {
        return this.getMainResource();
    }

    public void setTotalAmount(Double amount) {
        this.setMainResource(amount);
    }

    @Transient
    public String getCurrency() {
        return this.getResourceType();
    }

    public void setCurrency(String currency) {
        this.setResourceType(currency);
    }
}