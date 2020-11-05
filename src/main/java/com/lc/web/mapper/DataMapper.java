package com.lc.web.mapper;


import com.lc.web.model.CrawlerBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DataMapper {
    String TABLE_NAME = " crawlerBean ";
    String INSERT_FIELDS = "resourceUrl, title, province, country, city,content,commentsCount,avargeScore,remark ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"INSERT INTO", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{resourceUrl},#{title},#{province},#{country},#{city},#{content},#{commentsCount},#{avargeScore},#{remark})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertData(CrawlerBean crawlerBean);

    @Select({"select", SELECT_FIELDS, "from ", TABLE_NAME})
    CrawlerBean selectData();


    @Select({"select", SELECT_FIELDS, "from ", TABLE_NAME, " limit #{start},#{pageSize}"})
    @Results({
            @Result(property = "comments", column = "id", javaType = List.class,
                    many = @Many(select = "com.lc.Mapper.SubDataMapper.selectDataByPid"))
    })
    List<CrawlerBean> selectDataForPage(@Param("start") int start, @Param("pageSize") int pageSize);

    @Select({"select", "count(*)", "from ", TABLE_NAME})
    public int countData();
}
