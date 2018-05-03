/*
 * Copyright (c) 2018 Grumpy Cat Software S.L.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package tsa;

import org.junit.Assert;
import org.junit.Test;

public class DistancesTest {

    private static final double DELTA = 1e-6;

    @Test
    public void testEuclidean() throws Exception {
        float[] timeSeries = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        long[] dims = {4, 3, 1, 1};
        Array arrayOfTimeSeries = new Array(timeSeries, dims);
        float[] result = Distances.euclidean(arrayOfTimeSeries).getData();
        Assert.assertEquals(result[0], 0, DELTA);
        Assert.assertEquals(result[1], 0, DELTA);
        Assert.assertEquals(result[2], 0, DELTA);
        Assert.assertEquals(result[3], 8, DELTA);
        Assert.assertEquals(result[4], 0, DELTA);
        Assert.assertEquals(result[5], 0, DELTA);
        Assert.assertEquals(result[6], 16, DELTA);
        Assert.assertEquals(result[7], 8, DELTA);
        Assert.assertEquals(result[8], 0, DELTA);
    }

    @Test
    public void testSquaredEuclidean() throws Exception {
        float[] timeSeries = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        long[] dims = {4, 3, 1, 1};
        Array arrayOfTimeSeries = new Array(timeSeries, dims);
        float[] result = Distances.squaredEuclidean(arrayOfTimeSeries).getData();
        Assert.assertEquals(result[0], 0, DELTA);
        Assert.assertEquals(result[1], 0, DELTA);
        Assert.assertEquals(result[2], 0, DELTA);
        Assert.assertEquals(result[3], 64, DELTA);
        Assert.assertEquals(result[4], 0, DELTA);
        Assert.assertEquals(result[5], 0, DELTA);
        Assert.assertEquals(result[6], 256, DELTA);
        Assert.assertEquals(result[7], 64, DELTA);
        Assert.assertEquals(result[8], 0, DELTA);
    }

}