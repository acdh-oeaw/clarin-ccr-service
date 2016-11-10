package ac.at.acdh.cmdi.ccr;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CCRConcept{

	String uri;
	
	String prefLabel;
	
	CCRStatus status;

	public CCRConcept(String prefLabel, String uri, CCRStatus status) {
		this.prefLabel = prefLabel;
		this.uri = uri;
		this.status = status;
	}
	
	
	public CCRConcept(){}

	// getters

	public String getPrefLabel() {
		return prefLabel;
	}

	public String getUri() {
		return uri;
	}

	public CCRStatus getStatus() {
		return status;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CCRConcept))
			return false;
		if (obj == this)
			return true;

		CCRConcept rhs = (CCRConcept) obj;
		return new EqualsBuilder().append(uri, rhs.uri).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(15, 45).append(uri).hashCode();
	}

	public String toString() {
		return new ToStringBuilder(this)
				.append("preferedLabel", prefLabel)
				.append("uri", uri)
				.append("status", status.name())
				.toString();
	}

}
