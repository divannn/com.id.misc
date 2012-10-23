package com.id.misc.xml_csv.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author idanilov
 */
public class SkillLevel {

	private String strLevel;

	public static final SkillLevel LEVEL_NONE = new SkillLevel("");
	public static final List<SkillLevel> ALL_LEVELS = new ArrayList<SkillLevel>();

	static {
		ALL_LEVELS.add(LEVEL_NONE);
		ALL_LEVELS.add(new SkillLevel("1"));
		ALL_LEVELS.add(new SkillLevel("2"));
		ALL_LEVELS.add(new SkillLevel("3"));
		ALL_LEVELS.add(new SkillLevel("4"));
		ALL_LEVELS.add(new SkillLevel("5"));
		ALL_LEVELS.add(new SkillLevel("6"));
		ALL_LEVELS.add(new SkillLevel("7"));
		ALL_LEVELS.add(new SkillLevel("8"));
		ALL_LEVELS.add(new SkillLevel("9"));
	}

	public SkillLevel(final int i) {
		this(i + "");
	}

	public SkillLevel(String str) {
		if (str == null) {
			throw new IllegalArgumentException("Specify non null string.");
		}
		strLevel = str.trim();
	}

	public String getValue() {
		return strLevel;
	}

	public int toInt() {
		int result = -1;
		try {
			result = Integer.parseInt(strLevel);
		} catch (NumberFormatException nfe) {
			// no need to handle.
		}
		return result;
	}

	public boolean isValid() {
		return ALL_LEVELS.contains(this);
	}

	public boolean isApplicable() {
		return isValid() && !LEVEL_NONE.equals(this);
	}

	public String toString() {
		return strLevel;
	}

	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof SkillLevel) {
			result = ((SkillLevel) o).strLevel.equals(strLevel);
		}
		return result;
	}
}
