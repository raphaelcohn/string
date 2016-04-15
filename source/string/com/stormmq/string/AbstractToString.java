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

import java.util.*;
import java.util.function.Function;

import static com.stormmq.string.StringConstants.*;
import static java.util.Arrays.deepToString;

public abstract class AbstractToString
{
	@NotNull private static final Map<Class<?>, Function<Object, String>> PrimitiveArrayFormatters = primitiveArrayFormatters();

	@NotNull
	private static Map<Class<?>, Function<Object, String>> primitiveArrayFormatters()
	{
		final Map<Class<?>, Function<Object, String>> primitiveArrayFormatters = new HashMap<>(8);
		primitiveArrayFormatters.put(boolean.class, argument -> Arrays.toString((boolean[]) argument));
		primitiveArrayFormatters.put(byte.class, argument -> Arrays.toString((byte[]) argument));
		primitiveArrayFormatters.put(char.class, argument -> Arrays.toString((char[]) argument));
		primitiveArrayFormatters.put(short.class, argument -> Arrays.toString((short[]) argument));
		primitiveArrayFormatters.put(int.class, argument -> Arrays.toString((int[]) argument));
		primitiveArrayFormatters.put(long.class, argument -> Arrays.toString((long[]) argument));
		primitiveArrayFormatters.put(float.class, argument -> Arrays.toString((float[]) argument));
		primitiveArrayFormatters.put(double.class, argument -> Arrays.toString((double[]) argument));
		return primitiveArrayFormatters;
	}

	@SuppressWarnings("WeakerAccess")
	@NotNull
	public static String toStringAsTuple(@NotNull final Object instance, @NotNull final Object... arguments)
	{
		final StringBuilder stringBuilder = new StringBuilder(1024);
		stringBuilder.append(instance.getClass().getSimpleName());
		stringBuilder.append('(');
		final int length = arguments.length;
		for (int index = 0; index < length; index++)
		{
			if (index != 0)
			{
				stringBuilder.append(", ");
			}

			@Nullable final Object argument = arguments[index];
			final String value;
			if (argument == null)
			{
				value = _null;
			}
			else
			{
				final Class<?> argumentClass = argument.getClass();
				if (argumentClass.isArray())
				{
					final Class<?> componentType = argumentClass.getComponentType();
					value = PrimitiveArrayFormatters.getOrDefault(componentType, argumentX -> deepToString((Object[]) argumentX)).apply(argument);
				}
				else
				{
					value = argument.toString();
				}
			}
			stringBuilder.append(value);
		}
		stringBuilder.append(')');
		return stringBuilder.toString();
	}

	protected AbstractToString()
	{
	}

	@Override
	protected final void finalize()
	{
	}

	@Override
	protected final Object clone() throws CloneNotSupportedException
	{
		throw new CloneNotSupportedException("clone() is a legacy idea that should not be relied on");
	}

	@Override
	@NotNull
	@NonNls
	public final String toString()
	{
		return toStringAsTuple(this, fields());
	}

	@NotNull
	protected abstract Object[] fields();

	@SuppressWarnings("OverloadedVarargsMethod")
	@NotNull
	protected static Object[] fields(@NotNull final Object... fields)
	{
		return fields;
	}
}
