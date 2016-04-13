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

import org.jetbrains.annotations.NotNull;

import static com.stormmq.string.Formatting.format;
import static java.lang.Character.isHighSurrogate;
import static java.lang.Character.isLowSurrogate;
import static java.lang.Character.toCodePoint;

@FunctionalInterface
public interface CodePointUser<X extends Exception>
{
	int HighSurrogateIncrement = 2;

	int NonSurrogateIncrement = 1;

	void useCodePoint(final int index, final int codePoint) throws X;

	default void iterateOverStringCodePoints(@NotNull final CharSequence value) throws InvalidUtf16StringException, X
	{
		int index = 0;
		final int length = value.length();
		while (index < length)
		{
			final char firstCharacter = value.charAt(index);

			final int codePoint;
			final int indexIncrement;
			if (isHighSurrogate(firstCharacter))
			{
				final char low;
				try
				{
					low = value.charAt(index + 1);
				}
				catch (final StringIndexOutOfBoundsException e)
				{
					throw new InvalidUtf16StringException(format("String value contains a missing final low surrogate after index '%1$s'", index), e);
				}
				codePoint = toCodePoint(firstCharacter, low);
				indexIncrement = HighSurrogateIncrement;
			}
			else if (isLowSurrogate(firstCharacter))
			{
				throw new InvalidUtf16StringException(format("String value contains a low surrogate without a preceding high surrogate at index '%1$s'", index));
			}
			else
			{
				codePoint = firstCharacter;
				indexIncrement = NonSurrogateIncrement;
			}

			useCodePoint(index, codePoint);

			index += indexIncrement;
		}
	}
}
