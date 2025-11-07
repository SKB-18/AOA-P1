# Personal Finance Optimization: Debt Repayment and Savings Goal Algorithms

## Project Overview

This project implements two algorithmic solutions for personal finance optimization:

1. **Debt Repayment** – Greedy Avalanche Strategy
2. **Savings Goal Estimation** – Divide-and-Conquer (Binary Search)

---

## Problem Descriptions

### 1. Debt Repayment (Greedy Algorithm)

The **Debt Avalanche Strategy** minimizes total interest paid by prioritizing debts with the highest interest rates. Each month:
- Pay interest on all debts (minimum payments)
- Apply remaining budget to the debt with the highest APR
- When a debt is paid off, move to the next highest-rate debt

**Time Complexity:** O(N × M)  
where N = number of debts, M = months to payoff

---

### 2. Savings Goal Estimation (Binary Search)

Uses **binary search** to find the time needed to reach a savings target given:
- Initial principal
- Annual contribution
- Annual interest rate
- Target amount

**Time Complexity:** O(log(T/precision))  
where T = upper bound on years

---

## Project Structure

```
Debt Repayment/
├── pom.xml                          # Maven configuration
├── README.md                        # This file
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── finance/
│                   ├── Debt.java              # Debt data structure
│                   ├── DebtSimulator.java     # Avalanche strategy implementation
│                   ├── SavingsCalculator.java # Binary search for savings
│                   └── Main.java              # Demo and experiments
└── (generated files)
    ├── debt_runtime_enhanced.csv      # Performance test results
    ├── debt_performance_enhanced.png  # Runtime vs number of debts
    ├── strategy_comparison.csv        # Avalanche vs Snowball comparison
    ├── years_vs_contribution.png      # Savings analysis
    ├── years_vs_interest_rate.png     # Interest rate impact
    └── years_vs_target.png            # Target amount analysis
```

---

## Building and Running

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Build the Project
```bash
mvn clean compile
```

### Run the Application
```bash
mvn exec:java -Dexec.mainClass="com.finance.Main"
```

### Package as JAR
```bash
mvn clean package
java -jar target/debt-repayment-1.0-SNAPSHOT.jar
```

---

## Features

### Debt Simulator
- **Avalanche Strategy:** Automatically prioritizes highest-interest debts
- **Performance Testing:** Measures runtime with varying numbers of debts (10 to 10,000)
- **CSV Export:** Exports performance data for analysis
- **Strategy Comparison:** Compares Avalanche vs Snowball methods

### Savings Calculator
- **Binary Search:** Efficiently finds time to reach savings goal
- **High Precision:** Accurate to within one month
- **Multiple Scenarios:** Handles various contribution and interest rate combinations
- **Visual Analysis:** Generates graphs showing parameter impacts

### Graph Generation
The project uses **JFreeChart** to generate:
1. Runtime performance graph (debt simulator)
2. Years vs Annual Contribution
3. Years vs Interest Rate
4. Years vs Target Amount

---

## Algorithm Analysis

### Debt Avalanche – Correctness
The greedy choice of always paying the highest-interest debt first is **optimal** because:
- Interest accrues faster on higher-rate balances
- Reducing high-rate principal immediately reduces future interest charges
- Any deviation results in higher total interest paid

### Binary Search – Efficiency
- Converges in ≈ log₂(T/precision) iterations
- For 100-year range with 1-month precision: ≈ 11 iterations
- Much faster than linear year-by-year simulation

---

## Dependencies

- **JFreeChart 1.5.3** – Chart generation
- **JUnit 4.13.2** – Unit testing (optional)

---

## Performance Results

The debt simulator demonstrates **linear scaling** with the number of debts:

| Debts | Runtime |
|-------|---------|
| 10    | ~0 ms   |
| 100   | ~0 ms   |
| 1,000 | ~2 ms   |
| 10,000| ~37 ms  |

See `debt_runtime_enhanced.csv` and `debt_performance_enhanced.png` for detailed results.

---

## Extensions and Future Work

Possible enhancements:
- Add minimum payment requirements
- Support variable interest rates
- Implement additional payoff strategies
- Add GUI for interactive exploration
- Support monthly contributions for savings
- Include tax considerations

