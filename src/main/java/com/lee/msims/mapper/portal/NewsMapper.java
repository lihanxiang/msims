package com.lee.msims.mapper.portal;

import com.lee.msims.pojo.common.File;
import com.lee.msims.pojo.portal.News;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NewsMapper {

    // Insert
    @Insert("INSERT INTO news (title, faculty, content, date)" +
            "VALUES (#{title}, #{faculty}, #{content}, #{date})")
    void createNews(News news);

    @Insert("INSERT INTO news_file (newsId, fileId) VALUES (#{newsId}, #{fileId})")
    void addFileToNews(@Param("newsId") int newsId, @Param("fileId") int fileId);

    // Update
    @Update("UPDATE new SET title = #{title} AND faculty = #{faculty}" +
            "AND content = #{content} AND date = #{date}")
    void editNews(News news);

    // Select
    @Select("SELECT * FROM news WHERE id = #{id}")
    News getNewsById(int id);

    @Select("SELECT * FROM news LIMIT 5 DESC")
    List<News> getFiveLatestNews();

    @Select("SELECT * FROM news")
    List<News> getAllNews();

    @Select("SELECT * FROM news WHERE faculty = #{faculty}")
    List<News> getNewsInFaculty(String faculty);

    @Select("SELECT * FROM news WHERE name = %#{keyword}%")
    List<News> getNewsByName(String keyword);

    @Select("SELECT * FROM news WHERE date >= #{min} AND date <= #{max}")
    List<News> getNewsByDate(@Param("min") String min, @Param("max") String max);

    /*@Select("SELECT * FROM news WHERE faculty = #{faculty} AND name = %#{keyword}%")
    List<News> getNewsByFacultyAndName(@Param("faculty") String faculty, @Param("keyword") String keyword);

    @Select("SELECT * FROM news WHERE faculty = #{faculty} AND name = %#{keyword}%")
    List<News> getNewsByFacultyAndDate(@Param("faculty") String faculty, @Param("min") String min,
                                       @Param("max") String max);*/

    // Delete
    @Delete("DELETE FROM news WHERE id = #{id}")
    void deleteNews(int id);
}
