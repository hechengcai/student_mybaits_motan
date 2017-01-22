package com.student.room.web;

import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public  class FrameUtils {
	public static final String SPACE = " ";
	

	public static String toString(Object obj) {
		return ToStringBuilder.reflectionToString(obj, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public static String toClassName(Object obj) {
		if (obj == null) {
			return StringUtils.EMPTY;
		}
		return obj.getClass().getName();
	}

	public static String surroundLines(String title, String[] lines) {
		StringBuilder sb = new StringBuilder();
		sb.append(IOUtils.LINE_SEPARATOR).append(title);
		surroundLines(sb, lines);
		return sb.toString();
	}

	public static String surroundLines(String[] lines) {
		StringBuilder sb = new StringBuilder();
		surroundLines(sb, lines);
		return sb.toString();
	}

	public static String surroundLines(Collection<String> lines) {
		return surroundLines(lines.toArray(new String[lines.size()]));
	}

	public static String surroundLines(String title, Collection<String> lines) {
		return surroundLines(title, lines.toArray(new String[lines.size()]));
	}

	public static int surroundLines(StringBuilder sb, String[] lines) {
		sb.append(IOUtils.LINE_SEPARATOR);
		if (lines.length == 0) {
			sb.append("+---------------+").append(IOUtils.LINE_SEPARATOR);
			sb.append("|    (Empty)    |").append(IOUtils.LINE_SEPARATOR);
			sb.append("+---------------+").append(IOUtils.LINE_SEPARATOR);
			return "+---------------+".length();
		}
		String prefix = "|    ";
		int column = 0;
		for (String line : lines) {
			if (column < line.length()) {
				column = line.length();
			}
		}
		column = column + prefix.length() * 2;
		char[] topLine = new char[column];
		char[] bottomLine = topLine;
		char[] tabLine = new char[column];
		topLine[0] = topLine[topLine.length - 1] = '+';
		tabLine[0] = tabLine[tabLine.length - 1] = '|';
		for (int i = 1; i < (column - 1); ++i) {
			topLine[i] = '-';
			tabLine[i] = ' ';
		}
		sb.append(topLine).append(IOUtils.LINE_SEPARATOR).append(tabLine).append(IOUtils.LINE_SEPARATOR);
		for (String line : lines) {
			sb.append(prefix).append(line);
			for (int i = 0; i < (column - prefix.length() - line.length() - 1); ++i) {
				sb.append(' ');
			}
			sb.append('|').append(IOUtils.LINE_SEPARATOR);
		}
		sb.append(tabLine).append(IOUtils.LINE_SEPARATOR).append(bottomLine).append(IOUtils.LINE_SEPARATOR);
		return column;
	}
}
