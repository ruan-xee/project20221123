<template>
  <div>
    <div style="margin: 10px 0;">
      <el-input style="width: 200px;" suffix-icon="el-icon-tickets" placeholder="请输入文件名" v-model="queryParam.name" class="ml-5"></el-input>
      <el-button class="ml-5" type="primary" @click="fnQueryByParam">搜索</el-button>
      <el-upload action="http://localhost:8088/file/upload" :show-file-list="false" :onSuccess="fnImportSuccess" style="display: inline-block">
        <el-button type="primary" class="ml-5">上传文件(不超过1M) <i class="el-icon-top"></i></el-button>
      </el-upload>
      <template>
        <el-popconfirm title="确定删除？" confirm-button-type="danger" @confirm="fnMultiDel" class="ml-5">
          <el-button slot="reference" type="danger" >批量删除<i class="el-icon-remove-outline"></i></el-button>
        </el-popconfirm>
      </template>
    </div>
    <el-table :data="tableData" border stripe header-cell-class-name="headerBg" @selection-change="tableSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="name" label="文件名称"></el-table-column>
      <el-table-column prop="type" label="文件类型"></el-table-column>
      <el-table-column prop="size" label="文件大小(KB)"></el-table-column>
      <el-table-column prop="url" label="下载">
        <template slot-scope="scope">
          <el-button v-show="scope.row.enable" type="primary" @click="download(scope.row)">下载</el-button>
          <el-button v-show="!scope.row.enable" type="info" @click="download(scope.row)">下载</el-button>
        </template>
      </el-table-column>
      <el-table-column label="允许下载">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc" @change="changeEnable(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
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
  </div>
</template>

<script>
export default {
  name: "File",
  data(){
    return{
      tableData: [],
      pagination:{
        total: 0,
        pageNum: 1,
        pageSize: 10,
      },
      queryParam: {
        name:"",
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
      this.request.get("/file/page", {
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
    fnDelete(id){
      this.request.post("/file/del", id).then(res=>{
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
      this.request.post("/file/del/batch",ids).then(res=>{
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
    fnImportSuccess(){
      this.fnReset();
      this.$message.success("导入成功！");
    },
    download(row){
      if (row.enable) {
        window.open(row.url);
      } else {
        this.$message.warning("不允许下载该资源！");
      }
    },
    changeEnable(row){
      this.request.post("/file/update", row).then(res=>{
        if (res.code==="200"){
          this.$message.success("操作成功！");
          this.fnReset();
        }else {
          this.$message.warning(res.msg);
        }
      })

    }
  }
}
</script>

<style scoped>

</style>
