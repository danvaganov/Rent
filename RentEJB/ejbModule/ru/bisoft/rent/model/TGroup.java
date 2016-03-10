package ru.bisoft.rent.model;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;
import ru.bisoft.rent.model.TUser;

/**
 * Entity implementation class for Entity: TGroup
 *
 */
@Entity
@Table(name = "TGROUP")
@SequenceGenerator(name = "tgroup_seq", sequenceName = "tgroup_seq")
public class TGroup implements Serializable {
	
	@Column(name = "GROUPID_TGROUP")
	private String groupIDTGroup;
	
	@Id
	@OneToOne
	@JoinColumn(name = "USERID_TUSER", referencedColumnName = "USERID_TUSER")
	private TUser tUser;
	
	private static final long serialVersionUID = 1L;

	public TGroup() {
		super();
	}   
	
	public String getGroupIDTGroup() {
		return this.groupIDTGroup;
	}

	public void setGroupIDTGroup(String groupIDTGroup) {
		this.groupIDTGroup = groupIDTGroup;
	}   
	public TUser getTUser() {
		return this.tUser;
	}

	public void setTUser(TUser tUser) {
		this.tUser = tUser;
	}
}
