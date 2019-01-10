/*
 * Copyright (c) 2018 Shapelets.io
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package io.shapelets.khiva;

/**
 * Khiva Clustering class.
 */
public class Clustering extends Library {

    private native static long[] kShape(long ref, int k, float tolerance);


    /**
     * Calculates the centroids and indexes 
     *
     * @param arr       Array containing the input time series.
     * @param k         The number of clusters that will be computed.
     * @param tolerance The value of the error between consecutive set of centroids that will stop the computation
     *
     * @return The indexes and the centroids.
     */
    public static Array[] kShape(Array arr, int k, float tolerance) {
        long[] refs = kShape(arr.getReference(), k, tolerance);
        arr.setReference(refs[0]);
        return new Array[]{new Array(refs[1]), new Array([refs[2]])};
    }
  