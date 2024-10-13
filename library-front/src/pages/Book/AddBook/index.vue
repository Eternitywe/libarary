<script setup>

</script>

<template>
  <div style="width: 80%">
    <div style="margin-bottom: 20px"><h2>新增图书</h2></div>
    <el-form :inline="true"
             ref="ruleForm"
             :model="formInline"
             :rules="rules"
             status-icon
             class="demo-form-inline"
             label-width="100px">
      <el-form-item label="图书名称" prop="name">
        <el-input v-model="formInline.name" placeholder="请输入图书名称"/>
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input v-model="formInline.author" placeholder="请输入作者姓名"/>
      </el-form-item>
      <el-form-item label="图书简介" prop="description">
        <el-input type="textarea"
                  v-model="formInline.description"
                  placeholder="请输入图书简介"
                  :rows="2"/>
      </el-form-item>
      <el-form-item label="出版日期" prop="publishDate">
        <el-date-picker
            v-model="formInline.publishDate"
            type="date"
            placeholder="请选择出版日期"
        />
      </el-form-item>
      <el-form-item label="出版社" prop="publisher">
        <el-input v-model="formInline.publisher" placeholder="请输入出版社"/>
      </el-form-item>
      <el-form-item label="分类" prop="categoryid">
        <el-cascader
            v-model="categories"
            placeholder="选择图书分类"
            :options="options"
            :props="props"
            @change="handleChange"
        />
      </el-form-item>
      <el-form-item label="图书标准码" prop="bookNo">
        <el-input v-model="formInline.bookNo" placeholder="请输入图书标准码"/>
      </el-form-item>
      <el-form-item label="所需积分" prop="score">
        <el-input-number v-model="formInline.score" :min="10" :max="100" @change="handleChange" />
      </el-form-item>
      <el-form-item label="图书数量" prop="nums">
        <el-input-number v-model="formInline.nums" :min="10" :max="100" @change="handleChange" />
      </el-form-item>
      <el-form-item label="图书封面" prop="cover">
        <el-upload
            class="avatar-uploader"
            :action="'http://localhost:9090/api/book/file/upload?token='+this.admin.token"
            :show-file-list="false"
            :on-success="handleUploadSuccess">
          <img v-if="formInline.cover" :src="formInline.cover" class="avatar">
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </el-form-item>
    </el-form>
    <div style="text-align: center;margin-top: 30px">
      <el-button type="primary" @click="onSubmit"><el-icon><Check /></el-icon>提交</el-button>
      <el-button type="danger" @click="onReset"><el-icon><Close /></el-icon>重置</el-button>
    </div>
  </div>
</template>

<script>
import { defineComponent } from 'vue'
import request from '../../../utils/request'
import {ElMessage} from "element-plus";
import Cookies from "js-cookie";


const addSuccess = () => {
  ElMessage({
    message: '新增图书成功',
    type: 'success',
    plain: true
  })
}

export default  defineComponent({
  name:'addBook',
  data(){
    return{
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      categories: [],
      props:{
        value: 'id',
        label: 'name',
        children: 'children',
        expandTrigger: 'hover'
      },
      formInline:{
        score: 10,
        nums: 10
      },
      options:[],
      rules:{
        name: [
          { required: true, message: '请输入图书名称', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur' }
        ],
        bookNo: [
          { required: true, message: '请输入图书标准码', trigger: 'blur' },
        ],
        score: [
          { required: true, message: '请输入借书积分', trigger: 'blur' },
        ],
        nums: [
          { required: true, message: '请输入图书数量,至少为10', trigger: 'blur' },
        ],
      }
    }
  },
  created() {
    request.get("/category/tree").then(res => {
      if (res.code === 200){
        console.log("获取树形数据成功")
        this.options = res.data;
      }else{
        console.log("获取树形数据失败")
        ElMessage({
          message: res.msg,
          type: 'error',
          plain: true
        })
      }
    })
  },
  methods:{
    onSubmit(){
      this.$refs["ruleForm"].validate((valid) =>{
        if (valid){
          console.log(this.categories[this.categories.length-1])
          this.formInline.categoryid = this.categories[this.categories.length-1]
          request.post("/book/save",this.formInline).then(res => {
            if (res.code === 200){
              console.log("新增图书成功")
              addSuccess()
              this.$refs["ruleForm"].resetFields();//清空数据
            }else{
              console.log("新增图书失败")
              ElMessage({
                message: "新增图书失败"+res.msg,
                type: 'error',
                plain: true
              })
            }
          })
        }
      })
    },
    onReset(){
      this.formInline = {}
      ElMessage({
        message: '请重新输入图书信息',
        type: 'info',
        plain: true
      })
    },
    handleUploadSuccess(res){
      if (res.code === 200){
        this.formInline.cover = res.data
      }
    }
  }
})
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>