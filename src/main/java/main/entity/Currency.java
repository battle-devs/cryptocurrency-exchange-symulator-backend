package main.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "Currencies")
@Entity
@NoArgsConstructor
@Data
public class Currency {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@OneToOne
	private Asset asset;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "purchase_date", nullable = false)
	private Date dateOfPurchase;

}
