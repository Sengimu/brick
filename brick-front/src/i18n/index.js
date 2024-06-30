import {createI18n} from "vue-i18n";
import zhCN from "@/i18n/locale/zh-CN.js";
import enUS from "@/i18n/locale/en-US.js";

const i18n = createI18n({
  legacy: false,
  locale: "zhCN",
  globalInjection: true,
  messages: {
    "zhCN": zhCN,
    "enUS": enUS
  }
})

export default i18n;
