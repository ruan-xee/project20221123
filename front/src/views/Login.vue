<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color:#fff; width: 350px; height: 300px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>登 录</b></div>
      <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username" @keyup.enter.native="login"></el-input>
      <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.password" @keyup.enter.native="login"></el-input>
      <div style="margin: 10px 0; text-align: right">
        <el-button type="primary" size="small" autocomplete="off" @click="login">登录</el-button>
        <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/register')">注册</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import {setRoutes} from "@/router";

export default {
  name: "Login",
  data(){
    return{
      user: {}
    }
  },
  methods:{
    login(){
      if (this.user.username === null || this.user.username === ""){
        this.$message.error("请输入用户名！");
        return;
      }
      if (this.user.password === null || this.user.password === ""){
        this.$message.error("请输入密码！");
        return;
      }
      this.request.post("/login/into",this.user).then(data=>{
        if (data.code==="200"){
          localStorage.setItem("user", JSON.stringify(data.obj.user));//存储用户信息到浏览器
          localStorage.setItem("token", JSON.stringify(data.obj.token));//存储用户信息到浏览器
          localStorage.setItem("menus", JSON.stringify(data.obj.menus));//存储用户所能管理的菜单到浏览器
          //动态设置当前用户的路由
          setRoutes();
          this.$router.push("/");
          this.$message.success("欢迎回来！"+data.obj.user.nickname);
        } else {
          this.$message.error(data.msg);
        }
      })
    },
  }
}
</script>

<style scoped>
  .wrapper{
    height: 100vh;
    background-image: linear-gradient(to bottom right, #FC4668, #3F5EF8);
    overflow: hidden;
  }
</style>
