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

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

import static com.stormmq.string.Formatting.format;
import static java.lang.System.arraycopy;
import static java.nio.ByteBuffer.allocate;

@FunctionalInterface
public interface Utf8ByteUser<X extends Exception>
{
	static int maximumUtf16ToUtf8EncodingSize(@NotNull final CharSequence fullyQualifiedTypeName)
	{
		return fullyQualifiedTypeName.length() * 3;
	}

	@NotNull
	static byte[] encodeToUtf8ByteArrayWithCertaintyValueIsValid(@NonNls @NotNull final CharSequence value)
	{
		try
		{
			return encodeToUtf8ByteArray(value);
		}
		catch (final InvalidUtf16StringException e)
		{
			throw new IllegalArgumentException("value was not a valid UTF-16 string", e);
		}
	}

	@NotNull
	static byte[] encodeToUtf8ByteArray(@NonNls @NotNull final CharSequence value) throws InvalidUtf16StringException
	{
		final ByteBuffer byteBuffer = allocate(maximumUtf16ToUtf8EncodingSize(value));
		final Utf8ByteUser<RuntimeException> utf8ByteUser = utf8Byte ->
		{
			//noinspection NumericCastThatLosesPrecision
			byteBuffer.put((byte) utf8Byte);
		};
		utf8ByteUser.encodeUtf8Bytes(value);
		final byte[] underlying = byteBuffer.array();
		final int length = byteBuffer.position();
		final byte[] slice = new byte[length];
		arraycopy(underlying, 0, slice, 0, length);
		return slice;
	}

	static int utf8Length(@NotNull @NonNls final CharSequence value) throws InvalidUtf16StringException
	{
		class Counter implements Utf8ByteUser<RuntimeException>
		{
			private int count = 0;

			@Override
			public void useUnsignedByte(final int utf8Byte)
			{
				count++;
			}
		}
		@SuppressWarnings("ClassReferencesSubclass") final Counter counter = new Counter();
		counter.encodeUtf8Bytes(value);
		return counter.count;
	}

	void useUnsignedByte(final int utf8Byte) throws X;

	default void encodeUtf8Bytes(@NotNull final CharSequence value) throws InvalidUtf16StringException, X
	{
		codePointToUtf8Bytes().iterateOverStringCodePoints(value);
	}

	@SuppressWarnings("MagicNumber")
	@NotNull
	default CodePointUser<X> codePointToUtf8Bytes()
	{
		return (index, codePoint) ->
		{
			if (codePoint < 0x80)
			{
				useUnsignedByte(codePoint);
				return;
			}

			if (codePoint < 0x07FF)
			{
				useUnsignedByte(192 + (codePoint >>> 6));
				useUnsignedByte(128 + (codePoint % 64));
				return;
			}

			if (codePoint < 0xFFFF)
			{
				useUnsignedByte(224 + (codePoint >>> 12));
				useUnsignedByte(128 + ((codePoint >> 6) & 0x3F));
				useUnsignedByte(128 + (codePoint & 0x3F));
				return;
			}

			if (codePoint < 0x1FFFFF)
			{
				useUnsignedByte(240 + (codePoint >>> 18));
				useUnsignedByte(128 + ((codePoint >>> 12) & 0x3F));
				useUnsignedByte(128 + ((codePoint >>> 6) & 0x3F));
				useUnsignedByte(128 + (codePoint & 0x3F));
				return;
			}

			throw new IllegalArgumentException(format("Invalid Unicode Code Point '0x%1$08X' greater than 0x1FFFFF at index '%2$s' which should be impossible to exist in this context", codePoint, index));
		};
	}
}
