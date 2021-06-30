package military.menu.review.repository.rank;

import military.menu.review.domain.Rank;
import military.menu.review.repository.impl.OrderedRankLimitRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankRepository extends JpaRepository<Rank, Long>, OrderedRankLimitRepository {
}
