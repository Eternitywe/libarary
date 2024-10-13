<script setup>

</script>

<template>
  <div style="width: 80%">
    <div style="margin-bottom: 20px"><h2>新增管理员用户</h2></div>
    <el-form :inline="true"
             ref="ruleForm"
             :model="formInline"
             :rules="rules"
             status-icon
             class="demo-form-inline"
             label-width="100px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="formInline.username" placeholder="请输入您的用户名"/>
      </el-form-item>
      <el-form-item label="联系方式" prop="phone">
        <el-input v-model="formInline.phone" placeholder="请输入您的联系方式"/>
      </el-form-item>
      <el-form-item label="邮箱地址" prop="email">
        <el-input v-model="formInline.email" placeholder="请输入您的邮箱地址"/>
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
    message: '新增管理员用户成功',
    type: 'success',
    plain: true
  })
}

export default  defineComponent({
  name:'addAdmin',
  data(){
    //邮箱地址校验规则
    let checkEmail = (rule, value, callback) => {
      // 验证邮箱的正则表达式
      const regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
      if (regEmail.test(value)) {
        // 合法的邮箱
        return callback();
      }
      callback(new Error("请输入合法的邮箱"));
    };
    //手机号校验规则
    let validatePhone = (rule, value, callback) => {
      //校验手机号，号段主要有(不包括上网卡)：130~139、150~153，155~159，180~189、170~171、176~178。14号段为上网卡专属号段
      let regs = /^((13[0-9])|(17[0-1,6-8])|(15[^4,\\D])|(18[0-9]))\d{8}$/;
      if(value.length == 0){
        callback(new Error('请输入手机号'));
      }else{
        if(!regs.test(value)){
          callback([new Error('手机号输入不合法')]);
        }else{
          callback();
        }
      }
    }
    return{
      formInline:{
          username: '',
          phone: '',
          email: ''
      },
      rules:{
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
        email: [
          {
            validator: checkEmail,//从这里调用上面的方法即可
            trigger: "blur",
          },
        ],
        phone: [
          {
            validator: validatePhone,
            trigger: "blur"
          }
        ],
      }
    }
  },
  created() {

  },
  methods:{
    onSubmit(){
      this.$refs["ruleForm"].validate((valid) =>{
        if (valid){
          request.post("/admin/save",this.formInline).then(res => {
            if (res.code === 200){
              console.log("新增管理员用户成功")
              addSuccess()
              this.$refs["ruleForm"].resetFields();//清空数据
            }else{
              if (res.code === 300){
                console.log("新增管理员用户失败")
                ElMessage({
                  message: res.msg,
                  type: 'error',
                  plain: true
                })
              }
              console.log("新增管理员用户失败")
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
      this.formInline = {
        username: '',
        phone: '',
        email: ''
      }
      ElMessage({
        message: '请重新输入管理员用户信息',
        type: 'info',
        plain: true
      })
    }
  }
})
</script>

<style scoped>

</style>