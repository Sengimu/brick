<script setup>
import {onMounted, ref, watch} from "vue"
import {useRoute} from "vue-router"

const route = useRoute();

const bgUrl = ref('')

//bgUrl.value = 'url("https://bing.img.run/rand.php")'
//bgUrl.value = 'url("/bg.jpg")'
//https://source.unsplash.com/random/1920x1080
const getB = async () => {
  bgUrl.value = 'url(https://bing.img.run/rand.php)'
}
onMounted(() => {
  getB()
})

</script>

<template>
  <router-view :class="{'bg': route.fullPath === '/home'}"></router-view>
</template>

<style scoped>
.bg {
  background-image: linear-gradient(to bottom, rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0)), v-bind(bgUrl);
}
</style>
