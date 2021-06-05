package military.menu.review.mnd.chain;

import military.menu.review.domain.dto.DailyMealDTO;
import military.menu.review.domain.dto.MenuDTO;

import java.util.List;

public abstract class MndSaveBodyChain extends MndSaveChain{
    protected void execute(MndSaveChainCache cache) {
        process(cache);
        next(cache);
    }

    abstract protected void process(MndSaveChainCache cache);
}
