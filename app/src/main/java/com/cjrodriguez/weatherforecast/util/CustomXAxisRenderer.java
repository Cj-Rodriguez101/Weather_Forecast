package com.cjrodriguez.weatherforecast.util;

import android.graphics.Canvas;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.List;

public class CustomXAxisRenderer extends XAxisRenderer {
    public CustomXAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans) {
        super(viewPortHandler, xAxis, trans);
    }

    @Override
    protected void drawLabel(Canvas c, String formattedLabel, float x, float y, MPPointF anchor, float angleDegrees) {

//        val line: List<String> = formattedLabel.split("\n")
//        Utils.drawXAxisValue(c, line[0], y, x, mAxisLabelPaint, anchor, angleDegrees)
//        for (i in 1 until line.size) { // we've already processed 1st line
//            Utils.drawXAxisValue(c, line[i], y, x + mAxisLabelPaint.textSize * i,
//                    mAxisLabelPaint, anchor, angleDegrees)
//        }

//        for (i in 1 until line.size) {
//            Utils.drawXAxisValue(c, line[i], x, y + mAxisLabelPaint.textSize * i,
//                    mAxisLabelPaint, anchor, angleDegrees)
//        }

//        String [] line = formattedLabel.split("\n");
//
//        for(int i = 0; i<line.length; i++){
//            Utils.drawXAxisValue(c, line[i], x, y + mAxisLabelPaint.getTextSize() * i, mAxisLabelPaint, anchor, angleDegrees);
//        }
        super.drawLabel(c, formattedLabel, x, y, anchor, angleDegrees);
    }




}
