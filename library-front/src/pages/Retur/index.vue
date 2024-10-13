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
          v-model="params.bookName"
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
      <el-input
          v-model="params.userName"
          style="width: 240px;margin-right: 5px"
          placeholder="请输入用户名"
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
        <el-table-column prop="bookName" label="图书名称" />
        <el-table-column prop="bookNo" label="图书标准码"/>
        <el-table-column prop="userNo" label="会员码"/>
        <el-table-column prop="userName" label="用户名"/>
        <el-table-column prop="userPhone" label="联系方式"/>
        <el-table-column prop="createtime" label="借出时间"/>
        <el-table-column prop="status" label="借书状态"/>
        <el-table-column prop="days" label="借书天数"/>
        <el-table-column prop="returnDate" label="归还日期"/>
        <el-table-column prop="realDate" label="实际归还日期"/>
        <el-table-column prop="score" label="借书积分" />
        <el-table-column label="操作">
          <template v-slot="scope">
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
    message: '删除还书信息成功',
    type: 'success',
    plain: true
  })
}


export default  defineComponent({
  name:'Retur',
  data(){
    return{
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      total: 0,
      tableData: [],
      params: {
        pageNum: 1,
        pageSize: 5,
        bookName: '',
        bookNo: '',
        userName: ''
      },
    }
  },
  created() {
    this.load();
  },
  methods:{
    load(){
      request.get("/retur/page",{
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
        pageSize: 5,
        bookName: '',
        bookNo: '',
        userName: ''
      };
      this.load();
    },
    handleCurrentChange(pageNum){
      //点击分页按钮实现分页数据显示
      // console.log(pageNum)
      this.params.pageNum = pageNum;
      this.load();
    },
    handleDelete(row){
        request.post("/retur/delete/"+row.id).then(res => {
          if (res.code === 200){
            console.log("删除还书记录成功")
            deleteSuccess()
            this.load();
          }else{
            console.log("删除还书记录失败")
            ElMessage({
              message: '删除还书记录失败'+res.msg,
              type: 'error',
              plain: true
            })
          }
        })
      },
  }
})
</script>

<style scoped>

</style>