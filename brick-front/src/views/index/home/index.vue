<script setup>
import {onMounted, ref} from "vue";
import {getYiYan} from "@/api/home/home.js";

const yiYan = ref({
  content: ''
})

const otherSentence = async () => {
  const hitokoto = await getYiYan()
  yiYan.value.content = hitokoto
}

const contentColor = ref('#E5EAF3')
const refreshSentence = () => {
  contentColor.value = 'transparent'
  setTimeout(() => {
    otherSentence()
    setTimeout(() => {
      contentColor.value = '#E5EAF3'
    }, 200)
  }, 1000)
}

onMounted(() => {
  otherSentence()
})
</script>

<template>
  <div class="home">
    <el-text class="header">{{ $t('serverName') }}</el-text>
    <el-text v-if="yiYan.content !== ''" class="content">{{ yiYan.content }}</el-text>
    <el-button v-if="yiYan.content !== ''" @click="refreshSentence" plain round class="button">换一句</el-button>
  </div>
</template>

<style scoped lang="less">
.home {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;

  .header {
    font-family: Lot, sans-serif !important;
    font-size: 64px;
    color: #E5EAF3;
  }

  .content {
    color: v-bind('contentColor');
    margin-top: 10px;
    font-size: 28px;
    transition: all 1s;
  }

  .button {
    margin-top: 15px;
    width: 150px;
    background-color: transparent;
    color: #E5EAF3;
    border: 2px solid #E5EAF3;
    transition: all 1s;

    &:hover {
      color: #66b1ff;
      border: 2px solid #66b1ff;
    }
  }
}
</style>
