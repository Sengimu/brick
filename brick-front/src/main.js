import {createApp} from 'vue'

import App from '@/App.vue'
import router from '@/router/index.js'
import pinia from "@/stores/index.js"
import ElementPlus from 'element-plus'
import i18n from "@/i18n/index.js";

import '@/assets/css/main.css'
import '@/assets/css/font.css'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)

app.use(router)
app.use(pinia)
app.use(ElementPlus)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
app.use(i18n)

app.mount('#app')
