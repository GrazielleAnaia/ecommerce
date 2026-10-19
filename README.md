# 💣 Bomba-relógio na Produção: `equals()` e `hashCode()` em Entidades JPA

![Thumbnail do vídeo](docs/thumbnail.png)

> 🎥 **Assista ao vídeo:** [https://youtu.be/Qw4dSTIG9oY]

Projeto de exemplo com **Spring Boot + Spring Data JPA** mostrando como implementar `equals()` e `hashCode()` em uma entidade JPA de forma segura para produção, usando um **business key (UUID)** e `Hibernate.getClass()`.

---

## 🧨 O problema

Toda entidade JPA vai, cedo ou tarde, parar dentro de um `Set`, de um `Map` ou de uma comparação com `equals()`. Se você não sobrescreve esses métodos, o Java usa a implementação padrão de `Object`, que **compara apenas a referência na memória**.

Isso significa que o mesmo produto, carregado duas vezes do banco, pode ser considerado **dois objetos diferentes**:

```java
Produto a = repository.findById(1L).get();
Produto b = outroContexto.findById(1L).get();

a.equals(b); // false 😱 mesmo sendo o mesmo registro no banco
```

E usar o `id` gerado pelo banco também não resolve: ele é `null` antes do `save()` e muda depois. O `hashCode()` muda junto, e a entidade **"some" de dentro do `HashSet`**.

É uma bomba-relógio: funciona no seu teste, explode em produção.

---

## ✅ A solução

```java
@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private BigDecimal valor;

    private UUID businessId = UUID.randomUUID();

    // construtores, getters e setters...

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
```

### Por que isso funciona?

| Decisão | Por quê |
|---|---|
| `businessId = UUID.randomUUID()` | O identificador existe **desde a criação do objeto**, antes do `save()`. O `hashCode()` nunca muda. |
| Sem `setBusinessId()` | O business key é **imutável**. Ninguém altera a identidade do produto por engano. |
| `Hibernate.getClass(...)` | Compara a **classe real** da entidade, mesmo quando o Hibernate entrega um *proxy* (lazy loading). |
| `produto.getBusinessId()` | Acessa pelo **getter**, e não pelo campo, para funcionar corretamente com proxies não inicializados. |
| Não usa o `id` do banco | O `id` é `null` antes de persistir, então não serve como base para `equals()`/`hashCode()`. |

> 💡 Quando o Hibernate carrega um `Produto` do banco, ele chama o construtor vazio (que gera um UUID temporário) e depois **sobrescreve** o campo com o valor salvo. A identidade continua sendo sempre a persistida.

---

## 📁 Estrutura do projeto

```
src/main/java/com/ecommerce/produto
├── ProdutoApplication.java        # Classe principal + carga inicial de dados
├── model
│   └── Produto.java               # Entidade com equals() e hashCode()
└── repository
    └── ProdutoRepository.java     # JpaRepository<Produto, Long>
```

---

## 🛠️ Tecnologias

- **Java 25+** (o projeto usa `IO.println`)
- **Spring Boot** [VERSÃO]
- **Spring Data JPA / Hibernate**
- **Banco de dados:** [H2 / PostgreSQL / MySQL]

---

## ▶️ Como rodar

```bash
git clone [URL DO REPOSITÓRIO]
cd [NOME DO PROJETO]
./mvnw spring-boot:run
```

Ao iniciar, o `CommandLineRunner` salva dois produtos e imprime:

```
Produto na BD: 2
```

---

## 🙌 Gostou?

Se este projeto te ajudou:

- ⭐ Deixe uma estrela no repositório
- 🔔 Inscreva-se no canal: [LINK DO CANAL]
- 💬 Comente no vídeo qual bug em produção você já enfrentou com JPA
