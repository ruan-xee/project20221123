<template>
  <div>
    <div class="pd-10">
      <el-input style="width: 200px;" suffix-icon="el-icon-search" placeholder="请输入角色名" v-model="queryParam.name"></el-input>
      <el-button class="ml-5" type="primary" @click="fnQueryByParam">搜索</el-button>
      <el-button class="ml-5" type="danger" @click="fnReset">重置</el-button>
      <div style="margin: 10px 0;">
        <el-button type="primary" @click="fnAddUser">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
        <template>
          <el-popconfirm title="确定删除？" confirm-button-type="danger" @confirm="fnMultiDel" class="ml-5">
            <el-button slot="reference" type="danger" >批量删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </div>
    </div>
    <el-table :data="tableData" border stripe header-cell-class-name="headerBg" row-key="id" default-expand-all @selection-change="tableSelectionChange">
      <el-table-column type="selection" width="50">
      </el-table-column>
      <el-table-column prop="id" label="ID" width="50">
      </el-table-column>
      <el-table-column prop="name" label="名称">
      </el-table-column>
      <el-table-column prop="path" label="路径">
      </el-table-column>
      <el-table-column prop="icon" label="图标">
      </el-table-column>
      <el-table-column prop="description" label="描述">
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
    <el-dialog title="菜单信息" :visible.sync="flag.dialogFormVisible" width="30%">
      <el-form label-width="80px">
        <el-form-item v-if="form.id" label="ID">
          <el-input v-model="form.id" disabled autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="路径">
          <el-input v-model="form.path" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="form.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" autocomplete="off"></el-input>
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
  name: "Menu",
  data(){
    return{
      tableData: [],
      pagination:{
        total: 0,
        pageNum: 1,
        pageSize: 10,
      },
      queryParam:{
        name:"",
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
      this.request.get("/menu/page", {
        params:{
          pageNum:this.pagination.pageNum,
          pageSize:this.pagination.pageSize,
          name:this.queryParam.name,
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
      this.queryParam.name="";
      this.fnQuery();
    },
    fnAddUser(){
      this.flag.dialogFormVisible=true;
      this.form={};
    },
    fnSaveUser(){
      if(this.form.id === undefined){
        this.request.post("/menu/add", this.form).then(res=> {
          if (res.code === "200") {
            this.$message.success("保存成功");
            this.flag.dialogFormVisible = false;
            this.fnReset();
          } else {
            this.$message.warning(res.msg);
          }
        })
      } else {
        this.request.post("/menu/update", this.form).then(res=>{
          if (res.code==="200"){
            this.$message.success("保存成功");
            this.flag.dialogFormVisible=false;
            this.fnReset();
          } else {
            this.$message.warning(res.msg);
          }
        })
      }
    },
    fnEditItem(data){
      this.form = {...data};
      this.flag.dialogFormVisible = true;
    },
    fnDelete(id){
      this.request.delete("/menu/del/"+id).then(res=>{
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
      this.request.post("/menu/del/batch",ids).then(res=>{
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
  }
}
</script>

<style scoped>
.headerBg{
  background-color: #eee !important;
}
</style>
