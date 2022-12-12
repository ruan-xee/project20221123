<template>
  <el-container style="min-height: 100vh;">
    <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246); box-shadow: 2px 0 6px rgb(0 21 65)">
      <Aside :isCollapse="isCollapse"></Aside>
    </el-aside>

    <el-container>
      <el-header style=" border-bottom: 1px solid #ccc;">
        <Header :username="'王小虎'" :collapseBtnClass="collapseBtnClass" @collapse="collapse"/>
      </el-header>

      <el-main>
        <!-- 表示当前页面的子路由会在router-view中展示 -->
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>


import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: 'Manage',

  data() {
    return {

      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,//收缩侧边栏标志
      sideWidth: 200,//侧边栏宽度
    }
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
  },
  components:{
    Aside,Header
  }
}
</script>

<style>

</style>
