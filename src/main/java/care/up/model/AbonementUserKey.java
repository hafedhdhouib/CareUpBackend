package care.up.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Embeddable

public class AbonementUserKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "abonnement_id")
    Long abonnement;

    @Column(name = "user_id")
    Long user;
    @Column(name="startDate")
    private Date startDate;

    @Column(name = "endDate")
    private Date endDate;

}
