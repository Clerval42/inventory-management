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
        var entry = serviceToZ.floorEntry(serviceLevel);
        if (entry == null) {
            // If below minimum, return the lowest z in the table
            return serviceToZ.firstEntry().getValue();
        }
        return entry.getValue();
    }

    public double getLossFunctionValue(double z) {
        var entry = zToL.floorEntry(z);
        if (entry == null) {
            // If below minimum, return the lowest L(z) in the table
            return zToL.firstEntry().getValue();
        }
        return entry.getValue();
    }
}
