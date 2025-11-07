# Command Reference

## Personal Finance Optimization Project - Quick Command Reference

### Maven Commands

#### Build and Compile
```powershell
# Clean and compile
mvn clean compile

# Clean, compile, and run tests
mvn clean test

# Clean, compile, test, and package
mvn clean package
```

#### Running the Application
```powershell
# Run with Maven exec plugin
mvn exec:java -Dexec.mainClass="com.finance.Main"

# Run with increased memory (if needed)
mvn exec:java -Dexec.mainClass="com.finance.Main" -Dexec.args="-Xmx2g"

# Run the packaged JAR
java -jar target/debt-repayment-1.0-SNAPSHOT.jar
```

#### Testing
```powershell
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=DebtTest

# Run with verbose output
mvn test -X
```

#### Maintenance
```powershell
# Update dependencies
mvn clean install -U

# Display dependency tree
mvn dependency:tree

# Check for plugin updates
mvn versions:display-plugin-updates

# Clean target directory
mvn clean
```

### Java Commands (Alternative)

#### Compile Manually
```powershell
# Compile all Java files
javac -d target/classes -cp "~/.m2/repository/org/jfree/jfreechart/1.5.3/*" src/main/java/com/finance/*.java

# Compile with classpath
javac -d target/classes -sourcepath src/main/java src/main/java/com/finance/*.java
```

#### Run Manually
```powershell
# Run Main class
java -cp "target/classes;~/.m2/repository/org/jfree/jfreechart/1.5.3/*" com.finance.Main
```

### Project Management

#### Initialize/Reset
```powershell
# Full clean and rebuild
mvn clean install

# Remove all generated files
mvn clean
Remove-Item *.csv, *.png -ErrorAction SilentlyContinue
```

#### View Project Info
```powershell
# Show effective POM
mvn help:effective-pom

# Show project dependencies
mvn dependency:list

# Show project properties
mvn help:system
```

### Windows-Specific Commands

#### File Operations
```powershell
# List Java source files
Get-ChildItem -Recurse -Include *.java

# Count lines of code
Get-ChildItem -Recurse -Include *.java | Get-Content | Measure-Object -Line

# View generated outputs
Get-ChildItem *.csv, *.png

# Open CSV in Excel
Start-Process "debt_runtime.csv"

# Open PNG charts
Start-Process "debt_performance.png"
```

#### Environment Setup
```powershell
# Check Java version
java -version

# Check Maven version
mvn -version

# Set JAVA_HOME (if needed)
$env:JAVA_HOME = "C:\Program Files\Java\jdk-11"

# Add Maven to PATH (if needed)
$env:PATH += ";C:\Program Files\Apache\maven\bin"
```

### Troubleshooting Commands

#### Diagnose Issues
```powershell
# Verbose Maven output
mvn -X clean compile

# Skip tests if failing
mvn clean package -DskipTests

# Force update dependencies
mvn clean install -U

# Check classpath
mvn dependency:build-classpath
```

#### Clean Up
```powershell
# Remove Maven cache (if corrupted)
Remove-Item -Recurse -Force "$env:USERPROFILE\.m2\repository\org\jfree"
mvn clean install

# Remove target directory
Remove-Item -Recurse -Force target
```

### Performance Monitoring

```powershell
# Run with timing
Measure-Command { mvn exec:java -Dexec.mainClass="com.finance.Main" }

# Monitor memory usage
java -Xms256m -Xmx1024m -XX:+PrintGCDetails -jar target/debt-repayment-1.0-SNAPSHOT.jar
```

### Quick One-Liners

```powershell
# Build and run in one command
mvn clean compile ; mvn exec:java -Dexec.mainClass="com.finance.Main"

# Test and run
mvn clean test ; mvn exec:java -Dexec.mainClass="com.finance.Main"

# Full pipeline
mvn clean test package ; java -jar target/debt-repayment-1.0-SNAPSHOT.jar
```

### File Viewing

```powershell
# View CSV data
Get-Content debt_runtime.csv | Format-Table

# Open all charts
Get-ChildItem *.png | ForEach-Object { Start-Process $_.FullName }

# View specific Java file
Get-Content src\main\java\com\finance\Main.java | more
```

### IDE Integration

#### VS Code
```powershell
# Open project in VS Code
code .

# Open specific file
code src\main\java\com\finance\Main.java
```

#### IntelliJ IDEA
```powershell
# Generate IntelliJ files
mvn idea:idea

# Open project
idea .
```

#### Eclipse
```powershell
# Generate Eclipse files
mvn eclipse:eclipse

# Import into Eclipse workspace
# File > Import > Existing Maven Projects
```

---

## Most Common Commands

### First Time Setup
```powershell
mvn clean compile
```

### Run the Application
```powershell
mvn exec:java -Dexec.mainClass="com.finance.Main"
```

### Run Tests
```powershell
mvn test
```

### Full Build
```powershell
mvn clean package
```

### View Results
```powershell
Get-Content debt_runtime.csv
Start-Process debt_performance.png
```

---

**Pro Tip**: Add these to a PowerShell profile for quick access:
```powershell
# Add to $PROFILE
function Build-Finance { mvn clean compile }
function Run-Finance { mvn exec:java -Dexec.mainClass="com.finance.Main" }
function Test-Finance { mvn test }
```

Then use: `Build-Finance`, `Run-Finance`, `Test-Finance`
