
package com.finance;

/**
 * Uses binary search (divide-and-conquer) to calculate the time needed to reach a savings goal.
 * Given an initial principal, annual contribution, annual interest rate, and target amount,
 * this class determines the number of years required to reach or exceed the target.
 *
 * Algorithm:
 * 1. Set up binary search bounds: low = 0, high = estimated upper bound
 * 2. For each iteration, calculate balance at midpoint time
 * 3. If balance < target, search upper half; if balance >= target, search lower half
 * 4. Continue until convergence within desired precision (e.g., 1 month)
 *
 * Time Complexity: O(log(T/precision)) where T is the upper bound on years
 * Space Complexity: O(1)
 *
 * @author Personal Finance Optimization Project
 * @version 1.2 (COMPLETE FIX - all methods added)
 */
public class SavingsCalculator {

    private double initialPrincipal;
    private double annualContribution;
    private double annualRate;
    private double targetAmount;
    private double precision;  // in years (e.g., 1/12 for monthly precision)

    /**
     * Constructs a new SavingsCalculator with the specified parameters.
     *
     * @param initialPrincipal  the starting balance
     * @param annualContribution the amount contributed each year
     * @param annualRate        the annual interest rate (e.g., 0.05 for 5%)
     * @param targetAmount      the savings goal
     */
    public SavingsCalculator(double initialPrincipal, double annualContribution,
                             double annualRate, double targetAmount) {
        this.initialPrincipal = initialPrincipal;
        this.annualContribution = annualContribution;
        this.annualRate = annualRate;
        this.targetAmount = targetAmount;
        this.precision = 1.0 / 12.0;  // Default to monthly precision
    }

    /**
     * Calculates the number of years needed to reach the target amount using binary search.
     * This is a divide-and-conquer approach: repeatedly divide the search space in half.
     *
     * @return the time in years to reach the target
     */
    public double yearsToReachTarget() {
        // If already at or above target, return 0
        if (initialPrincipal >= targetAmount) {
            return 0.0;
        }

        // Check if goal is reachable
        if (annualContribution <= 0 && annualRate <= 0) {
            throw new IllegalStateException("Cannot reach target: no contributions and no interest");
        }

        // Binary search bounds
        double low = 0.0;
        double high = estimateUpperBoundYears();

        // Binary search for the time T where balance(T) >= target
        while (high - low > precision) {
            double mid = (low + high) / 2.0;
            double balanceMid = balanceAfterYears(mid);

            if (balanceMid < targetAmount) {
                // Need more time
                low = mid;
            } else {
                // Reached or exceeded target
                high = mid;
            }
        }

        return high;  // Return the upper bound (ensures we meet or exceed target)
    }

    /**
     * PUBLIC METHOD: Calculate the balance after a given number of years.
     * This method is now public so it can be called from Main.java for validation.
     *
     * Formula: F(t) = P(1+r)^t + C * [(1+r)^t - 1] / r
     * where P = principal, C = annual contribution, r = annual rate, t = years
     *
     * @param years the number of years
     * @return the account balance after the specified years
     */
    public double balanceAtTime(double years) {
        return balanceAfterYears(years);
    }

    /**
     * PRIVATE HELPER: Calculate the balance after a given number of years.
     * Uses the compound interest formula with regular contributions.
     *
     * @param years the number of years
     * @return the account balance after the specified years
     */
    private double balanceAfterYears(double years) {
        if (years <= 0) {
            return initialPrincipal;
        }

        // Compound interest with contributions formula
        // F(t) = P(1+r)^t + C * [(1+r)^t - 1] / r
        double growthFactor = Math.pow(1 + annualRate, years);
        double principalGrowth = initialPrincipal * growthFactor;

        // Handle the case where annual rate is 0 (no interest)
        double contributionGrowth;
        if (Math.abs(annualRate) < 1e-10) {
            contributionGrowth = annualContribution * years;
        } else {
            contributionGrowth = annualContribution * (growthFactor - 1) / annualRate;
        }

        return principalGrowth + contributionGrowth;
    }

    /**
     * NEW PUBLIC STATIC METHOD: Format years as "X years and Y months" string.
     * This is used by tests to format output.
     *
     * @param years the time in years (e.g., 5.5 for 5 years 6 months)
     * @return formatted string like "5 years and 6 months"
     */
    public static String formatYearsAndMonths(double years) {
        int wholeYears = (int) years;
        int months = (int) Math.round((years - wholeYears) * 12);

        // Handle rounding edge case: 11.99 months rounds to 12
        if (months >= 12) {
            wholeYears++;
            months = 0;
        }

        if (wholeYears == 0) {
            return months + " months";
        } else if (months == 0) {
            return wholeYears + " years";
        } else {
            return wholeYears + " years and " + months + " months";
        }
    }

    /**
     * Estimates an upper bound for the number of years needed.
     * Uses a simple heuristic: if we contributed the full amount each year with no interest,
     * how long would it take?
     *
     * @return an estimated upper bound in years
     */
    private double estimateUpperBoundYears() {
        if (annualContribution <= 0 && annualRate <= 0) {
            // No growth possible
            return 100.0;  // Return a large default value
        }

        // Pessimistic estimate: how long with just contributions and no interest?
        double deficit = targetAmount - initialPrincipal;
        if (deficit <= 0) {
            return 0.0;
        }

        if (annualContribution > 0) {
            double yearsNeeded = deficit / annualContribution;
            // Add some buffer for binary search
            return Math.max(yearsNeeded * 1.5, 100.0);
        } else {
            // Only interest, use rule of 72 as a rough estimate
            return Math.max(100.0, Math.log(targetAmount / initialPrincipal) / Math.log(1 + annualRate));
        }
    }

    /**
     * Prints a summary of the savings calculation.
     */
    public void printSummary() {
        double years = yearsToReachTarget();
        String formatted = formatYearsAndMonths(years);

        System.out.println("Savings Goal Calculation:");
        System.out.println("  Initial: $" + String.format("%.2f", initialPrincipal));
        System.out.println("  Annual Contribution: $" + String.format("%.2f", annualContribution));
        System.out.println("  Annual Interest Rate: " + String.format("%.1f", annualRate * 100) + "%");
        System.out.println("  Target: $" + String.format("%.2f", targetAmount));
        System.out.println("  Time to reach target: " + formatted);
        System.out.println("  Final balance: $" + String.format("%.2f", balanceAfterYears(years)));
        System.out.println();
    }

    // Getters
    public double getInitialPrincipal() {
        return initialPrincipal;
    }

    public double getAnnualContribution() {
        return annualContribution;
    }

    public double getAnnualRate() {
        return annualRate;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }
}