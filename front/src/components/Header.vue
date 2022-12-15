<template>
  <div style="font-size: 12px; line-height: 60px; display: flex;">
    <div style="flex:1; font-size: 20px">
      <span :class="collapseBtnClass" style="cursor: pointer" @click="changeAside"></span>

      <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
        <el-breadcrumb-item :to="'/'">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{currentPathName}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <el-dropdown style="width: 100px; cursor: pointer">
      <div style="display: inline-block">
        <img :src="user.portrait" alt=""
        style="width: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px">
        <span>{{user.nickname}}</span><i class="el-icon-arrow-down" style="margin-left: 10px"></i>
      </div>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>
          <router-link to="/person">个人信息</router-link>
        </el-dropdown-item>
        <el-dropdown-item>
          <span style="text-decoration: none" @click="logout">退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  data(){
    return{
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },
  computed:{
    currentPathName(){
      return this.$store.state.currentPathName;
    }
  },
  watch:{
  },
  methods:{
    changeAside(){
      this.$emit('collapse');
    },
    logout(){
      this.$router.push("/login");
      localStorage.removeItem("user");//清除缓存
      this.$message.success("退出成功！");
    },
  },
  props:{
    username: String,
    collapseBtnClass: String,
  },
}
</script>

<style scoped>

</style>
