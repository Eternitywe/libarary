<script setup>

</script>

<template>
  <div>
    <div style="margin: 20px 0">
      <el-select v-model="timeRange" placeholder="Select" style="width: 240px" @change="load">
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        />
      </el-select>
    </div>
    <el-card>
      <div id="line" style="width: 100%;height:400px;"></div>
    </el-card>
  </div>
</template>

<script >
import {defineComponent, reactive} from 'vue'
import Cookies from "js-cookie";
import * as echarts from 'echarts';
import request from "../../utils/request.js";

const option = {
  title: {
    text: '图书借还情况'
  },
  tooltip: {
    show: true,
    trigger: 'axis'
  },
  legend: {
    show: true,
    data: ['借书数量', '还书数量']
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  toolbox: {
    feature: {
      saveAsImage: {}
    }
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: []
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '借书数量',
      type: 'line',
      stack: 'Total',
      data: []
    },
    {
      name: '还书数量',
      type: 'line',
      stack: 'Total',
      data: []
    }
  ]
};

const charts  = { chart: null }

export default  defineComponent({
  name:'Home',
  data(){
    return{
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      timeRange: 'week',
      lineBox: null,
      options: [
        {
          value: 'week',
          label: '最近一周'
        },
        {
          value: 'month',
          label: '最近一月'
        },
        {
          value: 'month2',
          label: '最近两月'
        }
      ]
    }
  },
  created(){
  },
  mounted(){
    this.load()
  },
  methods:{
    load(){
      charts.chart = echarts.init(document.getElementById('line'));//初始化echarts容器
      request.get('/borrow/lineCharts/'+this.timeRange).then(res=>{
        if (res.code === 200){
          option.xAxis.data = res.data.date
          option.series[0].data = res.data.borrow
          option.series[1].data = res.data.retur
          console.log(option)
          charts.chart.setOption(option)  //从后台获取数据,重新给options设置值
        }
      })
    }
  }
})
</script>

<style scoped>

</style>