<script setup>

</script>

<template>
  <div style="width: 80%">
    <div style="margin-bottom: 20px"><h2>新增图书分类</h2></div>
    <el-form :inline="true"
             ref="ruleForm"
             :model="formInline"
             :rules="rules"
             status-icon
             class="demo-form-inline"
             label-width="100px">
      <el-form-item label="分类名称" prop="name">
        <el-input v-model="formInline.name" placeholder="请输入分类名称"/>
      </el-form-item>
      <el-form-item label="分类备注" prop="remark">
        <el-input v-model="formInline.remark" placeholder="请输入分类备注"/>
      </el-form-item>
      <el-form-item label="选择父级分类" prop="pid">
        <el-select v-model="formInline.pid" placeholder="请选择父级分类" style="width: 240px">
          <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.name"
              :value="item.id"
          />
        </el-select>
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


const addSuccess = () => {
  ElMessage({
    message: '新增分类成功',
    type: 'success',
    plain: true
  })
}

export default  defineComponent({
  name:'addCategory',
  data(){
    return{
      formInline:{
          name: '',
          remark: '',
      },
      options:[],
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
    request.get("/category/getAllParentCategory").then(res => {
      if (res.code === 200){
        console.log("获取父级分类成功")
        this.options = res.data;
      }else{
        console.log("获取父级分类失败")
      }
    })
  },
  methods:{
    onSubmit(){
      this.$refs["ruleForm"].validate((valid) =>{
        if (valid){
          request.post("/category/save",this.formInline).then(res => {
            if (res.code === 200){
              console.log("新增分类成功")
              addSuccess()
              this.$refs["ruleForm"].resetFields();//清空数据
            }else{
              console.log("新增分类失败")
              ElMessage({
                message: "新增分类失败"+res.msg,
                type: 'error',
                plain: true
              })
            }
          })
        }
      })
    },
    onReset(){
      this.formInline = {
        name: '',
        remark: '',
      }
      ElMessage({
        message: '请重新输入分类信息',
        type: 'info',
        plain: true
      })
    }
  }
})
</script>

<style scoped>

</style>