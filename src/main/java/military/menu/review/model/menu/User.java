package military.menu.review.model.menu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    @Id @GeneratedValue @Column(name="USER_ID")
    private Long id;

    private String name;

    public User() {}

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
