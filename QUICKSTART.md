# Quick Start Guide

## Personal Finance Optimization Project

### Step 1: Prerequisites

Ensure you have installed:
- **Java Development Kit (JDK) 11 or higher**
  - Check: `java -version`
- **Apache Maven 3.6 or higher**
  - Check: `mvn -version`

### Step 2: Build the Project

Open a terminal in the project directory and run:

```bash
mvn clean compile
```

This will:
- Download all dependencies (JFreeChart, JUnit)
- Compile all Java source files
- Set up the project structure

### Step 3: Run the Application

Execute the main program:

```bash
mvn exec:java -Dexec.mainClass="com.finance.Main"
```

**OR** compile and run with Java directly:

```bash
mvn clean package
java -jar target/debt-repayment-1.0-SNAPSHOT.jar
```

### Step 4: View Results

After running, check the following output files:

1. **Console Output** - Demonstration results and summaries
2. **debt_runtime.csv** - Performance test data
3. **PNG Charts** (generated in project root):
   - `debt_performance.png` - Runtime analysis
   - `years_vs_contribution.png` - Savings analysis
   - `years_vs_interest_rate.png` - Interest rate impact
   - `years_vs_target.png` - Target amount analysis

### Step 5: Run Tests (Optional)

To run unit tests:

```bash
mvn test
```

This validates:
- Debt class functionality
- Debt simulator correctness
- Savings calculator accuracy

### Alternative: Run Directly in IntelliJ IDEA (Easiest Method)

**No Maven installation needed!**

1. **Open IntelliJ IDEA**
2. **File → Open** → Select the project folder (`Debt Repayment`)
3. IntelliJ will automatically detect `pom.xml` and download dependencies
4. Wait for indexing and dependency resolution to complete
5. Navigate to: `src/main/java/com/finance/Main.java`
6. Right-click on `Main.java` → **Run 'Main.main()'**
   - Or click the run icon next to `public static void main`
7. All outputs will be generated automatically:
   - Console output in the Run window
   - `debt_runtime.csv` in project root
   - All PNG chart files in project root

**That's it!** IntelliJ handles everything (Maven, dependencies, compilation, execution).

### Expected Output

The program will:

1. **Demonstrate Debt Repayment**
   - Shows the Avalanche strategy with sample debts
   - Outputs months to payoff and total interest

2. **Demonstrate Savings Goals**
   - Calculates time to reach various savings targets
   - Shows years and months needed

3. **Run Performance Experiments**
   - Tests with 10 to 1000 debts
   - Measures runtime and exports to CSV

4. **Generate Analysis Graphs**
   - Creates 4 PNG charts analyzing savings parameters

5. **Compare Strategies**
   - Shows Avalanche vs Snowball comparison
   - Proves Avalanche saves more on interest

### Troubleshooting

**Issue: Maven not found**
- Install Maven from: https://maven.apache.org/download.cgi
- Add Maven bin directory to PATH

**Issue: Java version error**
- Update to JDK 11 or higher
- Set JAVA_HOME environment variable

**Issue: Charts not generating**
- Ensure JFreeChart dependency downloaded correctly
- Check internet connection and re-run `mvn clean compile`

**Issue: OutOfMemoryError**
- Increase heap size: `mvn exec:java -Dexec.mainClass="com.finance.Main" -Dexec.args="-Xmx2g"`

### Customizing the Code

#### Modify Debt Scenario

Edit `Main.java` - `demonstrateDebtRepayment()` method:

```java
List<Debt> demoDebts = new ArrayList<>();
demoDebts.add(new Debt(YOUR_PRINCIPAL, YOUR_RATE));
// Add more debts...

DebtSimulator simulator = new DebtSimulator(demoDebts, YOUR_BUDGET);
```

#### Modify Savings Scenario

Edit `Main.java` - `demonstrateSavingsGoal()` method:

```java
SavingsCalculator calc = new SavingsCalculator(
    INITIAL_AMOUNT,
    ANNUAL_CONTRIBUTION,
    INTEREST_RATE,
    TARGET_AMOUNT
);
```

#### Adjust Experiments

Change the debt counts in `runDebtPerformanceExperiments()`:

```java
int[] debtCounts = {10, 50, 100, 200, 500, 1000}; // Modify as needed
```

### Next Steps

- Review the generated charts to understand parameter relationships
- Examine the CSV file for detailed performance data
- Modify scenarios to match your personal finance situation
- Extend the code with additional features (see README.md)

### Support

For questions or issues:
1. Check the main README.md for detailed documentation
2. Review the JavaDoc comments in source files
3. Run unit tests to validate functionality
4. Check the technical report PDF for algorithm details

---

**Project**: Personal Finance Optimization  
**Course**: Analysis of Algorithms  
**Year**: 2025
