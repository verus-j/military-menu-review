package military.menu.review.service.mnd.filter;


public class MndSaveFilterBuilder {
    private final MndRestProcessFilter header;
    private MndSaveProcessFilter tail = null;

    public MndSaveFilterBuilder(MndRestProcessFilter header) {
        this.header = header;
    }

    public MndSaveFilterBuilder addChain(MndSaveProcessFilter chain) {
        connectWithTail(chain);
        changeTail(chain);
        return this;
    }

    private void connectWithTail(MndSaveProcessFilter chain) {
        findTail().setNext(chain);
    }

    private void changeTail(MndSaveProcessFilter chain) {
        tail = chain;
    }

    private MndSaveFilter findTail() {
        return hasTail() ? tail : header;
    }

    private boolean hasTail() {
        return tail != null;
    }

    public MndRestProcessFilter build() {
        return header;
    }
}
