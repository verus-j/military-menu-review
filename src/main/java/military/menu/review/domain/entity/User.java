package military.menu.review.domain.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class User {
    @Id @GeneratedValue @Column(name="user_id")
    private Long id;
    private String name;
}
