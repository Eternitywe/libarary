package com.qiu.library.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qiu.library.entity.Category;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CategoryDTO{

   private Integer id;

   private String name;

   private String remark;

   private Integer pid;

   @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
   private Date createtime;

   @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
   private Date updatetime;

   private Integer isdeleted = 0;

   private List<CategoryDTO> children;

}
