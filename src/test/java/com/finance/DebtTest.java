package com.finance;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the Debt class.
 */
public class DebtTest {

    private static final double DELTA = 0.01; // Tolerance for floating-point comparisons

    @Test
    public void testDebtCreation() {
        Debt debt = new Debt(5000, 0.18);
        assertEquals(5000, debt.getPrincipal(), DELTA);
        assertEquals(0.18, debt.getAnnualRate(), DELTA);
    }

    @Test
    public void testMonthlyInterest() {
        Debt debt = new Debt(5000, 0.18); // 18% annual = 1.5% monthly
        double expectedInterest = 5000 * (0.18 / 12.0);
        assertEquals(expectedInterest, debt.monthlyInterest(), DELTA);
    }

    @Test
    public void testPartialPayment() {
        Debt debt = new Debt(1000, 0.12); // $1000 at 12% annual
        double interest = debt.monthlyInterest(); // $10
        
        // Pay $50 (covers interest + $40 principal)
        double leftover = debt.applyPayment(50);
        
        assertEquals(0, leftover, DELTA); // No leftover
        assertEquals(1000 + interest - 50, debt.getPrincipal(), DELTA);
    }

    @Test
    public void testFullPayment() {
        Debt debt = new Debt(100, 0.12);
        double interest = debt.monthlyInterest();
        
        // Pay more than principal + interest
        double leftover = debt.applyPayment(200);
        
        assertTrue(leftover > 0); // Should have leftover
        assertTrue(debt.isPaidOff()); // Debt should be paid off
    }

    @Test
    public void testIsPaidOff() {
        Debt debt = new Debt(100, 0.12);
        assertFalse(debt.isPaidOff());
        
        // Pay off completely
        debt.applyPayment(200);
        assertTrue(debt.isPaidOff());
    }
}
