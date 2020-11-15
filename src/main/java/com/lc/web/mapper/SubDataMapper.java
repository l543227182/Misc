package com.lc.web.mapper;

import com.lc.web.model.ItemComment;
import com.lc.web.utils.StringArrayTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SubDataMapper {
    String TABLE_NAME = " itemcomment ";
    String INSERT_FIELDS = "userType, score, simplizeComment, serviceComment, overallComment,itemServiceDetailComment,pid ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"INSERT INTO", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userType},#{score},#{simplizeComment},#{serviceComment},#{overallComment},#{itemServiceDetailComment},#{pid})"})
    @Results({@Result(property = "itemServiceDetailComment", column = "itemServiceDetailComment", typeHandler = StringArrayTypeHandler.class)})
    public int insertData(ItemComment itemComment);


    @Select({"select", SELECT_FIELDS, "from ", TABLE_NAME})
    @Results({@Result(property = "itemServiceDetailComment", column = "itemServiceDetailComment", typeHandler = StringArrayTypeHandler.class)})
    public ItemComment selectData();


    @Select({"select", SELECT_FIELDS, "from ", TABLE_NAME, " where pid = #{pid}"})
    List<ItemComment> selectDataByPid(String pid);
}
