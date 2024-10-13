<script setup>

</script>

<template>
  <div style="width: 80%">
    <div style="margin-bottom: 20px"><h2>编辑会员用户</h2></div>
    <el-form :inline="true"
             ref="ruleForm"
             :model="formInline"
             :rules="rules"
             status-icon
             class="demo-form-inline"
             label-width="100px">
      <el-form-item label="卡号">
        <el-input v-model="formInline.username" disabled />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="formInline.name" placeholder="请输入您的姓名"/>
      </el-form-item>
      <el-form-item label="年龄" prop="age">
        <el-input v-model="formInline.age" placeholder="请输入您的年龄"/>
      </el-form-item>
      <el-form-item label="性别">
        <el-radio-group v-model="formInline.sex">
          <el-radio value="男">男</el-radio>
          <el-radio value="女">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="联系方式" prop="phone">
        <el-input v-model="formInline.phone" placeholder="请输入您的联系方式"/>
      </el-form-item>
      <el-form-item label="住宅地址">
        <el-input v-model="formInline.address" placeholder="请输入您的住宅地址"/>
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
    message: '修改用户信息成功',
    type: 'success',
    plain: true
  })
}

export default  defineComponent({
  name:'editUser',
  data(){
    //年龄前端校验规则
    let checkAge = (rule, value, callback) => {
      let reg = /^(?:[1-9][0-9]?|1[01][0-9]|120)$/; //年龄是1-120之间有效
      if (value == undefined) {
        callback([new Error("年龄格式输入不合法")]);
      } else {
        if (value && value.toString().length > 0) {
          if (!reg.test(value)) {
            callback([new Error("年龄输入不合法")]);
          } else {
            callback();
          }
        } else if (value.toString().length == 0) {
          callback(new Error("年龄不能为空"));
        } else {
          callback(new Error("年龄输入不合法"));
        }
      }
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
          name: '',
          age: '',
          sex: '',
          phone: '',
          address: ''
      },
      rules:{
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur' }
        ],
        age: [
          {
            validator: checkAge,//从这里调用上面的方法即可
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
    // console.log(this.$route.query.id)
    request.get("/user/"+this.$route.query.id).then(res => {
      console.log(res)
      this.formInline = res.data
    })
  },
  methods:{
    onSubmit(){
      this.$refs["ruleForm"].validate((valid) =>{
        if (valid){
          request.put("/user/update",this.formInline).then(res => {
            if (res.code === 200){
              console.log("修改用户信息成功!")
              updateSuccess()
              this.formInline = {
                name: '',
                age: '',
                sex: '',
                phone: '',
                address: ''
              }
              this.$router.push({
                path: '/user'
              })
            }else{
              console.log("修改用户失败")
              ElMessage({
                message: '修改用户信息失败'+res.msg,
                type: 'error',
                plain: true
              })
            }
          })
        }})
    },
    onReset(){
      this.formInline = {
        name: '',
        age: '',
        sex: '',
        phone: '',
        address: ''
      }
      this.$router.push({
        path: '/user'
      })
    }
  }
})
</script>

<style scoped>

</style>