<template>
  <div>
    <el-row>
      <el-col :span="12">
        <div id="main" style="width: 500px; height: 400px"></div>
      </el-col>
      <el-col :span="12">
        <div id="pie" style="width: 500px; height: 400px"></div>
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
      xAxis: {
        type: 'category',
        data: [],
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [],
          type: 'line'
        },
        {
          data: [],
          type: 'bar'
        }
      ]
    };
    let option2 = {
      title: {
        text: 'Referer of a Website',
        subtext: 'Fake Data',
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
          name: 'Access From',
          type: 'pie',
          radius: '50%',
          data: [
            { value: 1048, name: 'Search Engine' },
            { value: 735, name: 'Direct' },
            { value: 580, name: 'Email' },
            { value: 484, name: 'Union Ads' },
            { value: 300, name: 'Video Ads' }
          ],
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
    let chartDom = document.getElementById('main');
    let chartDom2 = document.getElementById('pie');
    let myChart = echarts.init(chartDom);
    let myChart2 = echarts.init(chartDom2);
    this.request.get("/echarts/example").then(res=>{
      console.log(res);
      if (res.code === '200'){
        option.xAxis.data = res.obj.x;
        option.series[0].data = res.obj.y;
        option.series[1].data = res.obj.y;
      }
    })
    myChart.setOption(option);
    myChart2.setOption(option2);
  },
}
</script>

<style scoped>

</style>
