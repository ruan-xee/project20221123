<template>
  <el-container style="min-height: 100vh;">
    <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246); box-shadow: 2px 0 6px rgb(0 21 65)">
      <el-menu style="min-height: 100%;overflow-x: hidden"
               :default-openeds="['1']"
               background-color="rgb(48,65,86)"
               text-color="#fff"
               active-text-color="#ffd04b"
               :collapse-transition="false"
               :collapse="isCollapse"
      >
        <div style="height: 60px; line-height: 60px; text-align: center">
          <img src="../assets/logo.png" style="width: 20px; position: relative; top: 5px; margin-right: 5px;">
          <b style="color: white" v-show="!isCollapse">后台管理系统</b>
        </div>
        <el-submenu index="1">
          <template slot="title"><i class="el-icon-message"></i>
            <span>导航一</span>
          </template>
          <el-menu-item-group>
            <template slot="title">分组一</template>
            <el-menu-item index="1-1">选项1</el-menu-item>
            <el-menu-item index="1-2">选项2</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="分组2">
            <el-menu-item index="1-3">选项3</el-menu-item>
          </el-menu-item-group>
          <el-submenu index="1-4">
            <template slot="title">选项4</template>
            <el-menu-item index="1-4-1">选项4-1</el-menu-item>
          </el-submenu>
        </el-submenu>
        <el-submenu index="2">
          <template slot="title"><i class="el-icon-menu"></i>
            <span>导航二</span>
          </template>
          <el-menu-item-group>
            <template slot="title">分组一</template>
            <el-menu-item index="2-1">选项1</el-menu-item>
            <el-menu-item index="2-2">选项2</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="分组2">
            <el-menu-item index="2-3">选项3</el-menu-item>
          </el-menu-item-group>
          <el-submenu index="2-4">
            <template slot="title">选项4</template>
            <el-menu-item index="2-4-1">选项4-1</el-menu-item>
          </el-submenu>
        </el-submenu>
        <el-submenu index="3">
          <template slot="title"><i class="el-icon-setting"></i>
            <span>导航三</span>
          </template>
          <el-menu-item-group>
            <template slot="title">分组一</template>
            <el-menu-item index="3-1">选项1</el-menu-item>
            <el-menu-item index="3-2">选项2</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="分组2">
            <el-menu-item index="3-3">选项3</el-menu-item>
          </el-menu-item-group>
          <el-submenu index="3-4">
            <template slot="title">选项4</template>
            <el-menu-item index="3-4-1">选项4-1</el-menu-item>
          </el-submenu>
        </el-submenu>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header style="font-size: 12px; border-bottom: 1px solid #ccc; line-height: 60px; display: flex;">
        <div style="flex:1; font-size: 20px">
          <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
        </div>
        <el-dropdown style="width: 80px; cursor: pointer">
          <span>王小虎</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>个人信息</el-dropdown-item>
            <el-dropdown-item>退出</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

      </el-header>

      <el-main>
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>用户管理</el-breadcrumb-item>
        </el-breadcrumb>
        <div class="pd-10">
          <el-input style="width: 200px;" suffix-icon="el-icon-search" placeholder="请输入用户名" v-model="queryParam.username"></el-input>
          <el-input style="width: 200px;" suffix-icon="el-icon-message" placeholder="请输入邮箱" v-model="queryParam.email" class="ml-5"></el-input>
          <el-input style="width: 200px;" suffix-icon="el-icon-position" placeholder="请输入地址" v-model="queryParam.address" class="ml-5"></el-input>
          <el-button class="ml-5" type="primary" @click="fnQueryByParam">搜索</el-button>
          <el-button class="ml-5" type="danger" @click="fnReset">重置</el-button>
          <div style="margin: 10px 0;">
            <el-button type="primary" @click="fnAddUser">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
            <el-button type="danger">批量删除 <i class="el-icon-remove-outline"></i></el-button>
            <el-button type="primary">导入 <i class="el-icon-bottom"></i></el-button>
            <el-button type="primary">导出 <i class="el-icon-top"></i></el-button>
          </div>
        </div>
        <el-table :data="tableData" border stripe header-cell-class-name="headerBg">
          <el-table-column prop="id" label="ID" width="80">
          </el-table-column>
          <el-table-column prop="username" label="用户名">
          </el-table-column>
          <el-table-column prop="nickname" label="昵称">
          </el-table-column>
          <el-table-column prop="address" label="地址">
          </el-table-column>
          <el-table-column prop="email" label="邮箱">
          </el-table-column>
          <el-table-column prop="phone" label="电话">
          </el-table-column>
          <el-table-column prop="createTime" label="创建日期">
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button type="success" @click="fnEditItem(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
              <template>
                <el-popconfirm title="确定删除？" confirm-button-type="danger" @confirm="fnDelete(scope.row.id)">
                  <el-button slot="reference" type="danger" >删除 <i class="el-icon-remove"></i></el-button>
                </el-popconfirm>
              </template>
            </template>
          </el-table-column>
        </el-table>
        <div class="pd-10">
          <el-pagination
              :current-page="pagination.pageNum"
              :page-sizes="[1, 2, 3, 4]"
              :page-size="pagination.pageSize"
              @size-change="handlerSizeChanged"
              @current-change="handlerCurrentChanged"
              layout="total, sizes, prev, pager, next, jumper"
              :total="pagination.total">
          </el-pagination>
        </div>
        <!-- Form -->
        <el-dialog title="用户信息" :visible.sync="flag.dialogFormVisible" width="30%">
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
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="flag.dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="fnSaveUser">确 定</el-button>
          </div>
        </el-dialog>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>


