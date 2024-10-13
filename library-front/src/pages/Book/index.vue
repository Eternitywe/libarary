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
          placeholder="请输入图书名称"
          clearable
      />
      <el-input
          v-model="params.bookNo"
          style="width: 240px;margin-right: 5px"
          placeholder="请输入图书标准码"
          clearable
      />
      <el-button type="primary" style="margin-right: 5px" @click="load">
        <el-icon><Search /></el-icon>搜索
      </el-button>
      <el-button type="danger" style="margin-left: 0px" @click="refresh">
        <el-icon><Refresh /></el-icon>重置
      </el-button>
    </div>
    <!--      数据表格组件-->
    <div style="margin-bottom: 20px;overflow: hidden;">
      <el-table :data="tableData" stripe border>
        <el-table-column fixed prop="id" label="主键" width="60"/>
        <el-table-column prop="name" label="图书名称" width="100"/>
        <el-table-column prop="description" label="图书简介" width="260"/>
        <el-table-column prop="publishDate" label="出版日期" width="110"/>
        <el-table-column prop="author" label="作者" width="60"/>
        <el-table-column prop="publisher" label="出版社" width="125"/>
        <el-table-column prop="categoryName" label="分类" width="110">
          <template v-slot="scope">
            <el-tag type="primary">
              {{ scope.row.categoryName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="bookNo" label="图书标准码"  width="135"/>
        <el-table-column prop="score" label="图书积分"/>
        <el-table-column prop="nums" label="图书数量"/>
        <el-table-column prop="cover" label="图书封面" width="100" >
          <template v-slot="scope">
            <el-image
                style="width: 100px; height: 100px"
                :src="scope.row.cover"
                :preview-src-list="[scope.row.cover]"
                :preview-teleported = true
            />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="255">
          <template v-slot="scope">
            <el-button type="primary" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
            <el-popconfirm
                confirm-button-text="确定"
                cancel-button-text="取消"
                title="您确定删除当前行数据嘛?"
                @confirm="handleDelete(scope.row)"
            >
              <template #reference>
                <el-button type="danger" style="margin-left: 5px" size="mini">删除</el-button>
              </template>
            </el-popconfirm>
            <el-button type="warning" style="margin-left: 5px" size="mini" @click="handleShow(scope.row)">图书详情</el-button>
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
    message: '删除图书信息成功',
    type: 'success',
    plain: true
  })
}


export default  defineComponent({
  name:'Book',
  data(){
    return{
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      total: 0,
      tableData: [],
      params: {
        pageNum: 1,
        pageSize: 2,
        name: '',
        bookNo: ''
      },
    }
  },
  created() {
    this.load();
  },
  methods:{
    load(){
      request.get("/book/page",{
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
        pageSize: 2,
        name: '',
        bookNo: ''
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
        path: '/editBook',
        query: {id: row.id}
      })
    },
    handleDelete(row){
        request.post("/book/delete/"+row.id).then(res => {
          if (res.code === 200){
            console.log("删除图书成功")
            deleteSuccess()
            this.load();
          }else{
            console.log("删除失败")
            ElMessage({
              message: '删除图书失败'+res.msg,
              type: 'error',
              plain: true
            })
          }
        })
      },
    handleShow(row){
        // console.log(row.id)
        this.$router.push({
          path: '/showBook',
          query: {id: row.id}
        })
      }
    }
})
</script>

<style scoped>

</style>