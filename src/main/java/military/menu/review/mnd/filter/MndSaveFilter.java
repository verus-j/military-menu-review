package military.menu.review.service.mnd.filter;

public abstract class MndSaveFilter {
    private MndSaveProcessFilter next = null;

    public void setNext(MndSaveProcessFilter next) {
        this.next = next;
    }

    protected void next(MndFilterCache cache) {
        if(hasNext()) {
            getNext().execute(cache);
        }
    }

    private boolean hasNext() {
        return getNext() != null;
    }

    private MndSaveProcessFilter getNext() {
        return next;
    }
}
