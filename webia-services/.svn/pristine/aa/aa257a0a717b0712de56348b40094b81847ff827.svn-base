package lu.wealins.webia.services.core.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "WORKFLOW_QUEUE")
// WORKFLOW_QUEUE is a SYNONYM
public class WorkflowQueueEntity implements Serializable {

	private static final long serialVersionUID = 6569997569238643131L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "NAME0")
	private String name0;
	@Column(name = "TYPE0")
	private Integer type0;
	@Column(name = "STATUS")
	private Integer status;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_USERSUSR_ID")
	@NotFound(action = NotFoundAction.IGNORE)
	private WorkflowUserEntity user;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_USER_GROUPSURG_ID")
	@NotFound(action = NotFoundAction.IGNORE)
	private WorkflowGroupEntity userGroup;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName0() {
		return name0;
	}
	public void setName0(String name0) {
		this.name0 = name0;
	}
	public Integer getType0() {
		return type0;
	}
	public void setType0(Integer type0) {
		this.type0 = type0;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public WorkflowUserEntity getUser() {
		return user;
	}

	public void setUser(WorkflowUserEntity user) {
		this.user = user;
	}

	public WorkflowGroupEntity getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(WorkflowGroupEntity userGroup) {
		this.userGroup = userGroup;
	}
	
}
