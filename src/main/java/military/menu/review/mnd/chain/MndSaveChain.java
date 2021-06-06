package military.menu.review.mnd.chain;

public abstract class MndSaveChain {
    private MndSaveBodyChain next = null;

    public void setNext(MndSaveBodyChain next) {
        this.next = next;
    }

    protected void next(MndSaveChainCache cache) {
        if(hasNext()) {
            getNext().execute(cache);
        }
    }

    private boolean hasNext() {
        return getNext() != null;
    }

    private MndSaveBodyChain getNext() {
        return next;
    }
}
