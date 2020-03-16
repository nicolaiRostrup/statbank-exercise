package gov.ufst.statbank_exercise.ui.helpers;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;

import java.util.ArrayList;
import java.util.List;

import gov.ufst.statbank_exercise.ui.main.MutableDataEntry;

public class MultipleLineChart {


    public Cartesian CreateMultipleLineChart (List<MutableDataEntry> preparedList) {

        Cartesian cartesian = AnyChart.line();

        List<DataEntry> data = new ArrayList<>();
        for (MutableDataEntry entry: preparedList) {
            data.add(new CustomDataEntry(entry.getYear(), entry.getSingleCount(),
                                                    entry.getTwinCount(), entry.getTripletCount(),
                                                    entry.getQuadCount()));

        }

        Set set = Set.instantiate();
        set.data(data);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Brandy");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
               .type(MarkerType.CIRCLE)
               .size(4d);
        series1.tooltip()
               .position("right")
               .anchor(Anchor.LEFT_CENTER)
               .offsetX(5d)
               .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("Whiskey");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
               .type(MarkerType.CIRCLE)
               .size(4d);
        series2.tooltip()
               .position("right")
               .anchor(Anchor.LEFT_CENTER)
               .offsetX(5d)
               .offsetY(5d);

        Line series3 = cartesian.line(series3Mapping);
        series3.name("Tequila");
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
               .type(MarkerType.CIRCLE)
               .size(4d);
        series3.tooltip()
               .position("right")
               .anchor(Anchor.LEFT_CENTER)
               .offsetX(5d)
               .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        return cartesian;
    }

    private static class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value, Number value2, Number value3, Number value4) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
            setValue("value4", value4);
        }
    }
}
