<script setup>
import Header from "../../components/Header.vue";
</script>

<template>
  <!--  头部区域-->
  <Header></Header>

  <!--侧边栏和主体-->
  <div style="display: flex">
    <!--    侧边导航栏-->
    <div style="width: 200px;min-height: calc(100vh - 62px);overflow: hidden;margin-right: 2px;background-color: white">
      <el-menu
          :default-active="$route.path"
          router
          class="el-menu-demo"
      >
        <el-menu-item index="/"><el-icon><HomeFilled /></el-icon>首页</el-menu-item>
        <el-sub-menu index="1">
          <template #title><el-icon><Menu /></el-icon>会员管理</template>
          <el-menu-item index="/user"><el-icon><UserFilled /></el-icon>会员列表</el-menu-item>
          <el-menu-item index="/addUser"><el-icon><CirclePlusFilled /></el-icon>会员添加</el-menu-item>
          <el-menu-item index="/empty"><el-icon><BellFilled /></el-icon>更多功能</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="2">
          <template #title><el-icon><Avatar /></el-icon>管理员管理</template>
          <el-menu-item index="/admin"><el-icon><Avatar /></el-icon>管理员列表</el-menu-item>
          <el-menu-item index="/addAdmin"><el-icon><CirclePlusFilled /></el-icon>管理员添加</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="3">
          <template #title><el-icon><List /></el-icon>分类管理</template>
          <el-menu-item index="/category"><el-icon><Document /></el-icon>分类列表</el-menu-item>
          <el-menu-item index="/addCategory"><el-icon><DocumentAdd /></el-icon>分类添加</el-menu-item>
          <el-menu-item index="/showCategory"><el-icon><Tickets /></el-icon>分类层级</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="4">
          <template #title><el-icon><Notebook /></el-icon>图书管理</template>
          <el-menu-item index="/book"><el-icon><Collection /></el-icon>图书列表</el-menu-item>
          <el-menu-item index="/addBook"><el-icon><DocumentAdd /></el-icon>图书添加</el-menu-item>
          <el-menu-item index="/showBook"><el-icon><Memo /></el-icon>图书详情</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="5">
          <template #title><el-icon><FolderRemove /></el-icon>借书管理</template>
          <el-menu-item index="/borrow"><el-icon><FolderOpened /></el-icon>借书列表</el-menu-item>
          <el-menu-item index="/addBorrow"><el-icon><FolderAdd /></el-icon>借书添加</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="6">
          <template #title><el-icon><Fold /></el-icon>还书管理</template>
          <el-menu-item index="/retur"><el-icon><Checked /></el-icon>还书列表</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </div>

    <!--    主体区域-->
    <div style="flex: 1;width: 0;background-color: white;padding: 10px;">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import { defineComponent } from 'vue'
export default  defineComponent({
  name:'Layout'
})
</script>

<style scoped>

</style>