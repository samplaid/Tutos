package lu.wealins.liability.services.core.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import lu.wealins.liability.services.core.persistence.entity.CliPolRelationshipEntity;
import lu.wealins.liability.services.core.persistence.entity.PolicyBeneficiaryClauseEntity;

class PolicyClauseWrapper {

	private Integer rank;

	private boolean nominated;

	private PolicyBeneficiaryClauseEntity text;

	private CliPolRelationshipEntity person;

	public PolicyBeneficiaryClauseEntity getText() {
		return text;
	}

	public void setText(PolicyBeneficiaryClauseEntity text) {
		this.text = text;
	}

	public CliPolRelationshipEntity getPerson() {
		return person;
	}

	public void setPerson(CliPolRelationshipEntity person) {
		this.person = person;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public boolean isNominated() {
		return nominated;
	}

	public void setNominated(boolean nominated) {
		this.nominated = nominated;
	}

	public static PolicyClauseWrapper withTextClauses(PolicyBeneficiaryClauseEntity e) {

		PolicyClauseWrapper c = new PolicyClauseWrapper();
		c.setText(e);
		c.setRank(e.getRank());
		c.setNominated(false);
		return c;
	}

	public static PolicyClauseWrapper withNominatedClauses(CliPolRelationshipEntity e) {
		PolicyClauseWrapper c = new PolicyClauseWrapper();
		c.setPerson(e);
		c.setRank(e.getTypeNumber());
		c.setNominated(true);
		return c;
	}

	@Override
	public String toString() {

		if (this.text != null) {
			String clause = "";
			if (!text.getTypeOfClause().equals("F")) { // this has been changed because of Scriptura CP editing
				clause = text.getRank() + ". ";
			}
			clause += StringUtils.capitalize(text.getTextOfClause());

			return clause;
		}

		if (this.person != null) {

			StringBuilder clause = new StringBuilder();

			clause.append(person.getTypeNumber());
			clause.append(". ");
			clause.append(person.getClient().getName().trim());

			if (StringUtils.isNotBlank(person.getClient().getFirstName())) {
				clause.append(" ");
				clause.append(person.getClient().getFirstName().trim());
			}

			if (person.getClient().getDateOfBirth() != null) {
				clause.append(" (");
				clause.append(DateFormatUtils.format(person.getClient().getDateOfBirth(), "dd/MM/yyyy"));
				clause.append(")");
			}

			if (person.getPercentageSplit() != null) {
				clause.append(" ");
				clause.append(person.getPercentageSplit().floatValue());
				clause.append("%");
			}

			return clause.toString();
		}

		return "";
	}

	public static List<String> toList(List<PolicyClauseWrapper> clauses) {

		ArrayList<String> rv = new ArrayList<String>();

		for (PolicyClauseWrapper c : clauses) {
			rv.add(c.toString());
		}

		return rv;
	}

}