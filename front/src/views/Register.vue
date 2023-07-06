<template>
  <div class="wrapper">
    <div style="margin: 100px auto; background-color:#fff; width: 450px; height: 450px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>注 册</b></div>
      <el-form ref="registForm" :model="user" label-width="110px" :rules="rules">
        <el-form-item label="用户名" prop="username" style="margin: 20px 0">
          <el-input size="medium" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname" style="margin: 20px 0">
          <el-input size="medium" prefix-icon="el-icon-user" v-model="user.nickname" autocomplete="off" :readonly="readOnly" @focus="handleReadOnly"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" style="margin: 20px 0">
          <el-input size="medium" prefix-icon="el-icon-lock" show-password v-model="user.password" autocomplete="off" :readonly="readOnly" @focus="handleReadOnly"></el-input>
        </el-form-item>
        <el-form-item label="再次输入密码" prop="comfirmPassword" style="margin: 20px 0">
          <el-input size="medium" prefix-icon="el-icon-lock" show-password v-model="user.comfirmPassword" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="role" style="margin: 20px 0">
          <el-select size="medium" v-model="user.role" style="width: 300px;" placeholder="请选择角色">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.name"
                :value="item.flag">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
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
    // 密码对比校验
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.user.comfirmPassword !== '') {
          this.$refs.registForm.validateField('comfirmPassword');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.user.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    }
    return{
      form: {},
      user: {
        username: '',
        nickname: '',
        password: '',
        role: '',
      },
      options: [],
      readOnly: true,

      // formRules
      rules: {
        username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur' },
        ],
        nickname: [
          { required: true, message: '请输入昵称', trigger: 'blur' },
          { min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur' },
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          { min: 3, max: 12, message: '长度在 3 到 12 个字符', trigger: 'blur' },
          { validator: validatePass, trigger: 'blur' },
        ],
        comfirmPassword: [
          {required: true, message: '请再次输入密码', trigger: 'blur'},
          { validator: validatePass2, message:'两次输入不同！请重试！', trigger: 'blur' },
        ],
        role: [
            {required: true, message: '请选择角色！', trigger: 'change'}
        ],
      },


    }
  },
  mounted() {
    this.getRole()
  },
  methods:{
    regist(){
      this.$refs["registForm"].validate((valid) => {
        if (valid) {
          this.request.post("/login/regist",this.user).then(data=>{
            if (data.code === '200'){
              this.$router.push("/login")
              this.$message.success("注册成功！请登录~")
            } else {
              this.$message.error(data.msg)
            }
          })
        } else {
          this.$message.error("信息填写错误！请重新正确填写！")
        }
      })

    },
    getRole(){
      this.request.post("/login/getRoleList").then(data=>{
        if (data.code === '200') {
          this.options = data.obj
        }
      })
    },
    handleReadOnly(){
      this.readOnly = false
    }
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
