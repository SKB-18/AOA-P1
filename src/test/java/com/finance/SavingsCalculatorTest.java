package com.finance;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the SavingsCalculator class.
 */
public class SavingsCalculatorTest {

    private static final double DELTA = 0.1; // Tolerance for time in years

    @Test
    public void testAlreadyAtGoal() {
        SavingsCalculator calc = new SavingsCalculator(10000, 0, 0.05, 5000);
        double years = calc.yearsToReachTarget();
        assertEquals(0.0, years, DELTA);
    }

    @Test
    public void testNoInterestLinearGrowth() {
        // $0 initial, $1000/year contribution, 0% interest, target $10,000
        SavingsCalculator calc = new SavingsCalculator(0, 1000, 0.0, 10000);
        double years = calc.yearsToReachTarget();
        
        // Should take exactly 10 years (10 * 1000 = 10,000)
        assertEquals(10.0, years, DELTA);
    }

    @Test
    public void testDoublingWithInterestOnly() {
        // $1000 initial, no contributions, 5% interest, target $2000
        // Rule of 72: ~14.2 years to double at 5%
        SavingsCalculator calc = new SavingsCalculator(1000, 0, 0.05, 2000);
        double years = calc.yearsToReachTarget();
        
        // Should be around 14.2 years
        assertTrue(years > 13 && years < 15);
    }

    @Test
    public void testWithContributionsAndInterest() {
        // $0 initial, $1000/year, 5% interest, target $10,000
        SavingsCalculator calc = new SavingsCalculator(0, 1000, 0.05, 10000);
        double years = calc.yearsToReachTarget();
        
        // Should be less than 10 years due to compound interest
        assertTrue(years < 10);
        assertTrue(years > 8); // But more than 8 years
    }

    @Test
    public void testHighInterestReducesTime() {
        // Compare 5% vs 10% interest
        SavingsCalculator calc5 = new SavingsCalculator(0, 5000, 0.05, 100000);
        SavingsCalculator calc10 = new SavingsCalculator(0, 5000, 0.10, 100000);
        
        double years5 = calc5.yearsToReachTarget();
        double years10 = calc10.yearsToReachTarget();
        
        // Higher interest should reduce time needed
        assertTrue(years10 < years5);
    }

    @Test
    public void testHigherContributionReducesTime() {
        // Compare $1000/year vs $5000/year
        SavingsCalculator calc1k = new SavingsCalculator(0, 1000, 0.05, 50000);
        SavingsCalculator calc5k = new SavingsCalculator(0, 5000, 0.05, 50000);
        
        double years1k = calc1k.yearsToReachTarget();
        double years5k = calc5k.yearsToReachTarget();
        
        // Higher contribution should significantly reduce time
        assertTrue(years5k < years1k);
    }

    @Test(expected = IllegalStateException.class)
    public void testImpossibleScenario() {
        // No contributions, no interest, but target > initial
        SavingsCalculator calc = new SavingsCalculator(1000, 0, 0.0, 5000);
        calc.yearsToReachTarget(); // Should throw exception
    }

    @Test
    public void testFormatYearsAndMonths() {
        String result1 = SavingsCalculator.formatYearsAndMonths(5.0);
        assertEquals("5 years", result1);
        
        String result2 = SavingsCalculator.formatYearsAndMonths(5.5);
        assertEquals("5 years and 6 months", result2);
        
        String result3 = SavingsCalculator.formatYearsAndMonths(0.25);
        assertEquals("3 months", result3);
    }
}
