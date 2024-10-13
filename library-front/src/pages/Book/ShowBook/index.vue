<script setup>
import {Back} from "@element-plus/icons-vue";
</script>

<template>
  <div style="width: 80%">
    <div style="margin-bottom: 20px"><h2>图书详情</h2></div>
    <el-descriptions
        :title="this.formInline.name"
        direction="vertical"
        border
        style="margin-top: 20px"
    >
      <el-descriptions-item
          :rowspan="2"
          :width="140"
          label="图书封面"
          align="center"
      >
        <el-image
            style="width: 100px; height: 100px"
            :src="this.formInline.cover"
            :preview-src-list="[this.formInline.cover]"
            :preview-teleported = true
        />
      </el-descriptions-item>
      <el-descriptions-item label="图书名称">{{this.formInline.name}}</el-descriptions-item>
      <el-descriptions-item label="作者">{{ this.formInline.author }}</el-descriptions-item>
      <el-descriptions-item label="图书标准码">{{ this.formInline.bookNo }}</el-descriptions-item>
      <el-descriptions-item label="分类">
        <el-tag size="small">{{this.formInline.categoryName}}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="图书简介">
        {{this.formInline.description}}
      </el-descriptions-item>
    </el-descriptions>
    <div style="text-align: center;margin-top: 30px">
      <el-button type="primary" size="large" @click="onBack"><el-icon><Back /></el-icon>返回图书列表</el-button>
    </div>
  </div>
</template>

<script>
import { defineComponent } from 'vue'
import request from '../../../utils/request'
import {ElMessage} from "element-plus";

const showSuccess = () => {
  ElMessage({
    message: '展示图书信息成功',
    type: 'success',
    plain: true
  })
}

export default  defineComponent({
  name:'showBook',
  data(){
    return{
      formInline:{},
    }
  },
  created() {
    // console.log(this.$route.query.id)
    request.get("/book/"+this.$route.query.id).then(res => {
      console.log(res)
      this.formInline = res.data
    })
  },
  methods:{
    onBack(){
      this.formInline = {}
      this.$router.push({
        path: '/book'
      })
    }
  }
})
</script>

<style scoped>

</style>