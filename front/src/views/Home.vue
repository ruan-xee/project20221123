<template>
  <div>
    <el-row :gutter="10" style="margin-bottom: 40px">
      <el-col :span="6">
        <el-card style="color: #409EFF">
          <div><i class="el-icon-user-solid"></i>用户总数</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold;">
            100
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #F56C6C">
          <div><i class="el-icon-s-data"></i>销售总量</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold;">
            ￥100000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #67C23A">
          <div><i class="el-icon-s-flag"></i>收益总额</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold;">
            ￥30000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #E6A23C">
          <div><i class="el-icon-s-shop"></i>门店总数</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold;">
            45
          </div>
        </el-card>
      </el-col>
      </el-row>
    <el-row>
      <el-col :span="12">
        <div id="main" style="width: 500px; height: 450px"></div>
      </el-col>
      <el-col :span="12">
        <div id="pie" style="width: 500px; height: 450px"></div>
      </el-col>
    </el-row>


  </div>
</template>

<script>
import * as echarts from 'echarts';
export default {
  name: "Home",
  data(){
    return{
    }
  },
  mounted() {
    let option = {
      title: {
        text: '用户统计',
        subtext: '在册用户分季度统计趋势图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      xAxis: {
        type: 'category',
        data: [],
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name: 'line',
          data: [],
          type: 'line'
        },
        {
          name: 'bar',
          data: [],
          type: 'bar'
        }
      ]
    };
    let chartDom = document.getElementById('main');
    let myChart = echarts.init(chartDom);
    let option2 = {
      title: {
        text: '用户统计',
        subtext: '在册用户分季度统计比例图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          type: 'pie',
          radius: '60%',
          label:{
            normal:{
              show:true,
              position:'inner',
              textStyle:{
                fontWeight: 300,
                fontSize: 16,
                color:"#fff",
              },
              formatter:'{d}%',
            },
          },
          data: [],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };
    let chartDom2 = document.getElementById('pie');
    let myChart2 = echarts.init(chartDom2);
    this.request.get("/echarts/members").then(res=>{
      if (res.code === '200'){
        option.xAxis.data = res.obj.x;
        option.series[0].data = res.obj.y;
        option.series[1].data = res.obj.y;
        myChart.setOption(option);
        option2.series[0].data = [
          {name:"第一季度", value:res.obj.y[0]},
          {name:"第二季度", value:res.obj.y[1]},
          {name:"第三季度", value:res.obj.y[2]},
          {name:"第四季度", value:res.obj.y[3]},
        ]
        myChart2.setOption(option2);
      }
    })

  },
}
</script>

<style scoped>

</style>
