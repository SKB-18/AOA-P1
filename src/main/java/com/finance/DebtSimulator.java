package com.finance;

import java.util.ArrayList;
import java.util.List;

/**
 * Simulates debt repayment using different strategies.
 * Supports both Avalanche (greedy - highest rate) and Snowball (lowest balance) strategies.
 *
 * Algorithm (Avalanche):
 * 1. Sort debts by interest rate (descending)
 * 2. Each month, make minimum payments (interest-only) on all debts except the highest-rate debt
 * 3. Apply all remaining budget to the highest-rate debt
 * 4. When a debt is paid off, move to the next highest-rate debt
 *
 * Algorithm (Snowball):
 * 1. Sort debts by balance (ascending)
 * 2. Each month, make minimum payments on all debts except the lowest-balance debt
 * 3. Apply all remaining budget to the lowest-balance debt
 * 4. When a debt is paid off, move to the next lowest-balance debt
 *
 * Time Complexity: O(N * M) where N is number of debts and M is months to payoff
 * Space Complexity: O(N) for storing debts and payoff order
 *
 * @author Personal Finance Optimization Project
 * @version 2.0 (FIXED - supports both strategies)
 */
public class DebtSimulator {

    public enum Strategy {
        AVALANCHE,  // Pay highest interest rate first (GREEDY - OPTIMAL)
        SNOWBALL    // Pay lowest balance first (psychological motivation)
    }

    private List<Debt> debts;
    private double monthlyBudget;
    private double totalInterestPaid;
    private int monthsElapsed;
    private List<Debt> payoffOrder;
    private Strategy strategy;

    /**
     * Constructs a new DebtSimulator with AVALANCHE strategy (default - greedy optimal).
     *
     * @param debts         the list of debts to pay off
     * @param monthlyBudget the total budget available each month
     */
    public DebtSimulator(List<Debt> debts, double monthlyBudget) {
        this(debts, monthlyBudget, Strategy.AVALANCHE);
    }

    /**
     * Constructs a new DebtSimulator with specified strategy.
     *
     * @param debts         the list of debts to pay off
     * @param monthlyBudget the total budget available each month
     * @param strategy      AVALANCHE (highest rate) or SNOWBALL (lowest balance)
     */
    public DebtSimulator(List<Debt> debts, double monthlyBudget, Strategy strategy) {
        this.debts = new ArrayList<>();
        // Create deep copy to avoid modifying original list
        for (Debt d : debts) {
            this.debts.add(new Debt(d.getPrincipal(), d.getAnnualRate()));
        }

        this.monthlyBudget = monthlyBudget;
        this.totalInterestPaid = 0;
        this.monthsElapsed = 0;
        this.payoffOrder = new ArrayList<>();
        this.strategy = strategy;

        // Sort based on strategy
        sortDebts();
    }

    /**
     * Sort debts based on the selected strategy.
     */
    private void sortDebts() {
        if (strategy == Strategy.AVALANCHE) {
            // Sort by interest rate (descending) - GREEDY CHOICE
            debts.sort((d1, d2) -> Double.compare(d2.getAnnualRate(), d1.getAnnualRate()));
        } else {
            // Sort by balance (ascending) - Snowball
            debts.sort((d1, d2) -> Double.compare(d1.getPrincipal(), d2.getPrincipal()));
        }
    }

    /**
     * Runs the debt repayment simulation month-by-month until all debts are paid.
     */
    public void simulate() {
        while (!debts.isEmpty()) {
            monthsElapsed++;
            double remainingBudget = monthlyBudget;

            // Calculate total interest for this month
            double monthlyInterest = 0;
            for (Debt d : debts) {
                monthlyInterest += d.monthlyInterest();
            }

            // Check if budget can even cover the interest
            if (monthlyBudget < monthlyInterest) {
                System.out.println("WARNING: Budget insufficient to cover interest!");
                System.out.println("Month " + monthsElapsed + ": Interest = $" +
                        String.format("%.2f", monthlyInterest) +
                        ", Budget = $" + monthlyBudget);
                break;
            }

            // Pay interest on all debts except the first (target)
            for (int i = 1; i < debts.size(); i++) {
                Debt d = debts.get(i);
                double interest = d.monthlyInterest();
                d.applyPayment(interest);
                remainingBudget -= interest;
                totalInterestPaid += interest;
            }

            // Apply all remaining budget to the target debt (first in sorted list)
            Debt targetDebt = debts.get(0);
            double targetInterest = targetDebt.monthlyInterest();
            totalInterestPaid += targetInterest;

            double excessPayment = targetDebt.applyPayment(remainingBudget);
            remainingBudget = excessPayment;

            // Check if target debt is paid off
            if (targetDebt.getPrincipal() <= 0.01) {
                payoffOrder.add(new Debt(targetDebt.getOriginalPrincipal(),
                        targetDebt.getOriginalRate()));
                debts.remove(0);
            }
        }
    }

    /**
     * Prints a summary of the simulation results.
     */
    public void printSummary() {
        System.out.println("\n========== DEBT REPAYMENT SIMULATION RESULTS ==========");
        System.out.println("Strategy: " + strategy);
        System.out.println("Monthly Budget: $" + String.format("%.2f", monthlyBudget));
        System.out.println("Months to Payoff: " + monthsElapsed +
                " (" + String.format("%.1f", monthsElapsed / 12.0) + " years)");
        System.out.println("Total Interest Paid: $" + String.format("%.2f", totalInterestPaid));

        System.out.println("\nPayoff Order (" + strategy + " Strategy):");
        for (int i = 0; i < payoffOrder.size(); i++) {
            Debt d = payoffOrder.get(i);
            System.out.println("  " + (i + 1) + ". " + d.toString());
        }
        System.out.println("=======================================================\n");
    }

    // Getters
    public int getMonthsElapsed() {
        return monthsElapsed;
    }

    public double getTotalInterestPaid() {
        return totalInterestPaid;
    }

    public List<Debt> getPayoffOrder() {
        return payoffOrder;
    }

    public Strategy getStrategy() {
        return strategy;
    }
}