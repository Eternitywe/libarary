<script setup>
import { ref } from 'vue'
const name = ref('')
const phone = ref('')
</script>

<template>
  <div>
    <!--      搜索表单组件-->
    <div style="margin-bottom: 10px">
      <el-input
          v-model="params.name"
          style="width: 240px;margin-right: 5px"
          placeholder="请输入分类名称"
          clearable
      />
      <el-input
          v-model="params.remark"
          style="width: 240px;margin-right: 5px"
          placeholder="请输入分类备注"
          clearable
      />
      <el-button type="primary" style="margin-right: 5px" @click="load">
        <el-icon><Search /></el-icon>搜索
      </el-button>
      <el-button type="danger" @click="refresh">
        <el-icon><Refresh /></el-icon>重置
      </el-button>
    </div>
    <!--      数据表格组件-->
    <div style="margin-bottom: 20px;overflow: hidden;">
      <el-table :data="tableData" stripe border>
        <el-table-column fixed prop="id" label="主键" width="80" />
        <el-table-column prop="name" label="分类名称" width="150" />
        <el-table-column prop="remark" label="分类备注" width="140" />
        <el-table-column prop="pid" label="父级id" width="80" />
        <el-table-column prop="pname" label="父级分类名称" width="140" />
        <el-table-column prop="createtime" label="创建时间"/>
        <el-table-column prop="updatetime" label="修改时间"/>
        <el-table-column label="操作" width="260">
          <template v-slot="scope">
            <el-button type="primary" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
            <el-popconfirm
                confirm-button-text="确定"
                cancel-button-text="取消"
                title="您确定删除当前行数据嘛?"
                @confirm="handleDelete(scope.row)"
            >
              <template #reference>
                <el-button type="danger" size="mini">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!--分页组件-->
    <div>
      <el-pagination
          background
          v-model:current-page= "params.pageNum"
          v-model:page-size= "params.pageSize"
          @current-change = "handleCurrentChange"
          layout="prev, pager, next"
          :total="total" />
    </div>
  </div>
</template>

<script>
import { defineComponent } from 'vue'
import request from '../../utils/request'
import {ElMessage} from "element-plus";
import Cookies from "js-cookie";

const deleteSuccess = () => {
  ElMessage({
    message: '删除分类信息成功',
    type: 'success',
    plain: true
  })
}


export default  defineComponent({
  name:'Category',
  data(){
    return{
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      total: 0,
      tableData: [],
      params: {
        pageNum: 1,
        pageSize: 10,
        name: '',
        remark: ''
      },
    }
  },
  created() {
    this.load();
  },
  methods:{
    load(){
      request.get("/category/page",{
        params: this.params
      }).then(res => {
        console.log(res)
        if (res.code === 200){
          this.tableData = res.data.list;
          this.total = parseInt(res.data.total);
        }
      })
    },
    refresh(){
      this.params= {
        pageNum: 1,
        pageSize: 10,
        name: '',
        remark: ''
      };
      this.load();
    },
    handleCurrentChange(pageNum){
      //点击分页按钮实现分页数据显示
      // console.log(pageNum)
      this.params.pageNum = pageNum;
      this.load();
    },
    handleEdit(row){
      // console.log(row.id)
      this.$router.push({
        path: '/editCategory',
        query: {id: row.id}
      })
    },
    handleDelete(row){
      request.get("/category/getByParentId/"+row.id).then(res => {
        if (res.code === 444){
          console.log("该分类下有子分类,不可删除!")
          ElMessage({
            message: res.msg,
            type: 'error',
            plain: true
          })
          return
        }else{
          console.log("该分类下没有子分类,可删除")
          request.post("/category/delete/"+row.id).then(res => {
            if (res.code === 200){
              console.log("删除分类成功")
              deleteSuccess()
              this.load();
            }else{
              console.log("删除失败")
              ElMessage({
                message: '删除分类失败'+res.msg,
                type: 'error',
                plain: true
              })
            }
          })
        }
      })
    },
  }
})
</script>

<style scoped>

</style>