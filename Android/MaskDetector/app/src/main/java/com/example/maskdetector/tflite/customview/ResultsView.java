package com.example.maskdetector.tflite.customview;

import com.example.maskdetector.tflite.tflite.Classifier;

import java.util.List;

public interface ResultsView {
    public void setResults(final List<Classifier.Recognition> results);
}

