<script setup>

</script>

<template>
  <div style="width: 80%">
    <div style="margin-bottom: 20px"><h2>新增借书记录</h2></div>
    <el-form :inline="true"
             ref="ruleForm"
             :model="formInline"
             :rules="rules"
             class="demo-form-inline"
             label-width="100px">
      <el-form-item label="图书标准码" prop="bookNo">
        <el-select
            v-model="formInline.bookNo"
            filterable
            clearable
            placeholder="请选择图书标准码"
            style="width: 240px"
            @change="selBook"
        >
          <el-option
              v-for="item in books"
              :key="item.id"
              :label="item.bookNo"
              :value="item.bookNo"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="图书名称" prop="bookName">
        <el-input v-model="formInline.bookName" disabled/>
      </el-form-item>
      <el-form-item label="图书数量" prop="nums">
        <el-input v-model="formInline.nums" disabled/>
      </el-form-item>
      <el-form-item label="借书积分" prop="score">
        <el-input v-model="formInline.score" disabled/>
      </el-form-item>
      <el-form-item label="借书天数" prop="days">
        <el-input-number v-model="formInline.days" :min="1" :max="30"/>
      </el-form-item>
      <br/>
      <el-form-item label="会员码" prop="userNo">
        <el-select
            v-model="formInline.userNo"
            filterable
            clearable
            placeholder="请选择用户id"
            style="width: 240px"
            @change="selUser"
        >
          <el-option
              v-for="item in users"
              :key="item.id"
              :label="item.username.concat(' ('+item.name+')')"
              :value="item.username"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="用户名" prop="userName">
        <el-input v-model="formInline.userName" disabled/>
      </el-form-item>
      <el-form-item label="用户积分" prop="account">
        <el-input v-model="formInline.account" disabled/>
      </el-form-item>
      <el-form-item label="联系方式" prop="userPhone">
        <el-input v-model="formInline.userPhone" disabled/>
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
    message: '新增借书记录成功',
    type: 'success',
    plain: true
  })
}

export default  defineComponent({
  name:'addBook',
  data(){
    return{
      users: [],
      formInline:{
        days: 1
      },
      books:[],
      rules:{
        bookNo: [
          { required: true, message: '请选择图书标准码', trigger: 'blur' },
        ],
        userId: [
          { required: true, message: '请选择用户id', trigger: 'blur' },
        ],
      }
    }
  },
  created() {
    request.get("/book/list").then(res => {
      if (res.code === 200){
        console.log("获取图书树形数据成功")
        this.books = res.data;
      }else{
        console.log("获取图书树形数据失败")
        ElMessage({
          message: res.msg,
          type: 'error',
          plain: true
        })
      }
    })
    request.get("/user/list").then(res => {
      if (res.code === 200){
        console.log("获取用户数据成功")
        this.users = res.data.filter(v => v.status);
      }else{
        console.log("获取用户数据失败")
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
          request.post("/borrow/save",this.formInline).then(res => {
            if (res.code === 200){
              console.log("新增借书记录成功")
              addSuccess()
              this.$refs["ruleForm"].resetFields();//清空数据
            }else {
              console.log("新增借书记录失败")
              ElMessage({
                message: res.msg,
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
        message: '请重新选择图书信息',
        type: 'info',
        plain: true
      })
    },
    selBook(){
      const book =  this.books.find(v => v.bookNo === this.formInline.bookNo)
      request.get("/book/"+book.id).then(res => {
        this.formInline.bookName = res.data.name;
        this.formInline.score = res.data.score;
        this.formInline.nums = res.data.nums;
      })
    },
    selUser(){
      const user = this.users.find(v => v.username === this.formInline.userNo)
      request.get("/user/"+user.id).then(res => {
        this.formInline.userName = res.data.name;
        this.formInline.userPhone = res.data.phone;
        this.formInline.account = res.data.account;
      })
    }
  }
})
</script>

<style scoped>

</style>