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

public class Linalg extends Library {

    private native static long[] lls(long refA, long refB);

    /**
     * Calculates the minimum norm least squares solution \(x\) \((\left\lVert{A·x − b}\right\rVert^2)\) to
     * \(A·x = b\). This function uses the singular value decomposition function of Arrayfire. The actual formula that
     * this function computes is \(x = V·D\dagger·U^T·b\). Where \(U\) and \(V\) are orthogonal matrices and
     * \(D\dagger\) contains the inverse values of the singular values contained in D if they are not zero, and zero
     * otherwise.
     *
     * @param arrA Coefficients of the linear equation problem to solve.
     * @param arrB Array with the measured values.
     * @return Contains the solution to the linear equation problem minimizing the norm 2.
     */
    public static Array lls(Array arrA, Array arrB) {
        long[] refs = lls(arrA.getReference(), arrB.getReference());
        arrA.setReference(refs[0]);
        arrB.setReference(refs[1]);
        return new Array(refs[2]);
    }
}
