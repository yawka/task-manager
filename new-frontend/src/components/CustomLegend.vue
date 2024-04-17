<template>
  <div id="custom-legend" class="custom-legend">
    <div v-for="(item, index) in items" :key="index" class="legend-item" @click="toggleVisibility(index)">
      <span :style="{ backgroundColor: item.color }" class="legend-color-box"></span>
      {{ item.label }}
    </div>
  </div>
</template>

<script>
export default {
  name: 'CustomLegend',
  props: {
    items: Array,
    toggleItem: Function
  },
  methods: {
    toggleVisibility(index) {
      this.toggleItem(index);
    }
  }
}
</script>

<style scoped>

.custom-legend {
  margin-top: 2.5rem;
  margin-left: 3.3rem;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  justify-content: space-between;
  gap: 7px;
}

.legend-item {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 5px;
}

.legend-color-box {
  width: 20px;
  height: 20px;
  display: inline-block;
  margin-right: 5px;
  border-radius: 6px;
}

</style>
