package military.menu.review.domain;

import lombok.Getter;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Role {
    @Id
    @GeneratedValue @Column(name="role_id")
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
}
