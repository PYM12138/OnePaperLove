package com.pym.mingblog.controller;

import com.pym.mingblog.model.Category;
import com.pym.mingblog.service.CategoryService;
import com.pym.mingblog.utils.DataMap;
import com.pym.mingblog.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Ming
 * @Date 2025/5/19 17:22
 * @Description: 分类控制器
 * @Version 1.0
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public String getAllCategories() {
        DataMap dataMap = categoryService.findAllCategories();
        if (dataMap != null) {
            return JsonResult.build(dataMap).toJSON();
        } else {
            return JsonResult.fail().toJSON();
        }
    }
  /*  @PostMapping("/add")
    public ResponseEntity<String> addCategory(@RequestBody Category category) {
        Integer result = categoryService.addCategory(category);
        if (result == null) {
            // 分类名重复，返回 409
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(JsonResult.fail(409).toJSON());
        } else if (result == 1) {
            return ResponseEntity.ok(JsonResult.success(200).toJSON());
        } else {
            // 其他错误，返回 400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResult.fail(400).toJSON());
        }
    }
*/
    @PostMapping("/add")
    public ResponseEntity<String> addCategory(@RequestBody Category category) {
        Integer result = categoryService.addCategory(category);
        if (result == null) {
            // 分类名重复，返回 409。这里建议 status 传 409，方便前端 error 回调处理
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(JsonResult.fail(409).message("分类名称已存在").toJSON());
        } else if (result == 1) {
            // 成功：使用默认的 0 (DEFAULT_STATUS_SUCCESS)
            // 或者如果你坚持要 200，请确保前端逻辑一致
            return ResponseEntity.ok(JsonResult.success().message("添加成功").toJSON());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResult.fail(400).message("请求参数错误").toJSON());
        }
    }






}

