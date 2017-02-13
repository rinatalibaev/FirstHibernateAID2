package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class DocTypes extends Model {

	private static final long serialVersionUID = 1L;

	@Column
	private String DocTypeName;

	public String getDocTypeName() {
		return DocTypeName;
	}

	public void setDocTypeName(String docTypeName) {
		DocTypeName = docTypeName;
	}
}
