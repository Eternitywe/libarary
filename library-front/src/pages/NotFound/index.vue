<template>
  <div style="text-align: center">
    <div style="margin-top: 150px;font-size: 100px;">
      404
      <span style="font-size: 50px;">Not Found</span>
    </div>
    <div style="font-size: 30px;">
      <span style="color: #409EFF;cursor: pointer" @click="toLogin">返回首页</span>
    </div>
  </div>
</template>

<script>
import { defineComponent } from 'vue'
import Cookie from "js-cookie";
export default  defineComponent({
  name:'NotFound',
  data(){
    return {

    }
  },
  created(){

  },
  methods:{
    toLogin(){
      this.$router.push('/')
    }
  }
  }
)
</script>
<style scoped>

</style>