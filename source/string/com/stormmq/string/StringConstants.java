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

public final class StringConstants
{
	@NotNull @NonNls public static final String Should_not_be_possible = "Should not be possible";
	@NotNull @NonNls public static final String Should_be_impossible = "Should be impossible";

	@NotNull @NonNls private static final String value = "value";
	@NotNull @NonNls public static final String DefaultAnnotationMemberName = value;

	// Keywords
	@NotNull @NonNls public static final String _super = "super";
	@NotNull @NonNls public static final String _class = "class";
	@NotNull @NonNls public static final String _interface = "interface";
	@NotNull @NonNls public static final String _enum = "enum";
	@NotNull @NonNls public static final String _protected = "protected";
	@NotNull @NonNls public static final String _private = "private";
	@NotNull @NonNls public static final String _default = "default";
	@NotNull @NonNls public static final String _void = "void";
	@NotNull @NonNls public static final String _boolean = "boolean";
	@NotNull @NonNls public static final String _byte = "byte";
	@NotNull @NonNls public static final String _char = "char";
	@NotNull @NonNls public static final String _short = "short";
	@NotNull @NonNls public static final String _float = "float";
	@NotNull @NonNls public static final String _double = "double";
	@NotNull @NonNls public static final String _int = "int";
	@NotNull @NonNls public static final String _long = "long";
	@NotNull @NonNls public static final String _true = "true";
	@NotNull @NonNls public static final String _false = "false";
	@NotNull @NonNls public static final String _null = "null";

	@NotNull @NonNls public static final String StaticInitializerMethodName = "<clinit>";
	@NotNull @NonNls public static final String InstanceInitializerMethodName = "<init>";

	@SuppressWarnings("HardcodedFileSeparator") public static final char InternalTypeNameSeparator = '/';
	@NotNull @NonNls public static final String InternalTypeNameSeparatorString = String.valueOf(InternalTypeNameSeparator);
	public static final char ExternalTypeNameSeparator = '.';
	@NotNull @NonNls public static final String ExternalTypeNameSeparatorString = String.valueOf(InternalTypeNameSeparator);
	public static final char EndOfTypeDescriptorCharacter = ';';
	public static final char ArrayTypeCodeCharacter = '[';
	public static final char StartOfMethodDescriptorParameters = '(';
	public static final char EndOfMethodDescriptorParameters = ')';

	private StringConstants()
	{
	}
}
