package life.majiang.community.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
//包括页面所有对象
public class PageinationDTO<T> {
    private List<T> data;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalPage, Integer page) {

        this.totalPage=totalPage;
        this.page=page;
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            //page往前数三个存到pages里,条件是page前三个都大于0,index等于0表示往头部加入
            if (page - i > 0) {
                pages.add(0,page - i);
            }
            //page再往后数三个,条件是page后三个都小于totalPage
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }


//        是否展示上一页
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }
//        是否展示下一页按钮
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }
//        是否展示第一页
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }
//        是否展示最后一页
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;

        }

    }
}
