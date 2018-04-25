/*
 * Copyright (c) 2018 Grumpy Cat Software S.L.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package tsa;

public class FloatComplex {
    private float real;
    private float imag;

    public FloatComplex(float re, float im) {
        set(re, im);
    }

    public FloatComplex() {
        set(0, 0);
    }

    /**
     * Sets the real and imaginary values.
     *
     * @param re Real value.
     * @param im Imaginary value.
     */
    public void set(float re, float im) {
        real = re;
        imag = im;
    }

    /**
     * Sets the real value.
     *
     * @param re Real value.
     */
    public void setReal(float re) {
        real = re;
    }

    /**
     * Sets the imaginary value.
     *
     * @param im Imaginary value.
     */
    public void setImag(float im) {
        imag = im;
    }

    /**
     * Gets the real value.
     *
     * @return The real value.
     */
    public float getReal() {
        return real;
    }

    /**
     * Gets the imaginary value.
     *
     * @return The imaginary value.
     */
    public float getImag() {
        return imag;
    }

    /**
     * toString function.
     *
     * @return String with the Object representation.
     */
    public String toString() {
        String str = String.valueOf(real);

        if (imag < 0) str = str + " - ";
        else str = str + " + ";

        return str + String.valueOf(Math.abs(imag)) + "i";
    }
}