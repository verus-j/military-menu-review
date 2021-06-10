package military.menu.review.service.mnd.filter;

public abstract class MndRestProcessFilter extends MndSaveFilter {
    public void execute(){
        next(initCache());
    }

    abstract protected MndFilterCache initCache();
}
