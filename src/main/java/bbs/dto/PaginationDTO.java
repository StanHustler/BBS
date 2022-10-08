package bbs.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<PostDTO> posts;
    private boolean showPrevious;
    private boolean showFirst;
    private boolean showNext;
    private boolean showLast;
    private int totalPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();

    public void setPagination(Integer totalCount, Integer rawPage, Integer size) {
        page = rawPage;
        // handle the totalPage
        totalPage = totalCount / size;
        if (totalCount % size != 0) totalPage += 1;
        // error response
        if (page < 1) page = 1;
        if (page > totalPage) page = totalPage;

        pages.add(page);
        for (int i = 1; i < 4; i++) {
            if (page - i > 0) pages.add(0, page - i);
            if (page + i <= totalPage) pages.add(page + i);
        }

        if (page != 1) showPrevious = true;
        if (page != totalPage) showNext = true;
        if (!pages.contains(1)) showFirst = true;
        if (!pages.contains(totalPage)) showLast = true;
    }
}
