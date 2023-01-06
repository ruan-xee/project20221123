<template>
  <el-menu style="min-height: 100%;overflow-x: hidden"
           :default-openeds="opens"
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

    <div v-for="item in menus" :key="item.id">
      <div v-if="item.path">
        <el-menu-item :index="item.path">
          <i :class="item.icon"></i>
          <span slot="title">{{item.name}}</span>
        </el-menu-item>
      </div>
      <div v-else>
        <el-submenu :index="item.id+''">
          <template slot="title">
            <i :class="item.icon"></i>
            <span slot="title">{{ item.name }}</span>
          </template>
          <div v-for="child in item.children" :key="child.id">
            <el-menu-item :index="child.path">
              <i :class="child.icon"></i>
              <span slot="title">{{child.name}}</span>
            </el-menu-item>
          </div>
        </el-submenu>
      </div>
    </div>

    <!--<el-menu-item index="/">-->
    <!--  <i class="el-icon-house"></i>-->
    <!--  <span slot="title">主页</span>-->
    <!--</el-menu-item>-->
    <!--<el-submenu index="2">-->
    <!--  <template slot="title"><i class="el-icon-setting"></i>-->
    <!--    <span>管理系统</span>-->
    <!--  </template>-->
    <!--  <el-menu-item index="/user">-->
    <!--    <i class="el-icon-user"></i><span>用户管理</span>-->
    <!--  </el-menu-item>-->
    <!--  <el-menu-item index="/file">-->
    <!--    <i class="el-icon-folder"></i><span>文件管理</span>-->
    <!--  </el-menu-item>-->
    <!--  <el-menu-item index="/role">-->
    <!--    <i class="el-icon-key"></i><span>角色管理</span>-->
    <!--  </el-menu-item>-->
    <!--  <el-menu-item index="/menu">-->
    <!--    <i class="el-icon-menu"></i><span>菜单管理</span>-->
    <!--  </el-menu-item>-->
    <!--</el-submenu>-->
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  data(){
    return{
      menus: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")) : [],
      opens: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")).map(v=>v.id+'') : [],
    }
  },
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
