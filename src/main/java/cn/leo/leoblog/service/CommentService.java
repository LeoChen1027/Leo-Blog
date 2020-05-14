package cn.leo.leoblog.service;

import cn.leo.leoblog.pojo.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long blodId);

    Comment saveComment(Comment comment);
}
