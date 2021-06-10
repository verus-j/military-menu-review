package military.menu.review.service.mnd.filter;

import military.menu.review.service.mnd.api.MndApi;
import military.menu.review.service.mnd.api.parser.DailyMealsParser;
import military.menu.review.service.mnd.api.parser.MenusParser;
import military.menu.review.service.mnd.filter.impl.RestMndDataFilter;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MndSaveFilterTest {
    static class Log {
        String log = "";

        public void addLog(String log) {
            this.log += log;
        }
    }

    @Test
    public void shouldChainingFilters() {
        Log log = new Log();
        MndRestProcessFilter head = new MndRestProcessFilter() {
            @Override
            protected MndFilterCache initCache() {
                log.addLog("head ");
                return null;
            }
        };

        MndSaveProcessFilter filter1 = new MndSaveProcessFilter() {
            @Override
            protected void process(MndFilterCache cache) {
                log.addLog("filter1 ");
            }
        };

        MndSaveProcessFilter filter2 = new MndSaveProcessFilter() {
            @Override
            protected void process(MndFilterCache cache) {
                log.addLog("filter2");
            }
        };

        new MndSaveFilterBuilder(head).addChain(filter1).addChain(filter2).build().execute();

        assertThat(log.log, is("head filter1 filter2"));
    }
}
