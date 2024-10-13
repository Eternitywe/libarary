<script setup>

import {User,Lock,Search} from "@element-plus/icons-vue";

</script>

<template>
  <div style="height: 100vh;overflow: hidden;position: relative">
    <div class="loginForm">
      <div style="text-align: center;font-size: 30px;color: #409EFF;font-weight: bold;margin-bottom: 25px">LOGIN</div>
      <el-form
          style="max-width: 600px;text-align: center"
          :model="admin"
          :rules="rules"
          ref="ruleForm"
          label-width="auto"
          class="demo-ruleForm"
      >
        <el-form-item prop="username">
          <el-input v-model="admin.username"
                    size="large"
                    placeholder="Please input username"
                    :prefix-icon="User"/>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="admin.password"
                    size="large"
                    type="password"
                    placeholder="Please input password"
                    :prefix-icon="Lock"
                    show-password
                    style="margin-bottom: 5px"/>
        </el-form-item>
        <el-form-item>
          <el-button size="large" style="width: 100%;font-weight: bold" type="primary" @click="submitForm()">
            Submit!
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script >
 import { defineComponent } from 'vue'
 import request from "../../utils/request.js";
 import {ElMessage} from "element-plus";
 import Cookies from "js-cookie";

 const LoginSuccess = () => {
   ElMessage({
     message: '登录成功',
     type: 'success',
     plain: true
   })
 }
  export default  defineComponent({
    name:'Login',
    data(){
      return{
        isShow: false,
        loginForm:{},
        admin: {
          username: '',
          password: ''
        },
        rules:{
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
          ],
        }
      }
    },
    created() {

    },
    methods:{
      submitForm(){
        this.$refs["ruleForm"].validate((valid) =>{
          if (valid){
            request.post("/admin/login",this.admin).then(res => {
              if (res.code === 200){
                console.log("登录成功")
                Cookies.set('admin',JSON.stringify(res.data))  //将用户数据转换为字符串写入cookie
                LoginSuccess()
                // Cookie.set('user',res.data,{expires: 1}) 设置1天过期
                // Cookie.get('user') 获取cookie数据
                // Cookie.remove('user') 删除cookie数据
                this.$router.push({
                  path: '/'
                })
                this.admin={}
              }else if(res.code === 10001){
                ElMessage({
                  message: res.msg,
                  type: 'error',
                  plain: true
                })
                this.admin={}
              }else{
                console.log("登录失败")
                ElMessage({
                  message: '用户名或密码错误!',
                  type: 'error',
                  plain: true
                })
                this.admin={}
              }
            })
          }
        })
      },
    }
  })
</script>

<style scoped>
.loginForm{
  width: 500px;
  height: 309px;
  background-color: white;
  border-radius: 10px;
  margin: 150px auto;
  padding: 40px;
}
</style>