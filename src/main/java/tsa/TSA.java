
/*
 * Copyright (c) 2018 Grumpy Cat Software S.L.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package tsa;

import org.apache.commons.lang3.ArrayUtils;


/**
 * TSA class for exposing the C++ TSA Library
 */
public class TSA {

    private static TSA instance = null;


    private TSA() {
        System.loadLibrary("TSALIB");
    }

    /**
     * getInstance function for using this class as a Singleton.
     *
     * @return TSA instance.
     */
    public static TSA getInstance() {
        if (instance == null) {
            instance = new TSA();
        }
        return instance;
    }

    /**
     * Stomp TSA's native function.
     *
     * @param ta  Array of doubles with the first time series.
     * @param tb  Array of doubles with the second time series.
     * @param lta Integer with the ta length.
     * @param ltb Integer with the tb length.
     * @param m   Long with the subsequence length.
     * @param p   Array of doubles for storing the distances.
     * @param i   Array of integers for storing the indexes.
     */
    private native void stomp(double[] ta, double[] tb, int lta, int ltb, long m, double[] p, int[] i);

    /**
     * StompSelfJoin TSA's native function.
     *
     * @param ta  Array of doubles with the time series.
     * @param lta Integer with the ta length.
     * @param m   Long with the subsequence length.
     * @param p   Array of doubles for storing the distances.
     * @param i   Array of integers for storing the indexes.
     */
    private native void stompSelfJoin(double[] ta, int lta, long m, double[] p, int[] i);

    /**
     * findBestMotifs TSA's native function.
     *
     * @param profile            The matrix profile containing the minimum distance of each sequence.
     * @param index              The matrix profile index containing where each minimum occurs
     * @param lengthProfile      Length of the matrix profile
     * @param n                  Number of motifs to extract
     * @param motifDistances     The distance of the best N motifs
     * @param motifIndices       The indices of the best N motifs
     * @param subsequenceIndices The indices of the query sequences that produced the minimum reported in the motifs.
     */
    private native void findBestNMotifs(double[] profile, int[] index, long lengthProfile, long n,
                                        double[] motifDistances, int[] motifIndices, int[] subsequenceIndices);

    /**
     * findBestNDiscords TSA's native function.
     *
     * @param profile            The matrix profile containing the minimum distance of each subsequence
     * @param index              The matrix profile index containing where each maximum occurs
     * @param lengthProfile      Length of the matrix profile
     * @param n                  Number of discords to extract
     * @param motifDistances     The distance of the best N discords
     * @param motifIndices       The indices of the best N discords
     * @param subsequenceIndices The indices of the query sequences that produced the "N" bigger discord.
     */
    private native void findBestNDiscords(double[] profile, int[] index, long lengthProfile, long n,
                                          double[] motifDistances, int[] motifIndices, int[] subsequenceIndices);

    /**
     * absoluteSumOfChanges TSA's native function.
     *
     * @param timeSeries         Time series concatenated in a single row.
     * @param timeSeriesLength   Length of each time series.
     * @param numberOfTimeSeries Number of time series into timeSeries.
     * @param jResult            Absolute sum of changes.
     */
    private native void absoluteSumOfChanges(double[] timeSeries,
                                             long timeSeriesLength, long numberOfTimeSeries, double[] jResult);

    /**
     * absEnergy TSA's native function.
     *
     * @param timeSeries         Time series concatenated in a single row.
     * @param timeSeriesLength   Length of each time series.
     * @param numberOfTimeSeries Number of time series into timeSeries.
     * @param jResult            Absolute Energy
     */
    private native void absEnergy(double[] timeSeries,
                                  long timeSeriesLength, long numberOfTimeSeries, double[] jResult);

    /**
     * cidCe TSA's native function
     *
     * @param tssConcatenated Time series concatenated in a single row.
     * @param tssLength       Length of each time series ( All the time series need to have the same length).
     * @param tssNumberOfTS   Number of time series in tssConcatenated.
     * @param zNormalize      Controls whether the time series should be z-normalized or not.
     * @param result          The complexity value for the given time series.
     */
    private native void cidCe(double[] tssConcatenated, long tssLength, long tssNumberOfTS, boolean zNormalize,
                              double[] result);

    /**
     * c3 TSA's native function.
     *
     * @param tssConcatenated Time series concatenated in a single row.
     * @param tssLength       Length of each time series (All the time series need to have the same length).
     * @param tssNumberOfTS   Number of time series in tssConcatenated.
     * @param lag             The lag.
     * @param result          The non-linearity value for the given time series.
     */
    private native void c3(double[] tssConcatenated, long tssLength, long tssNumberOfTS, long lag, double[] result);


    /**
     * Stomp algorithm.
     *
     * @param ta Array of doubles with the first time series.
     * @param tb Array of doubles with the second time series.
     * @param m  Long with the subsequence length.
     * @return MatrixProfile object.
     */
    public MatrixProfile stomp(double[] ta, double[] tb, long m) {
        int n = tb.length;
        double[] p = new double[n - (int) (m) + 1];
        int[] i = new int[n - (int) (m) + 1];

        stomp(ta, tb, ta.length, tb.length, m, p, i);

        return new MatrixProfile(p, i);
    }

