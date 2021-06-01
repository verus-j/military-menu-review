package military.menu.review.repository;

import military.menu.review.mndapi.MndApi;
import military.menu.review.mndapi.parser.DailyMenuListParser;
import military.menu.review.model.menu.DailyMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MndDailyMenuRepository implements DailyMenuRepository{
    MndApi api;

    @Autowired
    public MndDailyMenuRepository(MndApi api) {
        this.api = api;
    }

    @Override
    public List<DailyMenu> findAll() {
        return api.parse(new DailyMenuListParser());
    }

    @Override
    public void insert() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insertAll(List<DailyMenu> list) {
        throw new UnsupportedOperationException();
    }
}
