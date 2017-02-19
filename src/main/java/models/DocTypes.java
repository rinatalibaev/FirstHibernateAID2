package models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class DocTypes extends Model {

	private static final long serialVersionUID = 1L;

	@Column
	private String DocTypeName;

	@OneToMany(mappedBy = "docType")
	private Set<Documents> documents = new HashSet<Documents>();

	public String getDocTypeName() {
		return DocTypeName;
	}

	public Set<Documents> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<Documents> documents) {
		this.documents = documents;
	}

	public void setDocTypeName(String docTypeName) {
		this.DocTypeName = docTypeName;
	}
}
