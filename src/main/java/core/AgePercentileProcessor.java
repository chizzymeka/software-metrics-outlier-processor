package core;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import ucl.cdt.cybersecurity.App;

import java.util.ArrayList;

public class AgePercentileProcessor {

    void setPercentileData(ArrayList<Double> ageValuesList) {
        // Set the age values that percentile object will use to do calculations.
        Double[] ageValuesArray = ageValuesList.toArray(new Double[ageValuesList.size()]);
        double[] ageValuesArrayPrimitive = ArrayUtils.toPrimitive(ageValuesArray);
        Percentile percentile = App.getPercentile();
        percentile.setData(ageValuesArrayPrimitive);
    }

    public double getPthPercentileValue(double p) {
        Percentile percentile = App.getPercentile();
        return percentile.evaluate(p);
    }
}
