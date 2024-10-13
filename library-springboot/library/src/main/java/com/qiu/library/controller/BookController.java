package com.qiu.library.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.qiu.library.common.Result;
import com.qiu.library.common.utils.TokenUtils;
import com.qiu.library.common.vo.BookPageVo;
import com.qiu.library.entity.Admin;
import com.qiu.library.entity.Book;
import com.qiu.library.service.IBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/book")
public class BookController {

    @Resource
    private IBookService bookService;

    private static final String BASE_FILE_PATH = System.getProperty("user.dir")+"files/";

    @PostMapping("/file/upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (StrUtil.isBlank(originalFilename)){
            return Result.error("文件上传失败");
        }
        long flag = System.currentTimeMillis();
        String filePath = BASE_FILE_PATH+flag+"_"+originalFilename;
        try{
            FileUtil.mkParentDirs(filePath); //创建父级目录
            file.transferTo(FileUtil.file(filePath));
            Admin admin = TokenUtils.getCurrentAdmin();
            String token = TokenUtils.genToken(admin.getId().toString(), admin.getPassword(),7);
            String url = "http://localhost:9090/api/book/file/download/"+flag+"?token="+token;
            if (originalFilename.endsWith("png") || originalFilename.endsWith("jpg")){
                url += "&play=1";
            }
            return Result.success(url);
        }catch (Exception e){
            log.error("文件上传失败，{}",e.getMessage());
        }
        return Result.error("文件上传失败");
    }

    @GetMapping("/file/download/{flag}")
    public void download(@PathVariable String flag,@RequestParam(required = false) String play, HttpServletResponse response) {
        OutputStream os;
        List<String> fileNames = FileUtil.listFileNames(BASE_FILE_PATH);
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        try{
            if (StrUtil.isNotEmpty(fileName)){
                String realFileName = fileName.substring(fileName.indexOf("_") + 1);
                if("1".equals(play)){
                    response.addHeader("Content-Disposition", "inline;filename="+ URLEncoder.encode(realFileName, "UTF-8"));
                }else{
                    response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(realFileName, "UTF-8"));
                }
                byte[] bytes = FileUtil.readBytes(BASE_FILE_PATH+fileName);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        }catch (Exception e){
            log.error("文件下载失败，{}",e.getMessage());
        }
    }

    @GetMapping("list")
    public Result SelectAllBook(){
        List<Book> list = bookService.SelectAllBook();
        return Result.success(list);
    }


    @GetMapping("{id}")
    public Result getById(@PathVariable Integer id){
        BookPageVo book = bookService.getById(id);
        return Result.success(book);
    }

    @GetMapping("page")
    public Result page(BookPageVo bookPageVo){
        return Result.success(bookService.page(bookPageVo));
    }

    @PostMapping("save")
    public Result save(@RequestBody Book book){
        bookService.save(book);
        return Result.success();
    }

    @PutMapping("update")
    public Result update(@RequestBody Book book){
        bookService.update(book);
        return Result.success();
    }

    @PostMapping("delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        bookService.deleteById(id);
        return Result.success();
    }

}
