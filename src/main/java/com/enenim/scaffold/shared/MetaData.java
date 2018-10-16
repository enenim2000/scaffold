package com.enenim.scaffold.shared;

import com.enenim.scaffold.util.RequestUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.domain.Page;

import static com.enenim.scaffold.constant.CommonConstant.PAGE;

@Data
public class MetaData<T>{

    private Long total;

    @JsonProperty("per_page")
    private int perPage;

    @JsonProperty("current_page")
    private int currentPage;

    @JsonProperty("last_page")
    private int lastPage;

    @JsonProperty("next_page_url")
    private String nextPageUrl;

    @JsonProperty("prev_page_url")
    private String prevPageUrl;

    private Long from;
    private Long to;

    public MetaData(Page<T> result){

        setTotal(result.getTotalElements());

        setPerPage(Integer.valueOf(RequestUtil.perPage()));

        int nextPage = RequestUtil.page() <= 0 ? 1 : RequestUtil.page() + 1;

        int prevPage = RequestUtil.page() <= 0 ? 0 : RequestUtil.page() - 1;

        if(!result.getContent().isEmpty()){
            if(result.isFirst() && result.isLast()){
                setNextPageUrl(null);
                setPrevPageUrl(null);
            }else if(!result.isFirst() && !result.isLast()){
                setPrevPageUrl( RequestUtil.getRequest().getRequestURL().append("?").append(PAGE).append("=").append(prevPage).toString() );
                setNextPageUrl( RequestUtil.getRequest().getRequestURL().append("?").append(PAGE).append("=").append(nextPage).toString() );
            }else if(result.isFirst() && !result.isLast()){
                setPrevPageUrl( null );
                setNextPageUrl( RequestUtil.getRequest().getRequestURL().append("?").append(PAGE).append("=").append(nextPage).toString() );
            }

            setLastPage(result.getTotalPages());
            setCurrentPage(RequestUtil.page());
            setFrom(Long.valueOf(RequestUtil.from()));
            setTo(Long.valueOf(RequestUtil.to()));
        }
    }
}
