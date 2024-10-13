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
          placeholder="请输入名称"
          clearable
      />
      <el-input
          v-model="params.phone"
          style="width: 240px;margin-right: 5px"
          placeholder="请输入联系方式"
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
    <div>
      <el-table :data="tableData" stripe border style="margin-bottom: 20px">
        <el-table-column fixed prop="id" label="主键" width="80" />
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column prop="username" label="用户名"/>
        <el-table-column prop="age" label="年龄" width="80" />
        <el-table-column prop="sex" label="性别" width="80" />
        <el-table-column prop="phone" label="联系方式" width="155" />
        <el-table-column prop="address" label="地址" width="260" />
        <el-table-column prop="account" label="账户积分"/>
        <el-table-column label="状态" width="100">
          <template v-slot="scope">
            <el-switch
                v-model="scope.row.status"
                inline-prompt
                @change = "changeStatus(scope.row)"
                active-text="禁用"
                inactive-text="启用"
                style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                size="large"
            />
          </template>
        </el-table-column>
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
            <el-button type="warning" size="mini" @click="handlePay(scope.row)">充值积分</el-button>
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

    <el-dialog v-model="dialogFormVisible" title="充值积分" width="500">
      <el-form
          ref="ruleForm"
          :rules="rules"
          :model="form">
        <el-form-item label="充值账户名" label-width="100px">
          <el-input v-model="form.name" autocomplete="off"  disabled/>
        </el-form-item>
        <el-form-item label="账户余额" label-width="100px">
          <el-input v-model="form.account" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item label="充值积分" label-width="100px" prop="score">
          <el-input v-model="form.score" autocomplete="off"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="handleCancel">取消</el-button>
          <el-button type="primary" @click="addAccount">
            提交
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { defineComponent } from 'vue'
import request from '../../utils/request'
import {ElMessage} from "element-plus";
import { ElNotification } from 'element-plus'


const deleteSuccess = () => {
  ElMessage({
    message: '删除用户信息成功',
    type: 'success',
    plain: true
  })
}
let checkAccount = (rule, value, callback) => {
  value = parseInt(value);
  if (value < 20){
    callback(new Error('充值积分不能小于20'))
  }else{
    callback()
  }
}



export default  defineComponent({
  name:'User',
  data(){
    return{
      form:{},
      dialogFormVisible: false,
      total: 0,
      tableData: [],
      params: {
        pageNum: 1,
        pageSize: 10,
        name: '',
        phone: ''
      },
      rules:{
        score: [
          { required: true, message: '请输入充值积分', trigger: 'blur' },
          { validator: checkAccount, trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.load();
  },
  methods:{
    load(){
      request.get("/user/page",{
        params: this.params
      }).then(res => {
        console.log(res)
        if (res.code === 200){
          this.tableData = res.data.list;
          console.log("===================")
          console.log(this.tableData)
          this.total = parseInt(res.data.total);
          console.log(this.total)
        }
      })
    },
    refresh(){
      this.params= {
        pageNum: 1,
        pageSize: 10,
        name: '',
        phone: ''
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
        path: '/editUser',
        query: {id: row.id}
      })
    },
    handleDelete(row){
      request.post("/user/delete/"+row.id).then(res => {
        if (res.code === 200){
          console.log("删除成功")
          deleteSuccess()
          this.load();
        }else{
          console.log("删除失败")
          ElMessage({
            message: '删除用户失败'+res.msg,
            type: 'error',
            plain: true
          })
        }
      })
    },
    handlePay(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true;//开启充值积分对话框
    },
    handleCancel(){
      this.dialogFormVisible = false;//关闭充值积分对话框
      this.form={}
    },
    addAccount(){
      this.$refs["ruleForm"].validate((valid) =>{
        if (valid){
          request.post("/user/account",this.form).then(res => {
            if (res.code === 200){
              console.log("充值积分成功")
              ElNotification({
                title: '充值积分',
                message: '充值积分成功!!!',
                type: 'success',
              })
              this.dialogFormVisible = false;//关闭充值积分对话框
              this.form={}
              this.load();
            }else{
              console.log("充值积分失败")
              ElMessage({
                message: '充值积分失败'+res.msg,
                type: 'error',
               plain: true
              })
            }
          })
        }
      })
    },
    changeStatus(row){
        request.post("/user/changeStatus/"+row.id).then(res => {
          if (res.code === 200){
            console.log("修改用户状态成功")
            ElMessage({
              message: '修改用户状态成功',
              type: 'success',
              plain: true
            })
            this.load();
          }else{
            console.log("修改用户状态失败")
            ElMessage({
              message: '修改用户状态失败'+res.msg,
              type: 'error',
              plain: true
            })
          }
        })
    }
  }
})
</script>

<style scoped>

</style>