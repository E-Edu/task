package de.themorpheus.edu.taskservice.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class UserBanModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userBanId;

	@Column(columnDefinition = "BINARY(16)", nullable = false)
	private UUID userId;

}
