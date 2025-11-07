package com.finance;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Unit tests for the DebtSimulator class.
 */
public class DebtSimulatorTest {

    private static final double DELTA = 0.01;

    @Test
    public void testSimpleScenario() {
        List<Debt> debts = new ArrayList<>();
        debts.add(new Debt(1000, 0.12)); // $1000 at 12%
        
        // Budget of $100/month should pay this off in about 11-12 months
        DebtSimulator sim = new DebtSimulator(debts, 100);
        sim.simulate();
        
        assertTrue(sim.getMonthsElapsed() > 0);
        assertTrue(sim.getTotalInterestPaid() > 0);
        assertEquals(1, sim.getPayoffOrder().size());
    }

    @Test
    public void testAvalanchePriority() {
        List<Debt> debts = new ArrayList<>();
        debts.add(new Debt(1000, 0.05)); // Lower rate
        debts.add(new Debt(1000, 0.20)); // Higher rate
        
        DebtSimulator sim = new DebtSimulator(debts, 200);
        sim.simulate();
        
        // First debt paid off should be the higher-rate one
        Debt firstPaid = sim.getPayoffOrder().get(0);
        assertEquals(0.20, firstPaid.getOriginalRate(), DELTA);
    }

    @Test
    public void testMultipleDebts() {
        List<Debt> debts = new ArrayList<>();
        debts.add(new Debt(5000, 0.18));
        debts.add(new Debt(8000, 0.06));
        debts.add(new Debt(3000, 0.04));
        
        DebtSimulator sim = new DebtSimulator(debts, 500);
        sim.simulate();
        
        // All debts should be paid off
        assertEquals(3, sim.getPayoffOrder().size());
        assertTrue(sim.getMonthsElapsed() > 0);
        assertTrue(sim.getTotalInterestPaid() > 0);
    }

    @Test
    public void testInsufficientBudget() {
        List<Debt> debts = new ArrayList<>();
        debts.add(new Debt(10000, 0.25));  // $10k at 25% (monthly interest ~$208)
        debts.add(new Debt(5000, 0.20));   // $5k at 20% (monthly interest ~$83)
        debts.add(new Debt(3000, 0.15));   // $3k at 15% (monthly interest ~$37.50)
        // Total interest on non-target debts: $83 + $37.50 = $120.50
        // Budget is only $100, so budget goes negative
        
        DebtSimulator sim = new DebtSimulator(debts, 100);
        sim.simulate();
        
        // Should detect insufficient budget and stop immediately at month 1
        assertEquals(1, sim.getMonthsElapsed());
        // No debts should be paid off
        assertEquals(0, sim.getPayoffOrder().size());
    }
}
