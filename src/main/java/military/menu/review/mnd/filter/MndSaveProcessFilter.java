package military.menu.review.service.mnd.filter;

public abstract class MndSaveProcessFilter extends MndSaveFilter {
    public void execute(MndFilterCache cache) {
        process(cache);
        next(cache);
    }

    abstract protected void process(MndFilterCache cache);
}
