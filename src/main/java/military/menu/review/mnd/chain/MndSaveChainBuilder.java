package military.menu.review.mnd.chain;


public class MndSaveChainBuilder {
    private final MndSaveHeaderChain header;
    private MndSaveBodyChain tail = null;

    public MndSaveChainBuilder(MndSaveHeaderChain header) {
        this.header = header;
    }

    public MndSaveChainBuilder addChain(MndSaveBodyChain chain) {
        connectWithTail(chain);
        changeTail(chain);
        return this;
    }

    private void connectWithTail(MndSaveBodyChain chain) {
        findTail().setNext(chain);
    }

    private void changeTail(MndSaveBodyChain chain) {
        tail = chain;
    }

    private MndSaveChain findTail() {
        return hasTail() ? tail : header;
    }

    private boolean hasTail() {
        return tail != null;
    }

    public MndSaveHeaderChain build() {
        return header;
    }
}
