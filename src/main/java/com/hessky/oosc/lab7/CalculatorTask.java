package com.hessky.oosc.lab7;

public interface CalculatorTask {
    String evaluate(String expression);

    Coordinates findFirstPriorityBraces(String expression, int braceSeekStartPosition);

    String parse(String expression);

    String collapseSigns(String expression);
}
