package cn.leo.leoblog.web;
    /*主要有这些类：
    1.博客;
    2.博客分类;
    3.博客标签;
    4.博客评论;
    5.用户.*/

import cn.leo.leoblog.NotFoundException;
import cn.leo.leoblog.pojo.Blog;
import cn.leo.leoblog.service.BlogService;
import cn.leo.leoblog.service.TagService;
import cn.leo.leoblog.service.TypeService;
import cn.leo.leoblog.util.MarkdownUtils;
import cn.leo.leoblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class indexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(6));
        model.addAttribute("recommendBlogs",blogService.listBlogTop(4));
        model.addAttribute("mostviewBlogs",blogService.listViewBlog());
        return "index";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        Blog blog1=blogService.getBlog(id);
        model.addAttribute("blog",blog1);
        String content= MarkdownUtils.markdownToHtmlExtensions(blog1.getContent());
        model.addAttribute("content",content);
        return "blog";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));//为什么加百分号加一下
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/about")
    public String aboutBlog(){
        return "about";
    }

    @GetMapping("footer/newblog")
    public String newBlogs(Model model){
        model.addAttribute("newBlogs",blogService.listBlogTop(3));
        return "_fragments::newBlogList";
    }
}
