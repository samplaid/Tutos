#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "something")
public class SomethingEntity implements java.io.Serializable {

	private static final long serialVersionUID = -859053444531838830L;

	@Id
	private String id;

	@Column(name = "NAME", nullable = false, length = 40)
	private String name;

	public SomethingEntity() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}