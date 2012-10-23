package com.id.misc.xml_csv.model;

import java.util.List;

/**
 * @author idanilov
 */
public class SMRow {

	private String agentId;
	private List<SkillLevel> skillData;

	public SMRow(final String id, final List<SkillLevel> data) {
		agentId = id;
		skillData = data;
	}

	public String getAgentId() {
		return agentId;
	}

	public int getColumnCount() {
		return skillData.size();
	}

	public List<SkillLevel> getSkillData() {
		return skillData;
	}

	public String toString() {
		return "agent id: " + agentId + " model: "
				+ (skillData != null ? skillData.toString() : "NULL");
	}

}