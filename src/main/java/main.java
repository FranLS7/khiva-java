
/*
 * Copyright (c) 2018 Grumpy Cat Software S.L.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

import tsa.MatrixProfile;
import tsa.TSA;

/**
 * main class for checking the correct use of stomp and stomp_self_join.
 */
public class main {
    public static void main(String[] args) {
        double[] ta = {1, 2, 3, 5, 6, 7, 8, 10};
        double[] tb = {4, 5, 6, 24, 24, 24, 3, 3};
        long m = 3;
        TSA tsa = TSA.getInstance();
        MatrixProfile a = tsa.stomp(ta, tb, m);
        for (int i = 0; i < a.getIndex().length; i++) {
            System.out.println(a.getIndex()[i]);
        }
        MatrixProfile b = tsa.stompSelfJoin(tb, m);
        for (int i = 0; i < b.getIndex().length; i++) {
            System.out.println(b.getIndex()[i]);
        }
        for (int i = 0; i < b.getProfile().length; i++) {
            System.out.println(b.getProfile()[i]);
        }
    }
}
