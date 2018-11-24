package com.enenim.scaffold.dto.response;

import com.enenim.scaffold.shared.MetaData;
import com.enenim.scaffold.util.RequestUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

@Getter
@Setter
@ToString
public class CollectionResponse<T> {

    private String message;

    private Collection<T> data;

    @JsonProperty("meta_data")
    private MetaData metaData;

    public CollectionResponse(Collection<T> result) {
        setMessage(RequestUtil.getMessage());
        setData(result);
        setMetaData(null);
    }
}