<script setup>

</script>

<template>
  <el-descriptions title="User Info" border size="large">
    <el-descriptions-item label="Username">{{ admin.username }}</el-descriptions-item>
    <el-descriptions-item label="Telephone">{{ admin.phone }}</el-descriptions-item>
    <el-descriptions-item label="Email">{{ admin.email }}</el-descriptions-item>
  </el-descriptions>
</template>

<script>
import { defineComponent } from 'vue'
import Cookies from "js-cookie";

export default  defineComponent({
  name:'userCenter',
  data(){
    return{
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {}
    }
  },
  created(){

  },
  methods:{
  }
})
</script>

<style scoped>

</style>