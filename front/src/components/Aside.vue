<template>
  <el-menu style="min-height: 100%;overflow-x: hidden"
           :default-openeds="['1']"
           background-color="rgb(48,65,86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
  >
    <div style="height: 60px; line-height: 60px; text-align: center">
      <img src="../assets/logo.png" style="width: 20px; position: relative; top: 5px; margin-right: 5px;">
      <b style="color: white" v-show="!isCollapse">后台管理系统</b>
    </div>
    <el-menu-item index="/">
      <i class="el-icon-house"></i>
      <span slot="title">主页</span>
    </el-menu-item>
    <el-submenu index="2">
      <template slot="title"><i class="el-icon-setting"></i>
        <span>管理系统</span>
      </template>
      <el-menu-item index="/user">
        <i class="el-icon-user"></i><span>用户管理</span>
      </el-menu-item>
      <el-menu-item index="2-2">
        <i class="el-icon-key"></i><span>权限管理</span>
      </el-menu-item>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props:{
    isCollapse: Boolean,
  },
  methods:{
    handleSelect(){
      this.$route.fullPath.split("/")
    }
  }
}
</script>

<style scoped>

</style>