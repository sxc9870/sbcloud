package com.sbcloud.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sbcloud.common.SysConstants;

public class AmountUtil {
	private static final char[] UNIT = { '亿', '仟', '佰', '拾', '万', '仟', '佰', '拾', '元' };
	private static final char[] UNIT_POINT = { '角', '分' };
	private static final char[] CHINESEFIGURE = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' };
	private static final Pattern AMOUNT_PATTERN = Pattern.compile("^(0|[1-9]\\d{0,11})\\.(\\d\\d)$");
	private static final char[] RMB_NUMS = "零壹贰叁肆伍陆柒捌玖".toCharArray();
	private static final String[] UNITS = { "元", "角", "分", "整" };
	private static final String[] U1 = { "", "拾", "佰", "仟" };
	private static final String[] U2 = { "", "万", "亿" };

	/**
	 * 1000>1,000.o
	 *
	 * @param o
	 * @return
	 * @throws Exception
	 */
	public static String amountFormat(int o, String amount) throws Exception {
		if (!ValidateUtil.isAmount(amount)) {
			throw new Exception("传递的参数金额格式不正确");
		}
		String temp = String.valueOf((int) Math.pow(10, o)).replace("1", "");
		DecimalFormat format = temp.length() == 0 ? new DecimalFormat("#,##0.#") : new DecimalFormat("#,##0." + temp);
		format.setRoundingMode(RoundingMode.HALF_UP);
		return format.format(new BigDecimal(String.valueOf(amount))).toString();
	}

	/**
	 * @param type加减乘
	 * @param bigDecimals
	 * @return
	 */
	public static BigDecimal amounts(int type, BigDecimal... bigDecimals) {
		BigDecimal bigDecimal = new BigDecimal(0);
		for (BigDecimal bdl : bigDecimals) {
			if (type == -1) {
				bigDecimal = bigDecimal.add(bdl);
			} else if (type == -2) {
				bigDecimal = bigDecimal.subtract(bdl);
			} else if (type == -3) {
				bigDecimal = bigDecimal.multiply(bdl);
			}
		}
		return bigDecimal;
	}

	public static BigDecimal amounts(int type, List<BigDecimal> bigDecimals) {
		BigDecimal[] decimals = new BigDecimal[bigDecimals.size()];
		bigDecimals.toArray(decimals);
		return amounts(type, decimals);
	}

	/**
	 * 计算A对于B的涨跌幅 A/B-1 今天A是11 昨天B 是10 11/10=1.1 -1= 10% 涨幅就是10%
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static BigDecimal calcUpdownRate(BigDecimal a, BigDecimal b) {
		if (b.longValue() == 0) {
			return BigDecimal.ZERO;
		}
		return (a.divide(b, 4, BigDecimal.ROUND_HALF_EVEN)).subtract(new BigDecimal(1));
	}

	/**
	 * 转换数字到以万单位的字符串
	 *
	 * @param price
	 * @return
	 */
	public static String convertTenThousand(BigDecimal price) {
		if (null != price) {
			if (price.compareTo(SysConstants.BIGDECIMAL_TENTHOUSAND) >= 0) {
				Double v = price.divide(SysConstants.BIGDECIMAL_TENTHOUSAND).doubleValue();
				if (v % 1 == 0) {
					return v.intValue() + "万";
				} else {
					return v + "万";
				}
			} else {
				return price.intValue() + "";
			}
		}
		return null;
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指定精度以后的数字截断 。
	 *
	 * @param v1    被除数
	 * @param v2    除数
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static BigDecimal div(Object v1, Object v2, int scale, int round) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.divide(b2, scale, round);
	}

	// 保留两位小数
	public static BigDecimal formatComma2BigDecimal(Object obj) {
		if (null == obj) {
			return new BigDecimal(0);
		}
		return formatComma2BigDecimal(obj, 2);
	}

	public static BigDecimal formatComma2BigDecimal(Object obj, int scale) {
		if (null == obj) {
			return new BigDecimal("0.00");
		}
		String val = String.valueOf(obj);
		val = val.replaceAll(",", "");
		if (!ValidateUtil.isNumber(val))
			return new BigDecimal("0.00");
		BigDecimal decimal = new BigDecimal(val);
		decimal = decimal.setScale(scale, RoundingMode.DOWN);

		return decimal;
	}

	/**
	 * 不保留小数
	 *
	 * @param obj
	 * @return
	 */
	public static String formatCommaAnd0Point(Object obj) {
		BigDecimal decimal = new BigDecimal(obj.toString());

		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(decimal);
	}

	/**
	 * 保留四位小数
	 *
	 * @param obj
	 * @return
	 */
	public static String formatCommaAnd4Point(Object obj) {
		BigDecimal decimal = new BigDecimal(obj.toString());

		DecimalFormat df = new DecimalFormat("#,##0.0000");
		return df.format(decimal);
	}

