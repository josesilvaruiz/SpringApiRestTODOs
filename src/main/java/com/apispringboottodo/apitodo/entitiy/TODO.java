package com.apispringboottodo.apitodo.entitiy;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="todos")
public class TODO implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer Id;
	
	@NotEmpty(message = "Can't be empty")
	@Length(max = 200)
	@Column(nullable=false)
	private String Title;
	@NotNull(message = "Can't be empty")
	@Column(nullable=false)
	private Boolean Completed;
	
	@NotNull(message = "Can't be empty")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", referencedColumnName = "Id")
	private User user;
	
public TODO() {}
	
	public TODO(String Title, Boolean Completed, User user) {
		  this.Title = Title;
		  this.Completed = Completed;
		  this.user = user;
	}
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}
	
	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public Boolean getCompleted() {
		return Completed;
	}

	public void setCompleted(Boolean completed) {
		Completed = completed;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private static final long serialVersionUID = 1L;
}
