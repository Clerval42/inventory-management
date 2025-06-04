package com.inventorycontrol;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

public class ZChartLookupTable {

    private final TreeMap<Double, Double> serviceToZ = new TreeMap<>();
    private final TreeMap<Double, Double> zToL = new TreeMap<>();

    public ZChartLookupTable(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 3) {
                    double z = Double.parseDouble(parts[0]);
                    double Fz = Double.parseDouble(parts[1]);
                    double Lz = Double.parseDouble(parts[2]);
                    serviceToZ.put(Fz, z);
                    zToL.put(z, Lz);
                }
            }
        }
    }

    public double getZForServiceLevel(double serviceLevel) {
        var lower = serviceToZ.floorEntry(serviceLevel);
        var upper = serviceToZ.ceilingEntry(serviceLevel);
        if (lower == null && upper == null) {
            throw new IllegalArgumentException("No entries in z-table for service level: " + serviceLevel);
        } else if (lower == null) {
            return upper.getValue();
        } else if (upper == null) {
            return lower.getValue();
        } else if (lower.getKey().equals(upper.getKey())) {
            return lower.getValue();
        } else {
            // Linear interpolation
            double x0 = lower.getKey();
            double y0 = lower.getValue();
            double x1 = upper.getKey();
            double y1 = upper.getValue();
            return y0 + (serviceLevel - x0) * (y1 - y0) / (x1 - x0);
        }
    }

    public double getLossFunctionValue(double z) {
        var lower = zToL.floorEntry(z);
        var upper = zToL.ceilingEntry(z);
        if (lower == null && upper == null) {
            throw new IllegalArgumentException("No entries in loss function table for z: " + z);
        } else if (lower == null) {
            return upper.getValue();
        } else if (upper == null) {
            return lower.getValue();
        } else if (lower.getKey().equals(upper.getKey())) {
            return lower.getValue();
        } else {
            // Linear interpolation
            double x0 = lower.getKey();
            double y0 = lower.getValue();
            double x1 = upper.getKey();
            double y1 = upper.getValue();
            return y0 + (z - x0) * (y1 - y0) / (x1 - x0);
        }
    }
}
