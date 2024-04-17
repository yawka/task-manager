<template>
  <div>
    <canvas id="doughnut-chart"></canvas>
    <CustomLegend :items="legendItems" :toggleItem="toggleLegendItem"></CustomLegend>
  </div>
</template>

<script>
import Chart from 'chart.js/auto'
import CustomLegend from "@/components/CustomLegend.vue";
import ChartDataLabels from 'chartjs-plugin-datalabels';
Chart.register(ChartDataLabels);

export default {
  name: 'DoughnutChart',
  components: {
    CustomLegend,
  },
  data() {
    return {
      legendItems: []
    };
  },
  mounted() {
    this.renderChart()
  },
  methods: {
    renderChart() {
      const ctx = document.getElementById('doughnut-chart').getContext('2d')
      this.chart = new Chart(ctx, {
        type: 'doughnut',
        data: {
          labels: ["To do", 'Completed', 'Canceled', 'In progress'],
          datasets: [
            {
              label: 'Tasksч  ',
              data: [300, 50, 100, 40],
              backgroundColor: [
                'rgb(255, 99, 132)',
                'rgb(54, 162, 235)',
                'rgb(255, 205, 86)',
                'rgb(236,0,0)'
              ],
              hoverOffset: 4
            }
          ]
        },
        options: {
          responsive: true,
          plugins: {
            legend: {
              display: false,
            },
            datalabels: {
              color: '#fff',
              textAlign: 'center',
              font: {
                weight: 'bold'
              },
              formatter: (value, context) => {
                if (context.chart.getDatasetMeta(0).data[context.dataIndex].hidden) {
                  return '';
                }
                return value;
              }
            }
          },
        }
      });

      this.legendItems = this.chart.data.labels.map((label, index) => ({
        label: label,
        color: this.chart.data.datasets[0].backgroundColor[index]
      }));
    },

    toggleLegendItem(itemIndex) {
      const meta = this.chart.getDatasetMeta(0);
      meta.data[itemIndex].hidden = !meta.data[itemIndex].hidden;
      this.chart.update();
    },

    updateChartColors() {
      const isDarkTheme = document.body.classList.contains('dark-theme-variables');
      const darkThemeColors = ['rgba(185,180,181,0.8)', 'rgba(154,152,152,0.8)', 'rgba(107,105,100,0.8)', 'rgba(0,0,0,0.8)'];
      const lightThemeColors = ['rgb(255, 99, 132)', 'rgb(54, 162, 235)', 'rgb(255, 205, 86)', 'rgb(75, 192, 192)'];
      const newColors = isDarkTheme ? darkThemeColors : lightThemeColors;

      this.chart.data.datasets[0].backgroundColor = newColors;
      this.chart.update();

      this.legendItems = this.chart.data.labels.map((label, index) => ({
        label: label,
        color: newColors[index]
      }));
    }
  }
}
</script>

<style>
</style>
