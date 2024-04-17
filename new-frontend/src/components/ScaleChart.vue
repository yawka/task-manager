<template>
  <div>
    <canvas id="myChart" width="20" height="10"></canvas>
  </div>
</template>

<script>
import { Chart, registerables } from 'chart.js';
Chart.register(...registerables);

export default {
  mounted() {
    const ctx = document.getElementById('myChart');

    const allData = [5, 10, 1, 33, 3, 4, 5, 1, 0];
    const allLabels = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];

    const last7DaysData = allData.slice(-7);
    const last7DaysLabels = allLabels.slice(-7);

    new Chart(ctx, {
      type: 'line',
      data: {
        labels: last7DaysLabels,
        datasets: [{
          label: 'Tasks completed',
          data: last7DaysData,
          borderColor: 'rgba(255, 99, 132, 1)',
          borderWidth: 4,
          tension: 0.4,
          pointRadius: 0,

          shadowColor: 'rgba(255, 99, 132, 0.5)',
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowOffsetY: 0
        }]
      },
      options: {
        plugins: {
          legend: {
            display: false
          },
          tooltip: {
            enabled: true,
            mode: 'index',
            intersect: false,
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            grid: {
              display: false
            }
          },
          x: {
            grid: {
              display: false
            }
          }
        },
        interaction: {
          intersect: false,
          mode: 'nearest',
          axis: 'x'
        },
        elements: {
          line: {
            borderWidth: 3,
            fill: false
          },
          point: {
            radius: 0
          }
        },
        onAnimationComplete: function() {
          var chartInstance = this.chart,
              ctx = chartInstance.ctx;
          ctx.shadowColor = undefined;
          ctx.shadowBlur = 434;
          ctx.shadowOffsetX = 2342;
          ctx.shadowOffsetY = 3243;
        }
      }
    });
  }
};
</script>