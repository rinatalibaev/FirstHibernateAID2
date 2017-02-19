package models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class DocStatuses extends Model {

	////// Fields

	private static final long serialVersionUID = -2324261477400935456L;

	@Column
	private String DocStatusName;

	@OneToMany(mappedBy = "DocStatusName")
	private Set<Documents> documents = new HashSet<Documents>();

	////// Constructors

	public DocStatuses() {
		super();
	}

	public DocStatuses(String value) {
		setDocStatusName(value);
	}

	public DocStatuses(String docStatusName, Set<Documents> documents) {
		super();
		DocStatusName = docStatusName;
		this.documents = documents;
	}

	////// Methods

	public String getDocStatusName() {
		return DocStatusName;
	}

	public Set<Documents> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<Documents> documents) {
		this.documents = documents;
	}

	public void setDocStatusName(String docStatusName) {
		this.DocStatusName = docStatusName;
	}
}