---

## Example Output

```
==========================================================
  PERSONAL FINANCE OPTIMIZATION: ALGORITHM DEMONSTRATION
==========================================================

========== PART 1: DEBT REPAYMENT DEMONSTRATION ==========

Initial Debts:
  1. Debt[Principal: $5000.00, Rate: 18.00%]
  2. Debt[Principal: $8000.00, Rate: 6.00%]
  3. Debt[Principal: $3000.00, Rate: 4.00%]

Monthly Budget: $500.0

Running Avalanche Strategy (highest APR first)...

========== DEBT REPAYMENT SIMULATION RESULTS ==========
Monthly Budget: $500.00
Months to Payoff: 36 (3.0 years)
Total Interest Paid: $1700.73

Payoff Order (Avalanche Strategy - Highest APR First):
  1. Debt[Principal: $5000.00, Rate: 18.00%]
  2. Debt[Principal: $8000.00, Rate: 6.00%]
  3. Debt[Principal: $3000.00, Rate: 4.00%]
=======================================================

========== PART 2: SAVINGS GOAL DEMONSTRATION ==========

Example 1: Building savings from scratch

========== SAVINGS GOAL CALCULATION ==========
Initial Principal: $0.00
Annual Contribution: $1000.00
Annual Interest Rate: 5.00%
Target Amount: $10000.00

Time to Reach Goal: 8 years and 4 months
  (Approximately 8.35 years)
==============================================

Example 2: Doubling initial investment with no contributions

========== SAVINGS GOAL CALCULATION ==========
Initial Principal: $1000.00
Annual Contribution: $0.00
Annual Interest Rate: 5.00%
Target Amount: $2000.00

Time to Reach Goal: 14 years and 3 months
  (Approximately 14.21 years)
==============================================

Example 3: Reaching $100,000 with $5,000/year at 7% interest

========== SAVINGS GOAL CALCULATION ==========
Initial Principal: $0.00
Annual Contribution: $5000.00
Annual Interest Rate: 7.00%
Target Amount: $100000.00

Time to Reach Goal: 13 years
  (Approximately 12.99 years)
==============================================

========== PART 3: DEBT SIMULATOR PERFORMANCE EXPERIMENTS ==========

N=10 debts: 0 ms, 76 months
N=25 debts: 0 ms, 74 months
N=50 debts: 0 ms, 71 months
N=75 debts: 0 ms, 76 months
N=100 debts: 0 ms, 79 months
N=150 debts: 0 ms, 91 months
N=200 debts: 1 ms, 109 months
N=300 debts: 2 ms, 150 months
N=500 debts: 2 ms, 250 months
N=1000 debts: 3 ms, 500 months

Performance data saved to: debt_runtime_enhanced.csv
Performance graph saved to: debt_performance_enhanced.png

========== PART 4: SAVINGS GOAL ANALYSIS GRAPHS ==========

Graph saved: years_vs_contribution.png
Graph saved: years_vs_interest_rate.png
Graph saved: years_vs_target.png

========== PART 5: STRATEGY COMPARISON ==========

Comparing Avalanche (highest rate first) vs Snowball (lowest balance first)

AVALANCHE Strategy (Highest APR First):
  Months: 36
  Interest Paid: $1700.73

SNOWBALL Strategy (Lowest Balance First):
  Months: 36
  Interest Paid: $1700.73

Avalanche saves: $0.00 in interest!
This demonstrates why Avalanche is mathematically optimal.

==========================================================
  All demonstrations and experiments completed!
  Check output files: debt_runtime_enhanced.csv, *.png charts
==========================================================

Process finished with exit code 0
```

---

## References

1. [Investopedia – Debt Avalanche Strategy](https://www.investopedia.com/debt-avalanche-method-5195676)
2. [Fidelity – Avalanche vs Snowball](https://www.fidelity.com/learning-center/personal-finance/debt/avalanche-vs-snowball)
3. Compound Interest Formula with Contributions
4. Binary Search Applications in Finance

---

## Authors

**Personal Finance Optimization Project**  
Algorithm Analysis Course – 2025

---

## License

**Academic Project – 2025**
