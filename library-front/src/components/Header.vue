<script setup>

</script>

<template>
  <div style="height: 60px;line-height: 60px;background-color: white;margin-bottom: 2px;display: flex">

    <div style="width: 300px">
      <img src="../assets/logo.png" alt="" style="width: 40px;position: relative;top: 10px;left: 5px">
      <span style="margin-left: 20px;font-size: 24px">图书管理系统</span>
    </div>

    <div style="flex: 1">
      <el-dropdown trigger="click" class="el-dropdown-link" style="float: right;line-height: 60px;margin-right: 15px;cursor: pointer">
        <span>
          {{ admin.username }}
          <el-icon class="el-icon--right">
            <arrow-down />
          </el-icon>
        </span>
        <template #dropdown style="margin-top: -10px">
          <el-dropdown-menu>
            <el-dropdown-item class="clearfix" @click="toUserCenter">
              <el-icon><UserFilled /></el-icon>个人中心
            </el-dropdown-item>
            <el-dropdown-item class="clearfix" @click="toLogin">
              <el-icon><Close /></el-icon>退出登录
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
 import { defineComponent } from 'vue'
 import Cookies from "js-cookie";
  export default  defineComponent({
    name:'Header',
    data(){
      return{
        admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {}
      }
    },
    created(){

    },
    methods:{
      toUserCenter(){
        this.$router.push('/userCenter')
      },
      toLogin(){
        //清空浏览器用户数据
        Cookies.remove('admin')
        this.$router.push('/login')
      }
    }
  })
</script>

<style scoped>

</style>