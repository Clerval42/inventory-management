
package com.inventorycontrol;

public class InventoryManagement {

    public static double calculateHoldingCost(double I, double c) {
        return I * c;
    }
    public static double calculateAnnualDemand(double muL, double L) {
        return (12 / L) * muL;
    }
    public static double calculateInitialEOQ(double k, double lambda, double h) {
        return Math.sqrt((2 * k * lambda) / h);
    }
    public static double calculateServiceLevel(double Q, double h, double rho, double lambda) {
        return 1 - (Q * h) / (rho * lambda);
    }
    public static double calculateReorderPoint(double muL, double z, double sigmaL) {
        return muL + z * sigmaL;
    }
    public static double calculateSafetyStock(double z, double sigmaL) {
        return z * sigmaL;
    }
    public static double calculateExpectedBackorders(double sigmaL, double Lz) {
        return sigmaL * Lz;
    }
    public static double calculateAdjustedEOQ(double lambda, double k, double rho, double nR, double h) {
        return Math.sqrt((2 * lambda * (k + rho * nR)) / h);
    }
    public static double calculateHoldingCostAnnual(double h, double Q, double R, double muL) {
        return h * (Q / 2 + R - muL);
    }
    public static double calculateOrderingCostAnnual(double k, double lambda, double Q) {
        return (k * lambda) / Q;
    }
    public static double calculatePenaltyCostAnnual(double lambda, double Q, double rho, double nR) {
        return (lambda / Q) * rho * nR;
    }
    public static double calculateTimeBetweenOrders(double Q, double lambda) {
        return Q / lambda;
    }
    public static double calculateProportionUnmetDemand(double nR, double Q) {
        return nR / Q;
    }
}
