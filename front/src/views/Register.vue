<template>
  <div class="wrapper">
    <div style="margin: 100px auto; background-color:#fff; width: 350px; height: 400px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>注 册</b></div>
      <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username" placeholder="用户名"></el-input>
      <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.nickname" placeholder="昵称"></el-input>
      <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.password" placeholder="密码"></el-input>
      <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.comfirmPassword" placeholder="再次输入密码"></el-input>
      <div style="margin: 10px 0; text-align: right">
        <el-button type="primary" size="small" autocomplete="off" @click="regist">注册</el-button>
        <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/login')">返回登录</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data(){
    return{
      user: {}
    }
  },
  methods:{
    regist(){
      if (this.user.username === null || this.user.username === ""){
        this.$message.error("请输入用户名！");
        return;
      }
      if (this.user.nickname === null || this.user.nickname === ""){
        this.$message.error("请输入昵称！");
        return;
      }
      if (this.user.password === null || this.user.password === ""){
        this.$message.error("请输入密码！");
        return;
      }
      if (this.user.password !== this.user.comfirmPassword){
        this.$message.error("两次输入的密码不相同，请重试！");
        return;
      }
      this.request.post("/login/register",this.user).then(data=>{
        if (data.code==="200"){
          localStorage.setItem("user", JSON.stringify(data.obj));//存储用户信息到浏览器
          this.$router.push("/home");
          this.$message.success("欢迎回来！"+data.obj.nickname);
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
