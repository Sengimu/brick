<script setup>
import {ref} from "vue";
import SlideVerify from "vue3-slide-verify";
import "vue3-slide-verify/dist/style.css";

const block = ref(null)

const msg = ref('');
const onSuccess = (times) => {
  msg.value = `login success, 耗时${(times / 1000).toFixed(1)}s`;
};
</script>

<template>
  <SlideVerify
    ref="block"
    :slider-text="msg"
    :interval="10"
    @success="onSuccess">
  </SlideVerify>
</template>

<style scoped lang="less">

</style>
