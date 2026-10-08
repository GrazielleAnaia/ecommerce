## Episode 2 — Entity & Repository

This step introduces the first persistent domain model for the application.

### What was added
- **`Product`** entity — mapped to the database with `@Entity`, `@Id`, and `@GeneratedValue`
- **`ProductRepository`** — extends `JpaRepository<Product, Long>`, giving us full CRUD operations with zero SQL
- A `CommandLineRunner` bean that seeds two sample products on startup and logs the saved count, to verify persistence is working

### Key concepts
| Concept | Purpose |
|---|---|
| `@Entity` | Marks a class as a database table |
| `@Id` / `@GeneratedValue` | Defines the primary key and lets the database auto-generate it |
| `JpaRepository` | A data access abstraction layer that handles interactions with your database |

### Verify it yourself
1. Run the app
2. Check the console for: `Products in the DB: 2`
3. Open `http://localhost:8080/h2-console` and run:
```sql
   SELECT * FROM PRODUCT;
```

📺 Watch this step: *[Episode 2 — Creating the Entity & Repository]*

---
➡️ **Next:** Episode 3 — Exposing this data through a REST API
