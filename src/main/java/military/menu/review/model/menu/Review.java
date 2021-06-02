package military.menu.review.model.menu;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Review {
    @Id @GeneratedValue @Column(name="REVIEW_ID")
    private Long id;

    @Lob
    private String content;

    private LocalDate created;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name="MENULIST_ID")
    private MenuList menuList;

    public Review() {}

    public Review(User user, String content, LocalDate created) {
        this.user = user;
        this.content = content;
        this.created = created;
    }

    public void setMenuList(MenuList menuList) {
        this.menuList = menuList;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getCreated() {
        return created;
    }

    public String getUsername() {
        return user.getName();
    }

}
