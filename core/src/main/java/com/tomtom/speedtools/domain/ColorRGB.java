/*
 * Copyright (C) 2015. TomTom International BV (http://tomtom.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tomtom.speedtools.domain;

import com.tomtom.speedtools.objects.Objects;
import com.tomtom.speedtools.utils.MathUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;


/**
 * Color. (Immutable)
 */
@Immutable
public final class ColorRGB {
    private final int r;
    private final int g;
    private final int b;

    /**
     * Create a color.
     *
     * @param r Red, 0..255.
     * @param g Green, 0..255.
     * @param b Blue, 0..255.
     */
    public ColorRGB(
            final int r,
            final int g,
            final int b) {
        super();
        assert MathUtils.isBetween(r, 0, 255) : r;
        assert MathUtils.isBetween(g, 0, 255) : g;
        assert MathUtils.isBetween(b, 0, 255) : b;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public ColorRGB(@Nonnull final Integer intRGB) {
        this((intRGB & 0x00FF0000) >> 16, (intRGB & 0x0000FF00) >> 8, intRGB & 0x000000FF);
    }

    /**
     * Default constructor, used for deserialization only.
     */
    @SuppressWarnings("UnusedDeclaration")
    @Deprecated
    private ColorRGB() {
        super();
        r = 0;
        g = 0;
        b = 0;
    }

    /**
     * Red.
     *
     * @return Red, 0..255.
     */
    public int getR() {
        return r;
    }

    /**
     * Green.
     *
     * @return Green, 0..255.
     */
    public int getG() {
        return g;
    }

    /**
     * Blue.
     *
     * @return Blue, 0..255.
     */
    public int getB() {
        return b;
    }

    /**
     * Setter for {@link #getR()}.
     *
     * @param r Red, 0..255.
     * @return New immutable object.
     */
    @Nonnull
    public ColorRGB withR(final int r) {
        return new ColorRGB(r, g, b);
    }

    /**
     * Setter for {@link #getG()}.
     *
     * @param g Green, 0..255.
     * @return New immutable object.
     */
    @Nonnull
    public ColorRGB withG(final int g) {
        return new ColorRGB(r, g, b);
    }

    /**
     * Setter for {@link #getB()}.
     *
     * @param b Blue, 0..255.
     * @return New immutable object.
     */
    @Nonnull
    public ColorRGB withB(final int b) {
        return new ColorRGB(r, g, b);
    }

    @Nonnull
    public Integer toIntegerRGB() {
        final Integer colorRGB = (r << 16) + (g << 8) + b;
        return colorRGB;
    }

    public boolean canEqual(@Nonnull final Object obj) {
        assert obj != null;
        return obj instanceof ColorRGB;
    }

    @Override
    public boolean equals(@Nullable final Object obj) {
        boolean eq;
        if (this == obj) {
            eq = true;
        } else if ((obj != null) && (obj instanceof ColorRGB)) {
            final ColorRGB that = (ColorRGB) obj;
            eq = that.canEqual(this);
            // Top-level entity, don't check for super.equals(that).
            eq = eq && (r == that.r);
            eq = eq && (g == that.g);
            eq = eq && (b == that.b);
        } else {
            eq = false;
        }
        return eq;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(new Object[]{r, g, b});
    }

    @Override
    public String toString() {
        return "[" + r + ", " + g + ", " + b + ']';
    }
}
