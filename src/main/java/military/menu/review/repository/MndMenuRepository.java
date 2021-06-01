package military.menu.review.repository;

import military.menu.review.mndapi.MndApi;
import military.menu.review.mndapi.parser.MenuListParser;
import military.menu.review.model.menu.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MndMenuRepository implements MenuRepository {
    private MndApi api;

    @Autowired
    public MndMenuRepository(MndApi api) {
        this.api = api;
    }

    @Override
    public List<Menu> findAll() {
        return api.parse(new MenuListParser());
    }

    @Override
    public Menu findByName(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insert(Menu menu) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insertAll(List<Menu> list) {
        throw new UnsupportedOperationException();
    }
}
