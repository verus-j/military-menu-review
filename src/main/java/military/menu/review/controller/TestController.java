package military.menu.review.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;

class MyList{
    List<String> list = new ArrayList<>();

    public MyList() {
        list.add("a");
        list.add("b");
    }

    public List<String> getList() {
        return list;
    }
}

class Data{
    int value;
    Map<String, MyList> map = new HashMap<>();
    LocalDate date;

    public Data(int value) {
        this.value = value;
        map.put("a", new MyList());
        date = LocalDate.of(2012, 12, 1);
    }

    public int getValue() {
        return value;
    }

    public String getStr() {
        return "Hello";
    }

    public LocalDate getDate() {
        return date;
    }
}

@RestController
public class TestController {
    @GetMapping("/test")
    public Data test() {
        return new Data(12);
    }

    @GetMapping("/list")
    public List<Data> test2() {
        return Arrays.asList(new Data(1), new Data(2), new Data(3));
    }

    @GetMapping("/map")
    public Map<String, Data> test3() {
        Map<String, Data> map = new HashMap<>();
        map.put("a", new Data(1));
        map.put("b", new Data(2));
        map.put("c", new Data(3));
        return map;
    }
}
