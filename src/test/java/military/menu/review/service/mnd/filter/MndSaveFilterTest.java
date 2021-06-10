package military.menu.review.service.mnd.filter;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

public class MndSaveFilterTest {
    @Test
    public void shouldChainingFilters() {
        MndRestProcessFilter head = mock(MndRestProcessFilter.class);
        MndSaveProcessFilter filter1 = mock(MndSaveProcessFilter.class);
        MndSaveProcessFilter filter2 = mock(MndSaveProcessFilter.class);
        MndRestProcessFilter filter = new MndSaveFilterBuilder(head).addChain(filter1).addChain(filter2).build();

        filter.execute();

        InOrder inOrder = Mockito.inOrder(head, filter1, filter2);
        inOrder.verify(head).execute();
        inOrder.verify(filter1).execute(any(MndFilterCache.class));
        inOrder.verify(filter2).execute(any(MndFilterCache.class));
    }
}
