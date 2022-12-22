<template>
  <el-card style="width: 500px;">
    <el-form label-width="80px">
      <el-upload
          class="avatar-uploader"
          action="http://localhost:8088/file/upload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess">
        <img v-if="form.portrait" :src="form.portrait" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <el-form-item label="用户名">
        <el-input v-model="form.username" disabled autocomplete="off"></el-input>
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
          this.$bus.$emit('updateUsername','');
          this.$router.push("/home");
        } else {
          this.$message.warning(res.msg);
        }
      })
    },
    handleAvatarSuccess(res){
      if (res.code==="200") {
        this.form.portrait = res.obj;
      }
    }
  },

}
</script>

<style>
.avatar-uploader{
  text-align: center;
  padding-bottom: 10px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 138px;
  height: 138px;
  line-height: 138px;
  text-align: center;
}
.avatar {
  width: 138px;
  height: 138px;
  display: block;
}
</style>