export default {
  name: 'HomeView',
  data() {
    return {
      tableData: [],
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,//收缩侧边栏标志
      sideWidth: 200,//侧边栏宽度
      pagination:{
        total: 0,
        pageNum: 1,
        pageSize: 2,
      },
      queryParam:{
        username:"",
        address:"",
        email:"",
      },
      flag:{
        dialogFormVisible:false,
      },
      form:{

      },
    }
  },
  created() {
    //请求分页查询数据
    this.fnQuery();
  },
  methods: {
    collapse(){
      this.isCollapse = !this.isCollapse
      if (this.isCollapse){
        this.sideWidth = 64;
        this.collapseBtnClass = 'el-icon-s-unfold';
      } else {
        this.sideWidth = 200;
        this.collapseBtnClass = 'el-icon-s-fold';
      }
    },
    fnQuery(){
      //请求分页查询数据
      // fetch("http://localhost:8088/user/page?pageNum="+this.pagination.pageNum+"&pageSize="+this.pagination.pageSize+"&username="+this.queryParam.username).then(res=>res.json()).then(res=>{
      //   this.tableData = res.users;
      //   this.pagination.total = res.total;
      // })
      this.request.get("/user/page", {
        params:{
          pageNum:this.pagination.pageNum,
          pageSize:this.pagination.pageSize,
          username:this.queryParam.username,
          address:this.queryParam.address,
          email:this.queryParam.email,
          createTime:this.queryParam.createTime,
        }
      }).then(res=>{
        this.tableData = res.records;
        this.pagination.total = res.total;})
    },
    fnQueryByParam(){
      this.pagination.pageNum = 1;
      this.fnQuery();
    },
    handlerSizeChanged(pageSize){
      this.pagination.pageSize = pageSize;
      this.fnQuery();
    },
    handlerCurrentChanged(pageNum){
      this.pagination.pageNum = pageNum;
      this.fnQuery();
    },
    fnReset(){
      this.queryParam.email="";
      this.queryParam.address="";
      this.queryParam.username="";
      this.fnQuery();
    },
    fnAddUser(){
      this.flag.dialogFormVisible=true;
      this.form={};
    },
    fnSaveUser(){
      this.request.post("/user/add", this.form).then(res=>{
        if (res){
          this.$message.success("保存成功");
          this.flag.dialogFormVisible=false;
          this.fnReset();
        } else {
          this.$message.warning("保存失败");
        }
      })
    },
    fnEditItem(data){
      console.log(data);
      this.form = {...data};
      // this.form.id = data.id;
      // this.form.username = data.username;
      // this.form.nickname = data.nickname;
      // this.form.email = data.email;
      // this.form.address = data.address;
      // this.form.phone = data.phone;
      this.flag.dialogFormVisible = true;
    },
    fnDelete(id){
      console.log(id);
      this.request.post("/user/del",id).then(res=>{
        if (res){
          this.$message.success("删除成功");
          this.fnReset();
        }else {
          this.$message.warning("删除失败");
        }
      })
    }
  }
}
</script>

<style>
.headerBg{
  background-color: #eee !important;
}
</style>
