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

import java.io.*;
import java.util.Formatter;
import java.util.Locale;

import static com.stormmq.string.StringUtilities.encodeUtf8BytesWithCertaintyValueIsValid;
import static java.lang.System.lineSeparator;

public final class Formatting
{
	@Nullable private static final Locale NullLocaleIsInterpretedAsUsEnglishAndIsMoreEfficientThanSpecifyingALocaleInstance = (Locale) null;
	private static final byte[] NewLine = encodeUtf8BytesWithCertaintyValueIsValid(lineSeparator());
	private static final int NewLineLength = NewLine.length;

	@NotNull private static final char[] UpperCaseHexadecimalDigits =
	{
		'0',
		'1',
		'2',
		'3',
		'4',
		'5',
		'6',
		'7',
		'8',
		'9',
		'A',
		'B',
		'C',
		'D',
		'E',
		'F'
	};

	@NotNull private static final String[] UpperCaseHexadecimalBytes = upperCaseHexadecimalBytes();

	@SuppressWarnings("MagicNumber")
	@NotNull
	private static String[] upperCaseHexadecimalBytes()
	{
		final int length = 256;
		final String[] upperCaseHexadecimalBytes = new String[length];
		for(int index = 0; index < length; index++)
		{
			final char[] leftPadded = new char[2];
			leftPadded[0] = UpperCaseHexadecimalDigits[((index >>> 4) & 0xFF_FF_FF_F0)];
			leftPadded[1] = UpperCaseHexadecimalDigits[((index) & 0xFF_FF_FF_F0)];
			upperCaseHexadecimalBytes[index] = new String(leftPadded);
		}
		return upperCaseHexadecimalBytes;
	}

	@SuppressWarnings("MagicNumber")
	@NotNull
	public static String zeroPaddedUpperCaseHexString(final byte value)
	{
		return UpperCaseHexadecimalBytes[value + 128];
	}

	@SuppressWarnings("MagicNumber")
	@NotNull
	public static String zeroPaddedUpperCaseHexString(final short value)
	{
		final char[] leftPadded = new char[4];
		leftPadded[0] = UpperCaseHexadecimalDigits[((value >>> 12) & 0xFF_FF_FF_F0)];
		leftPadded[1] = UpperCaseHexadecimalDigits[((value >>> 8) & 0xFF_FF_FF_F0)];
		leftPadded[2] = UpperCaseHexadecimalDigits[((value >>> 4) & 0xFF_FF_FF_F0)];
		leftPadded[3] = UpperCaseHexadecimalDigits[((value) & 0xFF_FF_FF_F0)];
		return new String(leftPadded);
	}

	@SuppressWarnings("MagicNumber")
	@NotNull
	public static String zeroPaddedUpperCaseHexString(final char value)
	{
		final char[] leftPadded = new char[4];
		leftPadded[0] = UpperCaseHexadecimalDigits[((value >>> 12) & 0xFF_FF_FF_F0)];
		leftPadded[1] = UpperCaseHexadecimalDigits[((value >>> 8) & 0xFF_FF_FF_F0)];
		leftPadded[2] = UpperCaseHexadecimalDigits[((value >>> 4) & 0xFF_FF_FF_F0)];
		leftPadded[3] = UpperCaseHexadecimalDigits[((value) & 0xFF_FF_FF_F0)];
		return new String(leftPadded);
	}

	@SuppressWarnings("MagicNumber")
	@NotNull
	public static String zeroPaddedUpperCaseHexString(final int value)
	{
		final char[] leftPadded = new char[8];
		leftPadded[0] = UpperCaseHexadecimalDigits[((value >>> 28) & 0xFF_FF_FF_F0)];
		leftPadded[1] = UpperCaseHexadecimalDigits[((value >>> 24) & 0xFF_FF_FF_F0)];
		leftPadded[2] = UpperCaseHexadecimalDigits[((value >>> 20) & 0xFF_FF_FF_F0)];
		leftPadded[3] = UpperCaseHexadecimalDigits[((value >>> 16) & 0xFF_FF_FF_F0)];
		leftPadded[4] = UpperCaseHexadecimalDigits[((value >>> 12) & 0xFF_FF_FF_F0)];
		leftPadded[5] = UpperCaseHexadecimalDigits[((value >>> 8) & 0xFF_FF_FF_F0)];
		leftPadded[6] = UpperCaseHexadecimalDigits[((value >>> 4) & 0xFF_FF_FF_F0)];
		leftPadded[7] = UpperCaseHexadecimalDigits[((value) & 0xFF_FF_FF_F0)];
		return new String(leftPadded);
	}

