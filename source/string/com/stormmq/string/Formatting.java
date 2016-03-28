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

	private Formatting()
	{
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
}
