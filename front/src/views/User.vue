<template>
  <div>
    <div class="pd-10">
      <el-input style="width: 200px;" suffix-icon="el-icon-search" placeholder="请输入用户名" v-model="queryParam.username"></el-input>
      <el-input style="width: 200px;" suffix-icon="el-icon-message" placeholder="请输入邮箱" v-model="queryParam.email" class="ml-5"></el-input>
      <el-input style="width: 200px;" suffix-icon="el-icon-position" placeholder="请输入地址" v-model="queryParam.address" class="ml-5"></el-input>
      <el-button class="ml-5" type="primary" @click="fnQueryByParam">搜索</el-button>
      <el-button class="ml-5" type="danger" @click="fnReset">重置</el-button>
      <div style="margin: 10px 0;">
        <el-button type="primary" @click="fnAddUser">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
        <template>
          <el-popconfirm title="确定删除？" confirm-button-type="danger" @confirm="fnMultiDel" class="ml-5">
            <el-button slot="reference" type="danger" >批量删除<i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
        <!--            <el-button type="danger" @click="fnMultiDel">批量删除 <i class="el-icon-remove-outline"></i></el-button>-->
        <el-upload action="http://localhost:8088/user/import" :show-file-list="false" accept="xlsx" :onSuccess="fnImportSuccess" style="display: inline-block">
          <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
        </el-upload>
        <el-button type="primary" @click="fnExport" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
      </div>
    </div>
    <el-table :data="tableData" border stripe header-cell-class-name="headerBg" @selection-change="tableSelectionChange">
      <el-table-column type="selection" width="55">
      </el-table-column>
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
            <el-popconfirm title="确定删除？" confirm-button-type="danger" @confirm="fnDelete(scope.row.id)" class="ml-5">
              <el-button slot="reference" type="danger" >删除 <i class="el-icon-remove"></i></el-button>
            </el-popconfirm>
          </template>
        </template>
      </el-table-column>
    </el-table>
    <div class="pd-10">
      <el-pagination
          :current-page="pagination.pageNum"
          :page-sizes="[5, 10, 15, 20]"
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
  </div>
</template>

<script>
export default {
  name: "User",
  data(){
    return{
      tableData: [],
      pagination:{
        total: 0,
        pageNum: 1,
        pageSize: 10,
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
        //表单数据
      },
      tableSelect:[],
    }
  },
  created() {
    //请求分页查询数据
    this.fnQuery();
  },
  methods:{
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
        this.tableData = res.obj.records;
        this.pagination.total = res.obj.total;})
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
        console.log(res);
        if (res.code==="200"){
          this.$message.success("保存成功");
          this.flag.dialogFormVisible=false;
          this.fnReset();
        } else {
          this.$message.warning(res.msg);
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
      this.request.delete("/user/del/"+id).then(res=>{
        console.log(res);
        if (res.code==="200"){
          this.$message.success("删除成功");
          this.fnReset();
        }else {
          this.$message.warning(res.msg);
        }
      })
    },
    fnMultiDel(){
      let ids = this.tableSelect.map(v=>v.id)
      console.log(ids);
      this.request.post("/user/del/batch",ids).then(res=>{
        console.log(res);
        if (res.code==="200"){
          this.$message.success("批量删除成功");
          this.fnReset();
        }else {
          this.$message.warning(res.msg);
        }
      })
    },
    tableSelectionChange(val){
      this.tableSelect = val;
    },
    fnExport(){
      window.open("http://localhost:8088/user/export");
    },
    fnImportSuccess(){
      this.fnReset();
      this.$message.success("导入成功！");
    }
  }
}
</script>

<style scoped>
.headerBg{
  background-color: #eee !important;
}
</style>
