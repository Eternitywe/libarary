<script setup>

import "./index.vue";
</script>

<template>
  <div style="width: 80%">
    <div style="margin-bottom: 20px"><h2>图书分类</h2></div>
    <el-container>
      <el-aside width="400px" style="height: 500px">
        <el-input
            v-model="filterText"
            style="width: 240px;margin-bottom: 10px"
            placeholder="请输入过滤词"
        />
        <el-tree
            style="max-width: 600px"
            :data="Tree"
            accordion
            :props="defaultProps"
            @node-click="handleNodeClick"
            default-expand-all
            :filter-node-method="filterNode"
            ref="tree"
        />
      </el-aside>
      <el-main>
        <div>
          <el-descriptions border size="large" v-show="showCategory">
            <el-descriptions-item label="分类名称">{{ category.name }}</el-descriptions-item>
            <el-descriptions-item label="分类备注">{{ category.remark }}</el-descriptions-item>
            <el-descriptions-item label="父级Pid">{{ category.pid }}</el-descriptions-item>
            <el-descriptions-item label="创建时间">{{ category.createtime }}</el-descriptions-item>
            <el-descriptions-item label="修改时间">{{ category.updatetime }}</el-descriptions-item>
          </el-descriptions>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { defineComponent } from 'vue'
import request from '../../../utils/request'
import {ElMessage} from "element-plus";


export default  defineComponent({
  name:'showCategory',
  data(){
    return{
      showCategory: false,
      id: 0,
      category:{},
      filterText: '',
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      Tree: []
    }
  },
  created() {
    request.get("/category/tree").then(res => {
      if (res.code === 200){
        console.log("获取树形数据成功")
        this.Tree = res.data;
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
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
      this.showCategory = false;
    }
  },
  methods:{
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    handleNodeClick(data) {
      console.log(data);
      this.showCategory = true;
      this.category = data;
    }
  }
})
</script>

<style scoped>

</style>