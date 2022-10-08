package bbs.mapper;

import bbs.model.Post;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PostMapper {

    @Insert("insert into post (title,description,gmt_create,gmt_modified,creator,tag)" +
            "values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Post post);

    @Select("select * from post limit #{offset},#{size}")
    List<Post> list(@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select count(*) from post")
    Integer count();
}
