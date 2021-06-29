package main.entity;

import java.util.Date;
import javax.persistence.CascadeType;
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

@Table(name = "Transactions")
@Entity
@NoArgsConstructor
@Data
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@OneToOne(cascade = {CascadeType.ALL})
	private Currency currencyOwned;

	@OneToOne(cascade = {CascadeType.ALL})
	private Currency targetCurrency;

	@Column(name = "timestamp")
	private Date timestamp;
}
