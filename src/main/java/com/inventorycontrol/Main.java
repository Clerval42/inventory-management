package com.inventorycontrol;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter Unit Cost (c): ");
            double c = sc.nextDouble();

            System.out.print("Enter Ordering Cost (k): ");
            double k = sc.nextDouble();

            System.out.print("Enter Penalty Cost per Unit (rho): ");
            double rho = sc.nextDouble();

            System.out.print("Enter Annual Interest Rate (I): ");
            double I = sc.nextDouble();

            System.out.print("Enter Lead Time in Months (L): ");
            double L = sc.nextDouble();

            System.out.print("Enter Mean Demand During Lead Time (mu_L): ");
            double muL = sc.nextDouble();

            System.out.print("Enter Std. Dev. of Demand During Lead Time (sigma_L): ");
            double sigmaL = sc.nextDouble();

            double h = InventoryManagement.calculateHoldingCost(I, c);
            double lambda = InventoryManagement.calculateAnnualDemand(muL, L);
            double Q0 = InventoryManagement.calculateInitialEOQ(k, lambda, h);
            double SL = InventoryManagement.calculateServiceLevel(Q0, h, rho, lambda);

            ZChartLookupTable zTable = new ZChartLookupTable("src/main/resources/zchart.txt");
            double z = zTable.getZForServiceLevel(SL);
            // Iteratively solve for Q and nR, then use final values for output
            int iterations = 2;
            double Lz = zTable.getLossFunctionValue(z);
            double nR = InventoryManagement.calculateExpectedBackorders(sigmaL, Lz);
            double Q = InventoryManagement.calculateAdjustedEOQ(lambda, k, rho, nR, h);
            for (int i = 1; i < iterations; i++) {
                SL = InventoryManagement.calculateServiceLevel(Q, h, rho, lambda);
                z = zTable.getZForServiceLevel(SL);
                Lz = zTable.getLossFunctionValue(z);
                nR = InventoryManagement.calculateExpectedBackorders(sigmaL, Lz);
                Q = InventoryManagement.calculateAdjustedEOQ(lambda, k, rho, nR, h);
            }
            double R = InventoryManagement.calculateReorderPoint(muL, z, sigmaL);
            double safetyStock = InventoryManagement.calculateSafetyStock(z, sigmaL);
            double T = InventoryManagement.calculateTimeBetweenOrders(Q, lambda);
            double HC = InventoryManagement.calculateHoldingCostAnnual(h, Q, R, muL);
            double OC = InventoryManagement.calculateOrderingCostAnnual(k, lambda, Q);
            double PC = InventoryManagement.calculatePenaltyCostAnnual(lambda, Q, rho, nR);
            double unmetDemand = InventoryManagement.calculateProportionUnmetDemand(nR, Q);

            System.out.printf("\nOptimal Lot Size (Q): %.2f\n", Q);
            System.out.printf("Reorder Point (R): %.2f\n", R);
            System.out.printf("Safety Stock: %.2f\n", safetyStock);
            System.out.printf("Time Between Orders: %.4f years\n", T);
            System.out.printf("Annual Holding Cost: %.2f\n", HC);
            System.out.printf("Annual Ordering Cost: %.2f\n", OC);
            System.out.printf("Annual Penalty Cost: %.2f\n", PC);
            System.out.printf("%% of Cycles with No Stockout: %.2f%%\n", SL * 100);
            System.out.printf("%% of Demand Not Met: %.4f%%\n", unmetDemand * 100);
        }
    }
}
