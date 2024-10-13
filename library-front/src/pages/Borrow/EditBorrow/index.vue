<script setup>

</script>

<template>
  <div style="width: 80%">
    <div style="margin-bottom: 20px"><h2>编辑图书信息</h2></div>
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
            :options="options"
            :props="props"
            @change="handleChange"
        />
      </el-form-item>
      <el-form-item label="图书标准码" prop="bookNo">
        <el-input v-model="formInline.bookNo" placeholder="请输入图书标准码"/>
      </el-form-item>
      <el-form-item label="图书封面" prop="cover">
        <el-input v-model="formInline.cover" placeholder="请输入图书封面链接"/>
      </el-form-item>
    </el-form>
    <div style="text-align: center;margin-top: 30px">
      <el-button type="primary" @click="onSubmit"><el-icon><Check /></el-icon>提交</el-button>
      <el-button type="danger" @click="onReset"><el-icon><Close /></el-icon>返回</el-button>
    </div>
  </div>
</template>

<script>
import { defineComponent } from 'vue'
import request from '../../../utils/request'
import {ElMessage} from "element-plus";

const updateSuccess = () => {
  ElMessage({
    message: '修改图书信息成功',
    type: 'success',
    plain: true
  })
}

// 获取某一项的所有父节点的label值 list为树形结构的数据 id为回显节点的标识
const getParentsById = (list, id) => {
  for (let i in list) {
    if (list[i].id == id) {//这里的list[i].id中的id根据你的需求可进行改变
      //查询到就返回该数组对象的value
      return [list[i].id];
    }
    if (list[i].children) {
      let node = getParentsById(list[i].children, id);
      if (node !== undefined) {
        //查询到把父节把父节点加到数组前面
        node.unshift(list[i].id);
        return node;
      }
    }
  }
}

export default  defineComponent({
  name:'editBook',
  data(){
    return{
      categories: [],
      props: {
        value: 'id',
        label: 'name',
        children: 'children',
        expandTrigger: 'hover'
      },
      options: [],
      formInline:{},
      rules:{
        name: [
          { required: true, message: '请输入分类名称', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur' }
        ],
        remark: [
          { required: true, message: '请输入分类备注', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  created() {
    // console.log(this.$route.query.id)
    request.get("/book/"+this.$route.query.id).then(res => {
      console.log(res)
      this.formInline = res.data
    })

    request.get("/category/tree").then(res => {
      if (res.code === 200){
        console.log("获取树形数据成功")
        this.options = res.data;
        this.categories = getParentsById(this.options,this.formInline.categoryid)
        console.log(this.categories)
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
          request.put("/book/update",this.formInline).then(res => {
            if (res.code === 200){
              console.log("修改图书信息成功!")
              updateSuccess()
              this.formInline = {}
              this.$router.push({
                path: '/book'
              })
            }else{
              console.log("修改图书信息失败")
              ElMessage({
                message: '修改图书信息失败'+res.msg,
                type: 'error',
                plain: true
              })
            }
          })
        }})
    },
    onReset(){
      this.formInline = {}
      this.$router.push({
        path: '/book'
      })
    },
    handleChange(value) {
      console.log(value);
    }
  }
})
</script>

<style scoped>

</style>