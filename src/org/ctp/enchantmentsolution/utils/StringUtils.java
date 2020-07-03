package org.ctp.enchantmentsolution.utils;

import java.util.List;

public class StringUtils {
	public static String join(List<? extends Object> strings, String divider) {
		StringBuilder sb = new StringBuilder();

		for(int i = 0; i < strings.size(); i++) {
			sb.append(strings.get(i));
			if (i < strings.size() - 1) sb.append(divider);
		}

		return sb.toString();
	}

	public static String encodeString(String st) {
		String regex = "\\n";
		String regex2 = "\"";

		return st.replaceAll(regex, "\\\\n").replaceAll(regex2, "\\\"");
	}

	public static String decodeString(String st) {
		if (st == null) return st;
		StringBuilder sb = new StringBuilder(st.length());

		for(int i = 0; i < st.length(); i++) {
			char ch = st.charAt(i);
			if (ch == '\\') {
				char nextChar = i == st.length() - 1 ? '\\' : st.charAt(i + 1);
				// Octal escape?
				if (nextChar >= '0' && nextChar <= '7') {
					String code = "" + nextChar;
					i++;
					if (i < st.length() - 1 && st.charAt(i + 1) >= '0' && st.charAt(i + 1) <= '7') {
						code += st.charAt(i + 1);
						i++;
						if (i < st.length() - 1 && st.charAt(i + 1) >= '0' && st.charAt(i + 1) <= '7') {
							code += st.charAt(i + 1);
							i++;
						}
					}
					sb.append((char) Integer.parseInt(code, 8));
					continue;
				}
				switch (nextChar) {
					case '\\':
						ch = '\\';
						break;
					case 'b':
						ch = '\b';
						break;
					case 'f':
						ch = '\f';
						break;
					case 'n':
						ch = '\n';
						break;
					case 'r':
						ch = '\r';
						break;
					case 't':
						ch = '\t';
						break;
					case '\"':
						ch = '\"';
						break;
					case '\'':
						ch = '\'';
						break;
				}
				i++;
			}
			sb.append(ch);
		}
		return sb.toString();
	}
}