	/**
	 * 金额转大写
	 *
	 * @param intString
	 * @return
	 */
	public static String moneyToChinese(String intString) {
		StringBuffer result = new StringBuffer();
		String parta, partb;
		boolean isInt = true;

		if (ValidateUtil.isEmpty(intString))
			return "";

		if (intString.contains(".")) {
			String[] tmp = intString.split("\\.");
			parta = tmp[0];
			if (!intString.endsWith("."))
				partb = tmp[1];
			else
				partb = "";
		} else {
			parta = intString;
			partb = "";
		}

		if (!parta.equals("0")) {
			for (int i = 0; i < parta.length(); i++) {
				int unitIndex = UNIT.length - parta.length() + i;
				char ca = parta.charAt(i);
				if (!Character.isDigit(ca)) {
					return "数字中含有非法字符";
				}
				int ia = Character.digit(ca, 10);
				if (ia != 0) {
					result.append(CHINESEFIGURE[ia]).append(UNIT[unitIndex]);
				} else if (unitIndex % 4 == 0) {
					result.append(UNIT[unitIndex]);
				}
			}
		} else {
			isInt = false;
		}

		if (!ValidateUtil.isEmpty(partb)) {
			for (int i = 0; i < partb.length(); i++) {
				int unitIndex = i;
				char cb = partb.charAt(i);
				if (!Character.isDigit(cb)) {
					return "数字中含有非法字符";
				}
				int ib = Character.digit(cb, 10);
				if (ib != 0) {
					isInt = false;
					result.append(CHINESEFIGURE[ib]).append(UNIT_POINT[unitIndex]);
				}
			}
		}

		if (isInt)
			result.append("整");

		return result.toString();
	}

	/**
	 * 金额格式转换
	 *
	 * @param o
	 * @return
	 */
	public static String parseAmount(String core) {
		return core.replaceAll(",", "");
	}

	/**
	 * 将金额（整数部分等于或少于12位，小数部分2位）转换为中文大写形式.
	 *
	 * @param core 金额数字
	 * @return 中文大写
	 * @throws IllegalArgumentException
	 */
	public static String toAmountChinese(String core) throws IllegalArgumentException {
		core = core.replace(",", "");
		int isDecimal = core.indexOf(".");
		if (isDecimal == -1) {
			core += ".00";
		} else {
			// 小数部分为1位的时候自动补0
			if (2 == core.length() - isDecimal) {
				core += "0";
			} else if (3 <= core.length() - isDecimal) {
				core = core.substring(0, isDecimal + 3);
			}
		}
		// 验证金额正确性
		if (core.equals("0.00")) {
			return "零元整";
		}
		Matcher matcher = AMOUNT_PATTERN.matcher(core);
		if (!matcher.find()) {
			throw new IllegalArgumentException("输入金额有误.");
		}
		String integer = matcher.group(1); // 整数部分
		String fraction = matcher.group(2); // 小数部分
		String result = "";
		if (!integer.equals("0")) {
			result += toChineseBig(integer) + UNITS[0]; // 整数部分
		}
		if (fraction.equals("00")) {
			result += UNITS[3]; // 添加[整]
		} else if (fraction.startsWith("0") && integer.equals("0")) {
			result += toChinese(fraction).substring(1); // 去掉分前面的[零]
		} else {
			result += toChinese(fraction); // 小数部分
		}
		return result;
	}

	/**
	 * 将金额小数部分转换为中文大写
	 *
	 * @param fraction
	 * @return
	 */
	private static String toChinese(String fraction) {
		char jiao = fraction.charAt(0); // 角
		char fen = fraction.charAt(1); // 分
		return (RMB_NUMS[jiao - '0'] + (jiao > '0' ? UNITS[1] : ""))
				+ (fen > '0' ? RMB_NUMS[fen - '0'] + UNITS[2] : "");
	}

	/**
	 * 将金额整数部分转换为中文大写
	 *
	 * @param integer
	 * @return
	 */
	private static String toChineseBig(String integer) {
		StringBuilder buffer = new StringBuilder();
		// 从个位数开始转换
		int i, j;
		for (i = integer.length() - 1, j = 0; i >= 0; i--, j++) {
			char n = integer.charAt(i);
			if (n == '0') {
				// 当n是0且n的右边一位不是0时，插入[零]
				if (i < integer.length() - 1 && integer.charAt(i + 1) != '0') {
					buffer.append(RMB_NUMS[0]);
				}
				// 插入[万]或者[亿]
				if (j % 4 == 0) {
					if (i > 0 && integer.charAt(i - 1) != '0' || i > 1 && integer.charAt(i - 2) != '0'
							|| i > 2 && integer.charAt(i - 3) != '0') {
						buffer.append(U2[j / 4]);
					}
				}
			} else {
				if (j % 4 == 0) {
					buffer.append(U2[j / 4]); // 插入[万]或者[亿]
				}
				buffer.append(U1[j % 4]); // 插入[拾]、[佰]或[仟]
				buffer.append(RMB_NUMS[n - '0']); // 插入数字
			}
		}
		return buffer.reverse().toString();
	}

	public static String toChineseCurrency(BigDecimal o) {
		String s = new DecimalFormat("#.00").format(o);
		s = s.replaceAll("\\.", "");
		char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' };
		String unit = "仟佰拾兆仟佰拾亿仟佰拾万仟佰拾元角分";
		int l = unit.length();
		StringBuffer sb = new StringBuffer(unit);
		for (int i = s.length() - 1; i >= 0; i--)
			sb = sb.insert(l - s.length() + i, digit[(s.charAt(i) - 0x30)]);
		s = sb.substring(l - s.length(), l + s.length());
		s = s.replaceAll("零[拾佰仟]", "零").replaceAll("零{2,}", "零").replaceAll("零([兆万元])", "$1").replaceAll("零[角分]", "");
		if (s.endsWith("角"))
			s += "零分";
		if (!s.contains("角") && !s.contains("分") && s.contains("元"))
			s += "整";
		if (s.contains("分") && !s.contains("整") && !s.contains("角"))
			s = s.replace("元", "元零");
		return s;
	}

}
