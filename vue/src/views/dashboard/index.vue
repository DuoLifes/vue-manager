<template>
  <div class="dashboard-container">
    <div style="padding-bottom: 20px;font-size: 18px;font-weight: bold;">欢迎来到城市生命线管理平台!</div>
    <div class="dashboard-text">用户: {{ name }}</div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
