<template>
  <el-card style="width: 500px;">
    <el-form label-width="80px">
      <el-form-item label="用户名">
        <el-input v-model="form.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="form.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话号码">
        <el-input v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="fnSaveUser">确 定</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
export default {
  name: "Person",
  data(){
    return{
      form: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },
  created() {
  },
  methods:{
    fnSaveUser(){
      this.request.post("/user/updateAndBackUser", this.form).then(res=>{
        if (res.code === "200"){
          this.$message.success("保存成功");
          localStorage.setItem("user", JSON.stringify(res.obj));//存储用户信息到浏览器
          this.$router.push("/home");
        } else {
          this.$message.warning(res.msg);
        }
      })
    },
  },
}
</script>

<style scoped>

</style>