    /**
     * Stomp Self Join algorithm.
     *
     * @param ta Array of doubles with the first time series.
     * @param m  Long with the subsequence length.
     * @return MatrixProfile object.
     */
    public MatrixProfile stompSelfJoin(double[] ta, long m) {
        int n = ta.length;
        double[] p = new double[n - (int) (m) + 1];
        int[] i = new int[n - (int) (m) + 1];


        stompSelfJoin(ta, ta.length, m, p, i);

        return new MatrixProfile(p, i);
    }

    /**
     * findBestNMotifs function.
     *
     * @param profile The matrix profile containing the minimum distance of each subsequence.
     * @param index   The matrix profile index
     * @param n       Number of motifs to extract.
     * @return Sequence object.
     */
    public Sequence findBestNMotifs(double[] profile, int[] index, long n) {
        int nMotifs = (int) (n);

        double[] motifDistances = new double[nMotifs];
        int[] motifIndices = new int[nMotifs];
        int[] subsequenceIndices = new int[nMotifs];

        findBestNMotifs(profile, index, profile.length, n, motifDistances, motifIndices, subsequenceIndices);

        return new Sequence(motifDistances, motifIndices, subsequenceIndices);

    }

    /**
     * findBestNDiscords function.
     *
     * @param profile The matrix profile containing the minimum distance of each subsequence.
     * @param index   The matrix profile index
     * @param n       Number of discords to extract.
     * @return Sequence Object.
     */
    public Sequence findBestNDiscords(double[] profile, int[] index, long n) {
        int nMotifs = (int) (n);

        double[] motifDistances = new double[nMotifs];
        int[] motifIndices = new int[nMotifs];
        int[] subsequenceIndices = new int[nMotifs];

        findBestNDiscords(profile, index, profile.length, n, motifDistances, motifIndices, subsequenceIndices);

        return new Sequence(motifDistances, motifIndices, subsequenceIndices);

    }

    /**
     * cidCe function.
     *
     * @param tss        Array of arrays with the time series.
     * @param zNormalize Controls whether the time series should be z-normalized or not.
     * @return The complexity value for the given time series.
     */
    public double[] cidCe(double[][] tss, Boolean zNormalize) {
        long tssLength = tss[0].length;
        long tssNumberOfTS = tss.length;
        double[] tssConcatenated = new double[(int) (tssLength * tssNumberOfTS)];
        for (double[] time_series : tss) {
            tssConcatenated = ArrayUtils.addAll(tssConcatenated, time_series);
        }

        double[] result = new double[(int) (tssNumberOfTS)];
        cidCe(tssConcatenated, tssLength, tssNumberOfTS, zNormalize, result);

        return result;
    }

    /**
     * absoluteSumOfChanges function.
     *
     * @param timeSeriesMatrix Array of double arrays representing each time series.
     * @return Double array with the absolute sum of changes.
     */
    public double[] absoluteSumOfChanges(double[][] timeSeriesMatrix) {
        long timeSeriesLength = timeSeriesMatrix[0].length;
        long numberOfTimeSeries = timeSeriesMatrix.length;

        double[] concatenatedTimeSeries = new double[(int) (timeSeriesLength * numberOfTimeSeries)];
        double[] result = new double[(int) (numberOfTimeSeries)];

        for (double[] time_series : timeSeriesMatrix) {
            concatenatedTimeSeries = ArrayUtils.addAll(concatenatedTimeSeries, time_series);
        }

        absoluteSumOfChanges(concatenatedTimeSeries, timeSeriesLength, numberOfTimeSeries,
                result);

        return result;
    }


    /**
     * absEnergy function.
     *
     * @param timeSeriesMatrix Array of double arrays representing each Time Series.
     * @return Double array with the Absolute Energy.
     */
    public double[] absEnergy(double[][] timeSeriesMatrix) {
        long timeSeriesLength = timeSeriesMatrix[0].length;
        long numberOfTimeSeries = timeSeriesMatrix.length;

        double[] concatenatedTimeSeries = new double[(int) (timeSeriesLength * numberOfTimeSeries)];
        double[] result = new double[(int) (numberOfTimeSeries)];

        for (double[] time_series : timeSeriesMatrix) {
            concatenatedTimeSeries = ArrayUtils.addAll(concatenatedTimeSeries, time_series);
        }

        absEnergy(concatenatedTimeSeries, timeSeriesLength, numberOfTimeSeries,
                result);

        return result;
    }

    /**
     * c3 function.
     *
     * @param tss Array of double arrays representing each time series.
     * @param lag The lag
     * @return The non-linearity value for the given time series.
     */
    public double[] c3(double[][] tss, long lag) {
        long tssLength = tss[0].length;
        long tssNumberOfTS = tss.length;
        double[] tssConcatenated = new double[(int) (tssLength * tssNumberOfTS)];
        for (double[] time_series : tss) {
            tssConcatenated = ArrayUtils.addAll(tssConcatenated, time_series);
        }

        double[] result = new double[(int) (tssNumberOfTS)];
        c3(tssConcatenated, tssLength, tssNumberOfTS, lag, result);

        return result;
    }

}