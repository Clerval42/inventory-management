# Inventory Control (Q, R) System - Java Maven Project

This project calculates optimal lot size (Q) and reorder point (R) using Economic Order Quantity with backorders.

## 📦 Requirements

- Java 17+
- Maven 3+

## 🚀 How to Run

1. Clone or unzip the project.
2. Open terminal and navigate into the project root.

### To compile and run:
```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.inventorycontrol.Main"
```

## 📌 Inputs Required at Runtime

- Unit cost (c) — e.g., 20 $/jar
- Ordering cost (k) — e.g., 100 $/order
- Penalty cost per unit (rho or P) — e.g., 20 $/unit
- Annual interest rate (I) — e.g., 0.25 (for 25%)
- Lead time in months (L) — e.g., 4
- Mean demand during lead time (mu_L or μ) — e.g., 500
- Std. deviation of lead time demand (sigma_L or σ) — e.g., 100

### Example Input Sequence

```
Enter Unit Cost (c): 20
Enter Ordering Cost (k): 100
Enter Penalty Cost per Unit (rho): 20
Enter Annual Interest Rate (I): 0.25
Enter Lead Time in Months (L): 4
Enter Mean Demand During Lead Time (mu_L): 500
Enter Std. Dev. of Demand During Lead Time (sigma_L): 100
```

## 🧾 Output

- Optimal Q and R
- Holding, ordering, penalty costs
- Service level metrics
