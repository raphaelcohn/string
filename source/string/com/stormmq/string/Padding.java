// The MIT License (MIT)
//
// Copyright © 2016, Raphael Cohn <raphael.cohn@stormmq.com>
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package com.stormmq.string;

import org.jetbrains.annotations.*;

public final class Padding
{
	private static final char ZeroDigit = '0';

	private Padding()
	{
	}

	@NonNls
	@NotNull
	public static String padAsDecimal(final int ordinal, final int padding)
	{
		final String unpadded = Integer.toString(ordinal);
		return pad(unpadded, padding);
	}

	@NonNls
	@NotNull
	public static String padAsDecimal(final long ordinal, final int padding)
	{
		final String unpadded = Long.toString(ordinal);
		return pad(unpadded, padding);
	}

	@NonNls
	@NotNull
	public static String padAsHexadecimal(final int ordinal, final int padding)
	{
		final String unpadded = Integer.toHexString(ordinal);
		return pad(unpadded, padding);
	}

	@NonNls
	@NotNull
	public static String padAsHexadecimal(final long ordinal, final int padding)
	{
		final String unpadded = Long.toHexString(ordinal);
		return pad(unpadded, padding);
	}

	@NotNull
	public static String pad(@NotNull @NonNls final String unpadded, final int padding)
	{
		if (unpadded.length() == padding)
		{
			return unpadded;
		}
		int i = padding - unpadded.length();
		final char[] padded = new char[padding];
		unpadded.getChars(0, unpadded.length(), padded, i);
		while (i > 0)
		{
			i--;
			padded[i] = ZeroDigit;
		}
		return new String(padded);
	}
}
