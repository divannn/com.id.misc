package com.id.misc.xml_csv.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author idanilov
 */
public class SMData {

	private List<String> skillIds;
	private List<SMRow> rowData;

	public SMData(final List<String> skillIds) {
		this.skillIds = skillIds;
		rowData = new ArrayList<SMRow>();
	}

	public List<String> getSkillIds() {
		return Collections.unmodifiableList(skillIds);
	}

	public List<SMRow> getRowData() {
		return Collections.unmodifiableList(rowData);
	}

	public void addRow(final SMRow row) {
		rowData.add(row);
	}

	public int getSkillIdIndex(final String skillId) {
		int result = -1;
		for (int i = 0; i < skillIds.size(); i++) {
			String nextName = skillIds.get(i);
			if (skillId.equalsIgnoreCase(nextName)) {
				result = i;
				break;
			}
		}
		return result;
	}

	/**
	 * @param agentId
	 * @return true if such agent id already exists.
	 */
	public boolean isAgentIdExists(final String agentId) {
		boolean result = false;
		for (SMRow nextRow : rowData) {
			if (nextRow.getAgentId().equals(agentId)) {
				result = true;
				break;
			}
		}
		return result;
	}

}
