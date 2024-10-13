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
          v-model="params.username"
          style="width: 240px;margin-right: 5px"
          placeholder="请输入用户名"
          clearable
      />
      <el-input
          v-model="params.phone"
          style="width: 240px;margin-right: 5px"
          placeholder="请输入联系方式"
          clearable
      />
      <el-input
          v-model="params.email"
          style="width: 240px;margin-right: 5px"
          placeholder="请输入邮箱"
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
        <el-table-column prop="username" label="用户名" width="150" />
        <el-table-column prop="phone" label="联系方式" width="140" />
        <el-table-column prop="email" label="邮箱" width="140" />
        <el-table-column prop="createtime" label="创建时间" width="230" />
        <el-table-column prop="updatetime" label="修改时间" width="230" />
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
            <el-button type="warning" size="mini" @click="handleChangePassword(scope.row)">修改密码</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog v-model="dialogFormVisible" title="修改密码" width="500">
        <el-form :model="form"
                 ref="ruleForm"
                 :rules="rules">
          <el-form-item label="新密码" label-width="100px"  prop="newPassword">
            <el-input
                v-model="form.newPassword"
                show-password placeholder="请输入新密码"
                autocomplete="off" />
          </el-form-item>
          <el-form-item label="确认密码" label-width="100px" prop="newPasswordTwo">
            <el-input
                v-model="form.newPasswordTwo"
                show-password placeholder="请再次输入新密码"
                autocomplete="off" />
          </el-form-item>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="dialogFormVisible = false;this.form={}">Cancel</el-button>
            <el-button type="primary" @click="savePass">
              Confirm
            </el-button>
          </div>
        </template>
      </el-dialog>
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
    message: '删除管理员用户信息成功',
    type: 'success',
    plain: true
  })
}


export default  defineComponent({
  name:'Admin',
  data(){
    return{
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      dialogFormVisible: false,
      total: 0,
      tableData: [],
      form:{
        newPassword: '',
        newPasswordTwo: ''
      },
      params: {
        pageNum: 1,
        pageSize: 10,
        username: '',
        phone: '',
        email: ''
      },
      rules:{
        newPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        newPasswordTwo: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  created() {
    this.load();
  },
  methods:{
    load(){
      request.get("/admin/page",{
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
        path: '/editAdmin',
        query: {id: row.id}
      })
    },
    handleDelete(row){
      request.post("/admin/delete/"+row.id).then(res => {
        if (res.code === 200){
          console.log("删除成功")
          deleteSuccess()
          this.load();
        }else{
          console.log("删除失败")
          ElMessage({
            message: '删除管理员用户失败'+res.msg,
            type: 'error',
            plain: true
          })
        }
      })
    },
    handleChangePassword(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true;//开启修改密码对话框
    },
    savePass(){
      if (this.form.newPassword !== this.form.newPasswordTwo){
        ElMessage({
          message: '两次输入密码不一致,请重新输入密码!',
          type: 'error',
          plain: true
        })
        this.form={}
      }else{
        this.$refs["ruleForm"].validate((valid) =>{
          if (valid){
            request.post("/admin/changePassword",this.form).then(res => {
              if (res.code === 200){
                if (this.form.id === this.admin.id){
                  Cookies.remove('admin')
                  this.$router.push('/login')
                }
                ElMessage({
                  message: '修改密码成功',
                  type: 'success',
                  plain: true
                })
                this.dialogFormVisible = false;//关闭修改密码对话框
                this.load();
                this.form={}
              }else{
                ElMessage({
                  message: '修改密码失败'+res.msg,
                  type: 'error',
                  plain: true
                })
              }
            })
          }
        })
      }
    },
    changeStatus(row){
      if (this.admin.id===row.id){
        ElMessage({
          message: '当前操作不合法,请勿进行!',
          type: 'error',
          plain: true
        })
        row.status = true
        return
      }else {
        request.post("/admin/changeStatus/"+row.id).then(res => {
          if (res.code === 200){
            console.log("修改管理员用户状态成功")
            ElMessage({
              message: '修改管理员用户状态成功',
              type: 'success',
              plain: true
            })
            this.load();
          }else{
            console.log("修改管理员用户状态失败")
            ElMessage({
              message: '修改管理员用户状态失败'+res.msg,
              type: 'error',
              plain: true
            })
          }
        })
      }

    }
  }
})
</script>

<style scoped>

</style>