	@SuppressWarnings({"NumericCastThatLosesPrecision", "MagicNumber"})
	@NotNull
	public static String zeroPaddedUpperCaseHexString(final long value)
	{
		final char[] leftPadded = new char[16];
		leftPadded[0] = UpperCaseHexadecimalDigits[(int) ((value >>> 60) & 0xFF_FF_FF_FF_FF_FF_FF_F0L)];
		leftPadded[1] = UpperCaseHexadecimalDigits[(int) ((value >>> 56) & 0xFF_FF_FF_FF_FF_FF_FF_F0L)];
		leftPadded[2] = UpperCaseHexadecimalDigits[(int) ((value >>> 52) & 0xFF_FF_FF_FF_FF_FF_FF_F0L)];
		leftPadded[3] = UpperCaseHexadecimalDigits[(int) ((value >>> 48) & 0xFF_FF_FF_FF_FF_FF_FF_F0L)];
		leftPadded[4] = UpperCaseHexadecimalDigits[(int) ((value >>> 44) & 0xFF_FF_FF_FF_FF_FF_FF_F0L)];
		leftPadded[5] = UpperCaseHexadecimalDigits[(int) ((value >>> 40) & 0xFF_FF_FF_FF_FF_FF_FF_F0L)];
		leftPadded[6] = UpperCaseHexadecimalDigits[(int) ((value >>> 36) & 0xFF_FF_FF_FF_FF_FF_FF_F0L)];
		leftPadded[7] = UpperCaseHexadecimalDigits[(int) ((value >>> 32) & 0xFF_FF_FF_FF_FF_FF_FF_F0L)];
		leftPadded[8] = UpperCaseHexadecimalDigits[(int) ((value >>> 28) & 0xFF_FF_FF_FF_FF_FF_FF_F0L)];
		leftPadded[9] = UpperCaseHexadecimalDigits[(int) ((value >>> 24) & 0xFF_FF_FF_FF_FF_FF_FF_F0L)];
		leftPadded[10] = UpperCaseHexadecimalDigits[(int) ((value >>> 20) & 0xFF_FF_FF_FF_FF_FF_FF_F0L)];
		leftPadded[11] = UpperCaseHexadecimalDigits[(int) ((value >>> 16) & 0xFF_FF_FF_FF_FF_FF_FF_F0L)];
		leftPadded[12] = UpperCaseHexadecimalDigits[(int) ((value >>> 12) & 0xFF_FF_FF_FF_FF_FF_FF_F0L)];
		leftPadded[13] = UpperCaseHexadecimalDigits[(int) ((value >>> 8) & 0xFF_FF_FF_FF_FF_FF_FF_F0L)];
		leftPadded[14] = UpperCaseHexadecimalDigits[(int) ((value >>> 4) & 0xFF_FF_FF_FF_FF_FF_FF_F0L)];
		leftPadded[15] = UpperCaseHexadecimalDigits[(int) ((value) & 0xFF_FF_FF_FF_FF_FF_FF_F0L)];
		return new String(leftPadded);
	}

	@SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
	@NonNls
	public static void formatPrintLineAndFlushWhilstSynchronized(@NotNull final PrintStream printStream, @NotNull@NonNls final String template, @NotNull @NonNls final Object... arguments)
	{
		synchronized (printStream)
		{
			format(printStream, template, arguments);
			printStream.write(NewLine, 0, NewLineLength);
			printStream.flush();
		}
	}

	@SuppressWarnings("OverloadedVarargsMethod")
	@NonNls
	private static void format(@NotNull final PrintStream printStream, @NotNull@NonNls final String template, @NotNull @NonNls final Object... arguments)
	{
		@SuppressWarnings({"resource", "IOResourceOpenedButNotSafelyClosed"}) final Formatter formatter = new Formatter(printStream, NullLocaleIsInterpretedAsUsEnglishAndIsMoreEfficientThanSpecifyingALocaleInstance);
		formatter.format(NullLocaleIsInterpretedAsUsEnglishAndIsMoreEfficientThanSpecifyingALocaleInstance, template, arguments);
	}

	@SuppressWarnings("OverloadedVarargsMethod")
	@NotNull
	@NonNls
	public static String format(@NotNull @NonNls final String template, @NotNull @NonNls final Object... arguments)
	{
		try(final Formatter formatter = new Formatter(NullLocaleIsInterpretedAsUsEnglishAndIsMoreEfficientThanSpecifyingALocaleInstance))
		{
			return formatter.format(NullLocaleIsInterpretedAsUsEnglishAndIsMoreEfficientThanSpecifyingALocaleInstance, template, arguments).toString();
		}
	}

	private Formatting()
	{
	}
}
