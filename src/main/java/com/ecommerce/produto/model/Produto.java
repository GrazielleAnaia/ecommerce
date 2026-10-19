package com.ecommerce.produto.model;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private BigDecimal valor;

    private UUID businessId = UUID.randomUUID();

    public Produto() {
    }

    public Produto(String nome, BigDecimal valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public UUID getBusinessId() {
        return businessId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;

        Produto produto = (Produto) o;

        return Objects.equals(businessId, produto.getBusinessId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(businessId);
    }
}
