package military.menu.review.domain.member;

import lombok.Getter;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public enum Role {
    SOLDIER, NORMAL, ADMIN
}
