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
    <el-table :data="tableData" border stripe header-cell-class-name="headerBg" @selection-change="tableSelectionChange">
      <el-table-column type="selection" width="50">
      </el-table-column>
      <el-table-column prop="id" label="ID" width="50">
      </el-table-column>
      <el-table-column prop="name" label="用户名">
      </el-table-column>
      <el-table-column prop="flag" label="唯一标识">
      </el-table-column>
      <el-table-column prop="description" label="描述">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="info" @click="fnSelectMenu(scope.row)">分配菜单 <i class="el-icon-menu"></i></el-button>
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
    <el-dialog title="角色信息" :visible.sync="flag.dialogFormVisible" width="30%">
      <el-form label-width="80px">
        <el-form-item v-if="form.id" label="ID">
          <el-input v-model="form.id" disabled autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="唯一标识">
          <el-input v-model="form.flag" autocomplete="off"></el-input>
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

    <!-- Form -->
    <el-dialog title="菜单分配" :visible.sync="flag.menuDialogVisible" width="20%">
      <el-tree
          :props="props"
          :data="menuData"
          show-checkbox
          node-key="id"
          ref="tree"
          :default-expanded-keys="tree.expands"
          :default-checked-keys="tree.checks">
        <span class="custom-tree-node" slot-scope="{node, data}">
          <span><i :class="data.icon"></i> {{data.name}}</span>
        </span>
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeDialog">取 消</el-button>
        <el-button type="primary" @click="setRole2Menu">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Role",
  data(){
    return{
      tableData: [],
      menuData:[],
      props:{
        label: 'name'
      },
      pagination:{
        total: 0,
        pageNum: 1,
        pageSize: 10,
      },
      queryParam:{
        name:"",
      },
      tree:{
        expands:[],
        checks:[],
      },
      flag:{
        dialogFormVisible:false,
        menuDialogVisible:false,
      },
      form:{
        //表单数据
      },
      tableSelect:[],
      roleId: 0,
      roleFlag: '',
    }
  },
  created() {
    //请求分页查询数据
    this.fnQuery();
  },
  methods:{
    fnQuery(){
      this.request.get("/role/page", {
        params:{
          pageNum:this.pagination.pageNum,
          pageSize:this.pagination.pageSize,
          name:this.queryParam.name,
        }
      }).then(res=>{
        if (res.code === "200"){
          this.tableData = res.obj.records;
          this.pagination.total = res.obj.total;
        }else if (res.code === '401'){
          this.$router.push("/login");
        }})
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
      if(this.form.id !== undefined){
        this.request.post("/role/update", this.form).then(res=> {
          if (res.code === "200") {
            this.$message.success("保存成功");
            this.flag.dialogFormVisible = false;
            this.fnReset();
          } else {
            this.$message.warning(res.msg);
          }
        })
      } else {
        this.request.post("/role/add", this.form).then(res=>{
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
      this.request.delete("/role/del/"+id).then(res=>{
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
      this.request.post("/role/del/batch",ids).then(res=>{
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
    closeDialog(){
      this.tree.checks = [];
      this.flag.menuDialogVisible = false;
    },
    fnSelectMenu(role){
      this.roleId = role.id;
      this.roleFlag = role.flag;
      //请求菜单数据
      this.request.get("/menu/query").then(res=>{
        if (res.code==="200"){
          this.menuData = res.obj;
          this.tree.expands = this.menuData.map(value => value.id)
        }})

      //请求勾选项数据
      this.request.get("/r2m/"+this.roleId).then(res=>{
        if (res.code==="200"){
          this.tree.checks = res.obj;
          this.request.get("/menu/ids").then(res => {
            const ids = res.obj;
            ids.forEach(id=>{
              if (!this.tree.checks.includes(id)){
              //   this.$nextTick(
              //       ()=>{this.$refs.tree.setChecked(id, false);}
              // )
                this.$refs.tree.setChecked(id, false);
              }
            })
          })
        }})
      this.flag.menuDialogVisible = true;
    },
    setRole2Menu(){
      this.request.post("/r2m/"+this.roleId, this.$refs.tree.getCheckedKeys()).then(res=>{
        if (res.code==='200'){
          this.$message.success("修改成功~");
          this.tree.checks = [];
          this.flag.menuDialogVisible = false;
          if (this.roleFlag === 'admin') {
            this.$store.commit("logout");
          }
        } else {
          this.$message.error(res.msg);
        }
      })
    },
  }
}
</script>

<style scoped>
.headerBg{
  background-color: #eee !important;
}
</style>
