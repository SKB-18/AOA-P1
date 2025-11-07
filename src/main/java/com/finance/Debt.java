package com.finance;

/**
 * Represents a debt with a principal balance and an annual interest rate.
 * This class provides methods to calculate monthly interest and apply payments.
 * 
 * @author Personal Finance Optimization Project
 * @version 1.0
 */
public class Debt {
    private double principal;
    private double annualRate; // e.g., 0.20 for 20% annual interest
    private final double originalPrincipal;
    private final double originalRate;

    /**
     * Constructs a new Debt with the specified principal and annual interest rate.
     * 
     * @param principal the initial principal balance
     * @param annualRate the annual interest rate as a decimal (e.g., 0.18 for 18%)
     */
    public Debt(double principal, double annualRate) {
        this.principal = principal;
        this.annualRate = annualRate;
        this.originalPrincipal = principal;
        this.originalRate = annualRate;
    }

    /**
     * Calculate the interest accrued in one month on this debt.
     * 
     * @return the monthly interest amount
     */
    public double monthlyInterest() {
        double monthlyRate = annualRate / 12.0;
        return principal * monthlyRate;
    }

    /**
     * Apply a payment to this debt. This method adds the monthly interest to the
     * principal first (compounding), then deducts the payment amount.
     * 
     * @param payment the payment amount to apply
     * @return any excess payment if debt is fully paid off, otherwise 0
     */
    public double applyPayment(double payment) {
        // First, cover accrued interest for the month
        double interest = monthlyInterest();
        principal += interest; // add this month's interest to principal

        if (payment >= principal) {
            // debt will be paid off by this payment
            double excess = payment - principal;
            principal = 0.0;
            return excess; // return leftover funds to use for other debts
        } else {
            // partial payment – reduce principal
            principal -= payment;
            return 0.0;
        }
    }

    /**
     * Checks if this debt has been paid off.
     * 
     * @return true if the principal is effectively zero (within floating-point tolerance)
     */
    public boolean isPaidOff() {
        return principal <= 1e-9; // treat near-zero as paid off due to floating-point
    }

    /**
     * Gets the current principal balance.
     * 
     * @return the current principal
     */
    public double getPrincipal() {
        return principal;
    }

    /**
     * Gets the annual interest rate.
     * 
     * @return the annual interest rate as a decimal
     */
    public double getAnnualRate() {
        return annualRate;
    }

    /**
     * Gets the original principal amount.
     * 
     * @return the original principal
     */
    public double getOriginalPrincipal() {
        return originalPrincipal;
    }

    /**
     * Gets the original annual interest rate.
     * 
     * @return the original annual rate
     */
    public double getOriginalRate() {
        return originalRate;
    }

    @Override
    public String toString() {
        return String.format("Debt[Principal: $%.2f, Rate: %.2f%%]", 
                           originalPrincipal, originalRate * 100);
    }
}
